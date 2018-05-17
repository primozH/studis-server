package zrna.izpit;

import helpers.PrijavniPodatkiIzpit;
import izpit.*;
import prijava.Prijava;
import sifranti.Cenik;
import sifranti.Predmet;
import sifranti.StudijskoLeto;
import sifranti.VrstaVpisa;
import vloge.Student;
import vloge.Uporabnik;
import vpis.Vpis;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PrijavaNaIzpitZrno {

    private Logger log = Logger.getLogger(PrijavaNaIzpitZrno.class.getName());

    @PersistenceContext(name = "studis")
    private EntityManager em;
    @Inject private UserTransaction ux;

    public PrijavaRok applyForExam(PrijavaRok prijavaRok, Uporabnik uporabnik) throws Exception {

        if (!uporabnik.getId().equals(prijavaRok.getStudent().getId())) {
            throw new Exception("Ni pravic za prijavo");
        }
        // preveri število polaganj
        IzpitniRok izpitniRok = em.find(IzpitniRok.class, prijavaRok.getRok().getId());
        prijavaRok.setRok(izpitniRok);

        Long applicationCount = checkApplicationCount(prijavaRok);

        // preveri roke (prijava po izteku)
        checkDates(prijavaRok);

        // preveri prijavo na že opravljen izpit
        checkForPassedExam(prijavaRok);
        // praveri za obstoječo prijavo
        // preveri za prijavo z nezaključeno oceno
        checkIfApplicationExistsOrNotClosed(prijavaRok);

        return createApplication(izpitniRok, prijavaRok, applicationCount);
    }

    @Transactional
    public void returnApplication(PrijavaRok prijavaRok, Uporabnik odjavitelj) throws Exception {
        log.info("Odjava od izpita");
        odjavitelj = em.find(Uporabnik.class, odjavitelj.getId());


        try {
            prijavaRok = em.createNamedQuery("entitete.izpit.PrijavaRok.vrniPrijavo", PrijavaRok.class)
                    .setParameter("rok", prijavaRok.getRok().getId())
                    .setParameter("studentId", prijavaRok.getStudent().getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info("Ni razpisanega roka");
            throw new Exception("Ni razpisanega roka");
        }

        preveriOdjavitelja(prijavaRok, odjavitelj);
        IzpitniRok stored = prijavaRok.getRok();
        LocalDateTime lastValidDate = getLastValidDay(stored.getDatum());

        if (lastValidDate.isBefore(LocalDateTime.now()) && odjavitelj.getTip().equalsIgnoreCase("student")) {
            throw new Exception("Odjava ni več mogoča");
        }

        OdjavaIzpit odjavaIzpit = new OdjavaIzpit();
        odjavaIzpit.setCasOdjave(LocalDateTime.now());
        odjavaIzpit.setPrijavaRok(prijavaRok);
        odjavaIzpit.setOdjavitelj(odjavitelj);

        em.persist(odjavaIzpit);

        log.info("Označujem prijavo kot brisano...");
        prijavaRok.setBrisana(true);

        em.persist(prijavaRok);
        log.info("Prijava uspešno vrnjena");
    }

    public List<PrijavaRok> vrniPrijaveNaIzpit(Uporabnik uporabnik) {
        return em.createNamedQuery("entitete.izpit.PrijavaRok.prijaveZaStudenta", PrijavaRok.class)
                .setParameter("student", uporabnik.getId())
                .getResultList();
    }

    public List<PrijavaRok> vrniPrijavljeneStudente(Integer izpitniRok) {
        log.info("Iskanje vseh prijavljenih studentov na izpit.");
        return em.createNamedQuery("entitete.izpit.PrijavaRok.prijavljeniStudentje", PrijavaRok.class)
                .setParameter("id", izpitniRok)
                .getResultList();
    }

    public Long vrniPrijavljeneStudenteCount(Integer izpitniRok) {
        log.info("Stejem prijave na izpitni rok");
        return em.createNamedQuery("entitete.izpit.PrijavaRok.prijavljeniStudentjeCount", Long.class)
                .setParameter("id", izpitniRok)
                .getSingleResult();
    }

    /* HELPERS */
    private Long checkApplicationCount(PrijavaRok prijavaRok) throws Exception {
        Long countStudyYear;
        try {
            countStudyYear = getNumberOfApplicationsForStudyYear(
                    prijavaRok.getStudent().getId(),
                    prijavaRok.getRok().getIzvajanjePredmeta().getStudijskoLeto().getId(),
                    prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra());
        } catch (NoResultException e) {
            countStudyYear = 0L;
        }

        log.info("Stevilo prijav na izpit za tekoce studijsko leto: " + countStudyYear);

        if (countStudyYear >= 3) {
            log.warning("Presezeno stevilo prijav za tekoce studijsko leto");
            throw new Exception("Za to študijsko leto je preseženo največje dovoljeno število prijav na izpit pri posameznem " +
                    "predmetu");
        }

        Long countAll = em.createNamedQuery("entitete.izpit.PrijavaRok.stejPrijave", Long.class)
                .setParameter("student", prijavaRok.getStudent().getId())
                .setParameter("predmet", prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra())
                .getSingleResult();

        log.info("Stevilo vseh prijav na izpit: " + countAll);

        List<Vpis> vpisi = em.createNamedQuery("entitete.vpis.Vpis.vpisiZaStudenta", Vpis.class)
                .setParameter("student", prijavaRok.getStudent().getId())
                .getResultList();

        Vpis zadnjiVpis = vpisi.get(0);
        StudijskoLeto letoZadnjegaVpisa = zadnjiVpis.getStudijskoLeto();
        VrstaVpisa vrstaVpisa = zadnjiVpis.getVrstaVpisa();

        if (letoZadnjegaVpisa.getId().equals(prijavaRok.getRok().getIzvajanjePredmeta().getStudijskoLeto().getId())) {
            /* ponavljanje letnika - odstejemo */
            if (vrstaVpisa.getSifraVpisa().equals(2)) {
                countStudyYear = getNumberOfApplicationsForStudyYear(
                        prijavaRok.getStudent().getId(),
                        vpisi.get(1).getStudijskoLeto().getId(),
                        prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra()
                        );

                countAll -= countStudyYear;
            }
        } else {
            if (vrstaVpisa.getSifraVpisa().equals(2)) {
                countStudyYear = getNumberOfApplicationsForStudyYear(
                        prijavaRok.getStudent().getId(),
                        letoZadnjegaVpisa.getId(),
                        prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra());
                countAll -= countStudyYear;
            }
        }

        if (countAll >= 6) {
            log.warning("Presezeno stevilo prijav na izpit");
            throw new Exception("Presegli ste največje dovoljeno število polaganj za izbrani izpit");
        }

        return countAll;
    }

    private IzpitniRok checkDates(PrijavaRok prijavaRok) throws Exception {
        IzpitniRok izpitniRok;
        try {
            izpitniRok = em.createQuery("SELECT i FROM IzpitniRok i WHERE " +
                    "i.izvajanjePredmeta.predmet.sifra = :predmet AND " +
                    "i.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto AND " +
                    "i.datum = :datum", IzpitniRok.class)
                    .setParameter("predmet", prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra())
                    .setParameter("studijskoLeto", prijavaRok.getRok().getIzvajanjePredmeta().getStudijskoLeto().getId())
                    .setParameter("datum", prijavaRok.getRok().getDatum())
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new Exception("Ni razpisanega roka.");
        }

        LocalDateTime lastValidDateTime = getLastValidDay(izpitniRok.getDatum());


        log.info("Preverjam veljaven cas prijave na izpit. Zadnji rok za prijavo: " +
                "" + lastValidDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss dd-MM-yyyy")));
        LocalDateTime now = LocalDateTime.now();

        if (lastValidDateTime.isBefore(now)) {
            log.info("Prepozna prijava na izpit");
            throw new Exception("Prepozna prijava na izpit! Rok za prijavo je potekel ob " +
                    lastValidDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss dd-MM-yyyy")));
        }

        return izpitniRok;
    }

    private void checkForPassedExam(PrijavaRok prijavaRok) throws Exception {
        log.info("Preverjam, ce obstaja pozitivna ocena za izpit");
        Izpit izpit;
        try {
            izpit = em.createNamedQuery("entitete.izpit.Izpit.opravljenIzpit", Izpit.class)
                    .setParameter("student", prijavaRok.getStudent().getId())
                    .setParameter("predmet", prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra())
                    .getSingleResult();
        } catch (NoResultException e) {
            izpit = null;
        }

        if (izpit != null) {
            log.info("Ocena za predmet že obstaja");
            throw new Exception("Pozitivna ocena za ta predmet že obstaja!");
        }
        log.info("Ocena za ta predmet še ne obstaja");
    }

    private void checkIfApplicationExistsOrNotClosed(PrijavaRok prijavaRok) throws Exception {
        log.info("Preverjam za obstoječe prijave...");
        PrijavaRok stored;
        try {
            stored = em.createNamedQuery("entitete.izpit.PrijavaRok.aktivnePrijave", PrijavaRok.class)
                    .setParameter("predmet", prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra())
                    .setParameter("student", prijavaRok.getStudent().getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            stored = null;
        }

        if (stored != null) {
            log.info("Prijava obstaja!");
            throw new Exception("Prijava že obstaja");
        }

    }

    private BigDecimal setPayment(Long applicationCount) {
        if (applicationCount > 2) {
            return em.createNamedQuery("entitete.sifranti.Cenik.cenaZaIzpit", Cenik.class).getSingleResult().getCena();
        }
        else return BigDecimal.valueOf(0);
    }

    private PrijavaRok createApplication(IzpitniRok rok, PrijavaRok prijavaRok, Long applicationCount) {
        BigDecimal cena = setPayment(applicationCount);

        prijavaRok.setCasPrijave(LocalDateTime.now());
        prijavaRok.setRok(rok);
        prijavaRok.setCena(cena);

        try {
            ux.begin();
            em.persist(prijavaRok);
            ux.commit();
            return prijavaRok;
        } catch (NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException | RollbackException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Long getNumberOfApplicationsForStudyYear(Integer studentId, Integer studijskoLetoId,
                                                     Integer predmetId) throws NoResultException {
        log.info("Preverjam stevilo polaganj");
        return em.createNamedQuery("entitete.izpit.PrijavaRok.stejPrijaveStudijskoLeto", Long.class)
                .setParameter("student", studentId)
                .setParameter("studijskoLeto", studijskoLetoId)
                .setParameter("predmet", predmetId)
                .getSingleResult();
    }

    private LocalDateTime getLastValidDay(LocalDate date) {
        date = date.minusDays(2);
        LocalTime time = LocalTime.of(23, 59);
        return LocalDateTime.of(date, time);
    }

    private void preveriOdjavitelja(PrijavaRok prijavaRok, Uporabnik odjavitelj) throws Exception {
        if (!prijavaRok.getStudent().getId().equals(odjavitelj.getId())
                && odjavitelj.getTip().equalsIgnoreCase("student")) {
            throw new Exception("Ni pravic za odjavo");
        }

        if (odjavitelj.getTip().equalsIgnoreCase("ucitelj")) {
            try {
                IzvajanjePredmeta predmet = em.createNamedQuery("entitete.izpit.IzvajanjePredmeta.vrniPredmet", IzvajanjePredmeta.class)
                        .setParameter("ucitelj", odjavitelj.getId())
                        .setParameter("studijskoLeto", prijavaRok.getRok().getIzvajanjePredmeta().getStudijskoLeto().getId())
                        .setParameter("predmet", prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra())
                        .getSingleResult();
            } catch (NoResultException e) {
                throw new Exception("Ucitelj nima pravice odjave");
            }
        }
    }
}

package zrna.izpit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import helpers.entities.PrijavaNaIzpit;
import izpit.Izpit;
import izpit.IzpitniRok;
import izpit.IzvajanjePredmeta;
import izpit.OdjavaIzpit;
import izpit.PrijavaRok;
import sifranti.Cenik;
import sifranti.StudijskoLeto;
import sifranti.VrstaVpisa;
import vloge.Student;
import vloge.Uporabnik;
import vpis.Vpis;

@ApplicationScoped
public class PrijavaNaIzpitZrno {

    private Logger log = Logger.getLogger(PrijavaNaIzpitZrno.class.getName());

    @PersistenceContext(name = "studis")
    private EntityManager em;
    @Inject private UserTransaction ux;

    public PrijavaRok applyForExam(PrijavaRok prijavaRok, Uporabnik uporabnik) throws Exception {

        if (!uporabnik.getId().equals(prijavaRok.getStudent().getId()) && uporabnik.getTip().equals("Student")) {
            throw new Exception("Ni pravic za prijavo");
        }
        // preveri število polaganj
        IzpitniRok izpitniRok = em.find(IzpitniRok.class, prijavaRok.getRok().getId());
        prijavaRok.setRok(izpitniRok);

        List<Vpis> vpisi = em.createNamedQuery("entitete.vpis.Vpis.vpisiZaStudenta", Vpis.class)
                .setParameter("student", prijavaRok.getStudent().getId())
                .getResultList();

        Vpis zadnjiVpis = vpisi.get(0);
        Integer totalTries = checkApplicationCount(prijavaRok, vpisi);

        // preveri roke (prijava po izteku, premalo dni)
        checkDates(prijavaRok);

        // preveri prijavo na že opravljen izpit
        checkForPassedExam(prijavaRok);
        // praveri za obstoječo prijavo
        // preveri za prijavo z nezaključeno oceno
        checkIfApplicationExistsOrNotClosed(prijavaRok);

        return createApplication(izpitniRok, prijavaRok, totalTries, zadnjiVpis);
    }

    public IzpitniRok getExam(PrijavaRok prijavaRok) {
        return em.find(IzpitniRok.class, prijavaRok.getRok().getId());
    }

    public Uporabnik getUser(Uporabnik uporabnik) {
        return em.find(Uporabnik.class, uporabnik.getId());
    }

    public Student getStudentVpisna(Student student) {
        return em.createNamedQuery("entitete.vloge.Student.vrniStudentaPoVpisniStevilki", Student.class)
                .setParameter("vpisnaStevilka", student.getVpisnaStevilka())
                .getSingleResult();
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
            log.info("Prijava ne obstaja");
            throw new Exception("Prijava ne obstaja");
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

    public Long vrniPrijavljeneStudenteCount(Integer izpitniRok) {
        log.info("Stejem prijave na izpitni rok");
        return em.createNamedQuery("entitete.izpit.PrijavaRok.prijavljeniStudentjeCount", Long.class)
                .setParameter("id", izpitniRok)
                .getSingleResult();
    }

    public List<PrijavaNaIzpit> vrniPrijavljeneStudenteZOcenami(Integer izpitniRok) {
        log.info("Seznam vseh prijavljenih studentov z ocenami");

        List<PrijavaNaIzpit> prijaveZRezultati = new ArrayList<>();

        List<Object[]> list = em.createNamedQuery("entitete.izpit.PrijavaRok.prijavljeniStudentiZOcenami", Object[].class)
                .setParameter("rokId", izpitniRok)
                .getResultList();

        for (Object[] prijava : list) {
            PrijavaNaIzpit prijavaNaIzpit = new PrijavaNaIzpit();
            prijavaNaIzpit.setPrijavaRok((PrijavaRok) prijava[0]);

            if (prijava[1] != null) {
                Izpit izpit = (Izpit) prijava[1];
                prijavaNaIzpit.setOcenaPisno(izpit.getOcenaPisno());
                prijavaNaIzpit.setKoncnaOcena(izpit.getKoncnaOcena());
            }

            prijaveZRezultati.add(prijavaNaIzpit);
        }

        log.info("Vracam prijavljene studente na rok " + izpitniRok + " z ocenami (" + prijaveZRezultati.size() + ")");
        return prijaveZRezultati;
    }

    /* HELPERS */
    private Integer checkApplicationCount(PrijavaRok prijavaRok, List<Vpis> vpisi) throws Exception {
        Integer countStudyYear;
        try {
            countStudyYear = getNumberOfApplicationsForStudyYear(
                    prijavaRok.getStudent().getId(),
                    prijavaRok.getRok().getIzvajanjePredmeta().getStudijskoLeto().getId(),
                    prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra());
        } catch (Exception e) {
            countStudyYear = 0;
        }

        log.info("Stevilo prijav na izpit za tekoce studijsko leto: " + countStudyYear);

        if (countStudyYear >= 3) {
            log.warning("Presezeno stevilo prijav za tekoce studijsko leto");
            throw new Exception("Za to študijsko leto je preseženo največje dovoljeno število prijav na izpit pri posameznem " +
                    "predmetu");
        }

        Integer countAll = em.createNamedQuery("entitete.izpit.Izpit.vrniPolaganja", Long.class)
                .setParameter("studentId", prijavaRok.getStudent().getId())
                .setParameter("sifraPredmeta", prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra())
                .getResultList().size();

        log.info("Stevilo opravljanj izpita: " + countAll);

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
            }
        }
        else {
            countStudyYear = 0;
//            if (vrstaVpisa.getSifraVpisa().equals(2)) {
//                countStudyYear = getNumberOfApplicationsForStudyYear(
//                        prijavaRok.getStudent().getId(),
//                        letoZadnjegaVpisa.getId(),
//                        prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra());
//                countAll -= countStudyYear;
//            }
        }

        if ((countAll - countStudyYear) >= 6) {
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

        LocalDate timeBetweenApplications = izpitniRok.getDatum().plusDays(14);

        List<PrijavaRok> zakljucenePrijave = em.createNamedQuery("entitete.izpit.PrijavaRok.zakljucenePrijave", PrijavaRok.class)
                .setParameter("predmet", prijavaRok.getRok().getIzvajanjePredmeta().getPredmet().getSifra())
                .setParameter("studentId", prijavaRok.getStudent().getId())
                .getResultList();

        if (zakljucenePrijave.size() != 0 &&
            zakljucenePrijave.get(0).getRok().getDatum().isBefore(timeBetweenApplications)) {
            log.info("Od zadnje prijave še ni minilo dovolj časa");
            throw new Exception("Od zadnje prijave še ni minilo dovolj časa");
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

    private BigDecimal setPayment(Integer applicationCount, boolean enrolled) {
        if (applicationCount >= 3 || !enrolled) {
            return em.createNamedQuery("entitete.sifranti.Cenik.cenaZaIzpit", Cenik.class).getSingleResult().getCena();
        }
        else return BigDecimal.valueOf(0);
    }

    private PrijavaRok createApplication(IzpitniRok rok, PrijavaRok prijavaRok, Integer totalTries, Vpis lastEnrollment) {
        boolean enrolled = true;
        if (!lastEnrollment.getStudijskoLeto().getId().equals(prijavaRok.getRok().getIzvajanjePredmeta().getStudijskoLeto().getId())) {
            enrolled = false;
        }

        BigDecimal cena = setPayment(totalTries, enrolled);

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

    private Integer getNumberOfApplicationsForStudyYear(Integer studentId, Integer studijskoLetoId,
                                                     Integer predmetId) throws NoResultException {
        log.info("Preverjam stevilo polaganj");
        return em.createNamedQuery("entitete.izpit.Izpit.vrniIzpitZaLeto", Long.class)
                .setParameter("studentId", studentId)
                .setParameter("studijskoLeto", studijskoLetoId)
                .setParameter("sifraPredmeta", predmetId)
                .getResultList().size();
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

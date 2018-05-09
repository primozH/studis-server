package zrna.izpit;

import helpers.PrijavniPodatkiIzpit;
import izpit.*;
import sifranti.Cenik;
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

    public PrijavaRok applyForExam(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {

        // preveri število polaganj
        Long applicationCount = checkApplicationCount(prijavniPodatki);

        // preveri roke (prijava po izteku)
        IzpitniRok izpitniRok = checkDates(prijavniPodatki);

        // preveri prijavo na že opravljen izpit
        checkForPassedExam(prijavniPodatki);
        // praveri za obstoječo prijavo
        // preveri za prijavo z nezaključeno oceno
        checkIfApplicationExistsOrNotClosed(prijavniPodatki);

        return createApplication(izpitniRok, prijavniPodatki, applicationCount);
    }

    @Transactional
    public void returnApplication(PrijavniPodatkiIzpit prijavniPodatki, Uporabnik odjavitelj) throws Exception {
        log.info("Odjava od izpita");
        IzpitniRok izpitniRok = getIzpitniRok(prijavniPodatki);
        LocalDateTime lastValidDate = getLastValidDay(izpitniRok.getDatum());
        odjavitelj = em.find(Uporabnik.class, odjavitelj.getId());

        if (lastValidDate.isBefore(LocalDateTime.now()) && odjavitelj.getTip().equalsIgnoreCase("student")) {
            throw new Exception("Odjava ni več mogoča");
        }

        PrijavaRok prijavaRok = getPrijavaIzpit(izpitniRok, prijavniPodatki);

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

    public PrijavaRok vrniZadnjoPrijavoZaPredmet(int sifraPredmeta, int studentId) {
        return em.createNamedQuery("entitete.izpit.PrijavaRok.vrniZadnjoPrijavo", PrijavaRok.class)
                .setParameter("sifraPredmeta", sifraPredmeta)
                .setParameter("studentId", studentId)
                .getSingleResult();
    }

    public List<PrijavaRok> vrniPrijavljeneStudente(int sifraPredmeta, int studijskoLeto, LocalDate datum) {
        log.info("Iskanje vseh prijavljenih studentov na izpit. Predmet: " + sifraPredmeta +
                ", studijsko leto: " + studijskoLeto + ", datum: " + datum);
        return   em.createNamedQuery("entitete.izpit.PrijavaRok.prijavljeniStudentje", PrijavaRok.class)
                .setParameter("predmet", sifraPredmeta)
                .setParameter("studijskoLeto", studijskoLeto)
                .setParameter("datum", datum)
                .getResultList();
    }

    private Long checkApplicationCount(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {
        Long countStudyYear;
        try {
            countStudyYear = getNumberOfApplicationsForStudyYear(
                    prijavniPodatki.getStudent(),
                    prijavniPodatki.getStudijskoLeto(),
                    prijavniPodatki.getPredmet());
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
                .setParameter("student", prijavniPodatki.getStudent())
                .setParameter("predmet", prijavniPodatki.getPredmet())
                .getSingleResult();

        log.info("Stevilo vseh prijav na izpit: " + countAll);

        List<Vpis> vpisi = em.createNamedQuery("entitete.vpis.Vpis.vpisiZaStudenta", Vpis.class)
                .setParameter("student", prijavniPodatki.getStudent())
                .getResultList();

        Vpis zadnjiVpis = vpisi.get(0);
        StudijskoLeto letoZadnjegaVpisa = zadnjiVpis.getStudijskoLeto();
        VrstaVpisa vrstaVpisa = zadnjiVpis.getVrstaVpisa();

        if (letoZadnjegaVpisa.getId().equals(prijavniPodatki.getStudijskoLeto())) {
            /* ponavljanje letnika - odstejemo */
            if (vrstaVpisa.getSifraVpisa().equals(2)) {
                countStudyYear = getNumberOfApplicationsForStudyYear(
                        prijavniPodatki.getStudent(),
                        vpisi.get(1).getStudijskoLeto().getId(),
                        prijavniPodatki.getPredmet()
                        );

                countAll -= countStudyYear;
            }
        } else {
            if (vrstaVpisa.getSifraVpisa().equals(2)) {
                countStudyYear = getNumberOfApplicationsForStudyYear(
                        prijavniPodatki.getStudent(),
                        letoZadnjegaVpisa.getId(),
                        prijavniPodatki.getPredmet());
                countAll -= countStudyYear;
            }
        }

        if (countAll >= 6) {
            log.warning("Presezeno stevilo prijav na izpit");
            throw new Exception("Presegli ste največje dovoljeno število polaganj za izbrani izpit");
        }

        return countAll;
    }

    private IzpitniRok checkDates(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {
        IzpitniRok izpitniRok;
        try {
            izpitniRok = em.createQuery("SELECT i FROM IzpitniRok i WHERE " +
                    "i.izvajanjePredmeta.predmet.sifra = :predmet AND " +
                    "i.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto AND " +
                    "i.datum = :datum", IzpitniRok.class)
                    .setParameter("predmet", prijavniPodatki.getPredmet())
                    .setParameter("studijskoLeto", prijavniPodatki.getStudijskoLeto())
                    .setParameter("datum", prijavniPodatki.getDatumIzvajanja())
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

    private void checkForPassedExam(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {
        log.info("Preverjam, ce obstaja pozitivna ocena za izpit");
        Izpit izpit;
        try {
            izpit = em.createNamedQuery("entitete.izpit.Izpit.opravljenIzpit", Izpit.class)
                    .setParameter("student", prijavniPodatki.getStudent())
                    .setParameter("predmet", prijavniPodatki.getPredmet())
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

    private void checkIfApplicationExistsOrNotClosed(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {
        log.info("Preverjam za obstoječe prijave...");
        PrijavaRok stored;
        try {
            stored = em.createNamedQuery("entitete.izpit.PrijavaRok.aktivnePrijave", PrijavaRok.class)
                    .setParameter("predmet", prijavniPodatki.getPredmet())
                    .setParameter("studijskoLeto", prijavniPodatki.getStudijskoLeto())
                    .setParameter("student", prijavniPodatki.getStudent())
                    .setParameter("datum", prijavniPodatki.getDatumIzvajanja())
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

    private PrijavaRok createApplication(IzpitniRok rok, PrijavniPodatkiIzpit prijavniPodatki, Long applicationCount) {
        BigDecimal cena = setPayment(applicationCount);
        Student student = new Student();
        student.setId(prijavniPodatki.getStudent());

        PrijavaRok prijavaRok = new PrijavaRok();
        prijavaRok.setCasPrijave(LocalDateTime.now());
        prijavaRok.setRok(rok);
        prijavaRok.setCena(cena);
        prijavaRok.setStudent(student);

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

    private IzpitniRok getIzpitniRok(PrijavniPodatkiIzpit prijavniPodatkiIzpit) {
        return em.find(IzpitniRok.class, getIzpitniRokId(prijavniPodatkiIzpit));
    }

    private PrijavaRok getPrijavaIzpit(IzpitniRok rok, PrijavniPodatkiIzpit prijavniPodatkiIzpit) {
        try {
            return em.createQuery("SELECT p FROM PrijavaRok p WHERE p.rok = :rok " +
                    "AND p.student.id = :student " +
                    "AND p.brisana = FALSE", PrijavaRok.class)
                    .setParameter("rok", rok)
                    .setParameter("student", prijavniPodatkiIzpit.getStudent())
                    .getSingleResult();
        } catch (NoResultException e) {
            throw e;
        }
    }


    private IzpitniRokId getIzpitniRokId(PrijavniPodatkiIzpit prijavniPodatkiIzpit) {
        IzvajanjePredmetaId izvajanjePredmetaId = new IzvajanjePredmetaId();
        izvajanjePredmetaId.setPredmet(prijavniPodatkiIzpit.getPredmet());
        izvajanjePredmetaId.setStudijskoLeto(prijavniPodatkiIzpit.getStudijskoLeto());

        IzpitniRokId id = new IzpitniRokId();
        id.setDatum(prijavniPodatkiIzpit.getDatumIzvajanja());
        id.setIzvajanjePredmeta(izvajanjePredmetaId);
        return id;
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
}

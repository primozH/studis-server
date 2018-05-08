package zrna;

import helpers.PrijavniPodatkiIzpit;
import izpit.*;
import sifranti.Cenik;
import sifranti.StudijskoLeto;
import sifranti.VrstaVpisa;
import vloge.Student;
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

    private Logger logger = Logger.getLogger(PrijavaNaIzpitZrno.class.getName());

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

        logger.info("Stevilo prijav na izpit za tekoce studijsko leto: " + countStudyYear);

        if (countStudyYear >= 3) {
            logger.warning("Presezeno stevilo prijav za tekoce studijsko leto");
            throw new Exception("Za to študijsko leto je preseženo največje dovoljeno število prijav na izpit pri posameznem " +
                    "predmetu");
        }

        Long countAll = em.createNamedQuery("entitete.izpit.PrijavaRok.stejPrijave", Long.class)
                .setParameter("student", prijavniPodatki.getStudent())
                .setParameter("predmet", prijavniPodatki.getPredmet())
                .getSingleResult();

        logger.info("Stevilo vseh prijav na izpit: " + countAll);

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
            logger.warning("Presezeno stevilo prijav na izpit");
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


        logger.info("Preverjam veljaven cas prijave na izpit. Zadnji rok za prijavo: " +
                "" + lastValidDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss dd-MM-yyyy")));
        LocalDateTime now = LocalDateTime.now();

        if (lastValidDateTime.isBefore(now)) {
            logger.info("Prepozna prijava na izpit");
            throw new Exception("Prepozna prijava na izpit! Rok za prijavo je potekel ob " +
                    lastValidDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss dd-MM-yyyy")));
        }

        return izpitniRok;
    }

    private void checkForPassedExam(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {
        logger.info("Preverjam, ce obstaja pozitivna ocena za izpit");
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
            logger.info("Ocena za predmet že obstaja");
            throw new Exception("Pozitivna ocena za ta predmet že obstaja!");
        }
        logger.info("Ocena za ta predmet še ne obstaja");
    }

    private void checkIfApplicationExistsOrNotClosed(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {
        logger.info("Preverjam za obstoječe prijave...");
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
            logger.info("Prijava obstaja!");
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
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Transactional
    public void returnApplication(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {
        logger.info("Odjava od izpita");
        IzpitniRok izpitniRok = getIzpitniRok(prijavniPodatki);
        LocalDateTime lastValidDate = getLastValidDay(izpitniRok.getDatum());

        if (lastValidDate.isBefore(LocalDateTime.now())) {
            throw new Exception("");
        }
        PrijavaRok prijavaRok = getPrijavaIzpit(prijavniPodatki);

        OdjavaIzpit odjavaIzpit = new OdjavaIzpit();
        odjavaIzpit.setCasOdjave(LocalDateTime.now());
        odjavaIzpit.setPrijavaRok(prijavaRok);

        em.persist(odjavaIzpit);

        logger.info("Označujem prijavo kot brisano...");
        prijavaRok.setBrisana(true);

        em.persist(prijavaRok);
        logger.info("Prijava uspešno vrnjena");
    }

    private IzpitniRok getIzpitniRok(PrijavniPodatkiIzpit prijavniPodatkiIzpit) {
        return em.find(IzpitniRok.class, getIzpitniRokId(prijavniPodatkiIzpit));
    }

    private PrijavaRok getPrijavaIzpit(PrijavniPodatkiIzpit prijavniPodatkiIzpit) {
        PrijavaRokId id = new PrijavaRokId();

        id.setStudent(prijavniPodatkiIzpit.getStudent());
        id.setRok(getIzpitniRokId(prijavniPodatkiIzpit));

        return em.find(PrijavaRok.class, id);
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
        logger.info("Preverjam stevilo polaganj");
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

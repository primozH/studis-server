package zrna;

import izpit.*;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class IzpitZrno {

    private final static Logger logger = Logger.getLogger(IzpitZrno.class.getName());

    @PersistenceContext
    private EntityManager em;

    public void applyForExam(PrijavaIzpit prijavaIzpit) throws Exception {

        // preveri število polaganj
        // celotno število polaganj (največ 6), polaganja v tekočem letu (največ 3)
        checkApplicationCount(prijavaIzpit);

        // prijava na izpit iz prejšnjega letnika

        // preveri roke (prijava po izteku)
        checkDates(prijavaIzpit);

        // preveri prijavo na že opravljen izpit
        checkForPassedExam(prijavaIzpit);
        // praveri za obstoječo prijavo
        // preveri za prijavo z nezaključeno oceno
        checkIfApplicationExistsOrNotClosed(prijavaIzpit);

        // preveri za plačilo
        // ponavljalcu se odštejejo polaganja iz prvega vpisa
        setPayment(prijavaIzpit);
    }

    private void checkApplicationCount(PrijavaIzpit prijavaIzpit) throws Exception {
        logger.info("Preverjam stevilo polaganj");
        Integer countStudyYear = em.createNamedQuery("entitete.izpit.PrijavaIzpit.stejPrijaveStudijskoLeto", Integer.class)
                .setParameter("student", prijavaIzpit.getPredmetStudent().getVpis().getStudent())
                .setParameter("studijskoLeto", prijavaIzpit.getPredmetStudent().getVpis().getStudijskoLeto())
                .setParameter("predmet", prijavaIzpit.getPredmetStudent().getPredmet())
                .getSingleResult();
        Integer countAll = em.createNamedQuery("entitete.izpit.PrijavaIzpit.stejPrijave", Integer.class)
                .setParameter("student", prijavaIzpit.getPredmetStudent().getVpis().getStudent())
                .setParameter("predmet", prijavaIzpit.getPredmetStudent().getPredmet())
                .getSingleResult();

        logger.info("Stevilo prijav na izpit za tekoce studijsko leto: " + countStudyYear);
        logger.info("Stevilo vseh prijav na izpit: " + countAll);
        if (countStudyYear >= 3) {
            logger.warning("Presezeno stevilo prijav za tekoce studijsko leto");
            throw new Exception("Za to študijsko leto je preseženo največje dovoljeno število prijav na izpit pri posameznem " +
                    "predmetu");
        }

        if (countAll >= 6) {
            logger.warning("Presezeno stevilo prijav na izpit");
            throw new Exception("Presegli ste največje dovoljeno število polaganj za izbrani izpit");
        }
    }

    private void checkDates(PrijavaIzpit prijavaIzpit) throws Exception {
        LocalDateTime lastValidDate = prijavaIzpit.getRok().getDatumCasIzvajanja()
                .minusDays(2)
                .withHour(23)
                .withMinute(59)
                .withSecond(0);
        logger.info("Preverjam veljaven cas prijave na izpit. Zadnji rok za prijavo: " +
                "" + lastValidDate.format(DateTimeFormatter.ofPattern("hh:mm:ss dd-MM-yyyy")));
        LocalDateTime now = LocalDateTime.now();

        if (lastValidDate.isBefore(now)) {
            logger.info("Prepozna prijava na izpit");
            throw new Exception("Prepozna prijava na izpit! Rok za prijavo je potekel ob " +
            lastValidDate.format(DateTimeFormatter.ofPattern("hh:mm:ss dd-MM-yyyy")));
        }
    }

    private void checkForPassedExam(PrijavaIzpit prijavaIzpit) throws Exception {
        logger.info("Preverjam, ce obstaja pozitivna ocena za izpit");
        Izpit i = em.createNamedQuery("entitete.izpit.PrijavaIzpit.preveriZaOpravljenIzpit", Izpit.class)
                .setParameter("student", prijavaIzpit.getPredmetStudent().getVpis().getStudent())
                .setParameter("predmet", prijavaIzpit.getPredmetStudent().getPredmet())
                .getSingleResult();

        if (i != null) {
            logger.info("Ocena za predmet že obstaja");
            throw new Exception("Pozitivna ocena za ta predmet že obstaja!");
        }
        logger.info("Ocena za ta predmet še ne obstaja");
    }

    private void checkIfApplicationExistsOrNotClosed(PrijavaIzpit prijavaIzpit) throws Exception {
        logger.info("Preverjam za obstoječe prijave...");
        PrijavaIzpit stored = em.createNamedQuery("entitete.izpit.PrijavaIzpit.aktivnePrijave", PrijavaIzpit.class)
                .setParameter("predmetStudent", prijavaIzpit.getPredmetStudent())
                .setParameter("rok", prijavaIzpit.getRok())
                .getSingleResult();

        if (stored != null) {
            logger.info("Prijava obstaja!");
            throw new Exception("Prijava že obstaja");
        }

    }

    private void setPayment(PrijavaIzpit prijavaIzpit) { }

    public List<IzpitniRok> vrniRokeZaPredmet(int sifraPredmeta) {
        return em.createNamedQuery("entities.izpit.IzpitniRok.vrniIzpitneRokeZaPredmet", IzpitniRok.class)
                .setParameter("sifraPredmeta", sifraPredmeta)
                .getResultList();
    }

    public int vrniSteviloVsehPolaganjPredmetaZaStudenta(int studentId, int sifraPredmeta) {
        return em.createNamedQuery("entities.izpit.Izpit.vrniSteviloVsehPolaganj", Izpit.class)
                .setParameter("sifraPredmeta", sifraPredmeta)
                .setParameter("studentId", studentId)
                .getResultList().size();
    }

    public Izpit vrniIzpitZaLeto(int sifraPredmeta, int studentId, int studijskoLeto) {
        return em.createNamedQuery("entities.izpit.Izpit.vrniIzpitZaLeto", Izpit.class)
                 .setParameter("sifraPredmeta", sifraPredmeta)
                 .setParameter("studentId", studentId)
                 .setParameter("studijskoLeto", studijskoLeto)
                 .getSingleResult();
    }

    public PrijavaIzpit vrniZadnjoPrijavoZaPredmet(int sifraPredmeta, int studentId) {
        return em.createNamedQuery("entities.izpit.PrijavaIzpit.vrniZadnjoPrijavo", PrijavaIzpit.class)
                 .setParameter("sifraPredmeta", sifraPredmeta)
                 .setParameter("studentId", studentId)
                 .getSingleResult();
    }

    public void returnApplication(OdjavaIzpit odjava) {
        logger.info("Vracanje prijave");

        OdjavaIzpit odjavaIzpit = new OdjavaIzpit();
        odjavaIzpit.setCasOdjave(LocalDateTime.now());
        odjavaIzpit.setOdjavitelj(odjava.getOdjavitelj());
        odjavaIzpit.setPrijavaIzpit(odjava.getPrijavaIzpit());

        em.persist(odjavaIzpit);
        em.refresh(odjavaIzpit);

        logger.info("Označujem prijavo kot brisano...");
        PrijavaIzpit prijavaIzpit = odjavaIzpit.getPrijavaIzpit();
        prijavaIzpit.setBrisana(true);

        em.persist(prijavaIzpit);
        logger.info("Prijava uspešno vrnjena");
    }
}

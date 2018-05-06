package zrna;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

import helpers.PrijavniPodatkiIzpit;
import izpit.Izpit;
import izpit.IzpitniRok;
import izpit.IzpitniRokId;
import izpit.IzvajanjePredmeta;
import izpit.IzvajanjePredmetaId;
import izpit.OdjavaIzpit;
import izpit.PrijavaIzpit;
import izpit.PrijavaIzpitId;
import izpit.StatusRazpisaRoka;
import sifranti.Praznik;
import sifranti.Predmet;
import student.PredmetStudent;
import student.PredmetStudentId;
import vloge.Uporabnik;
import vpis.VpisId;

@ApplicationScoped
public class IzpitZrno {

    private final static Logger logger = Logger.getLogger(IzpitZrno.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction userTransaction;

    public void applyForExam(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {

        // preveri število polaganj
        // celotno število polaganj (največ 6), polaganja v tekočem letu (največ 3)
        checkApplicationCount(prijavniPodatki);

        // prijava na izpit iz prejšnjega letnika

        // preveri roke (prijava po izteku)
        IzpitniRok izpitniRok = checkDates(prijavniPodatki);

        // preveri prijavo na že opravljen izpit
        checkForPassedExam(prijavniPodatki);
        // praveri za obstoječo prijavo
        // preveri za prijavo z nezaključeno oceno
        checkIfApplicationExistsOrNotClosed(prijavniPodatki);

        createApplication(izpitniRok, prijavniPodatki);
    }

    private void checkApplicationCount(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {
        logger.info("Preverjam stevilo polaganj");
        Long countStudyYear = em.createNamedQuery("entitete.izpit.PrijavaIzpit.stejPrijaveStudijskoLeto", Long.class)
                .setParameter("student", prijavniPodatki.getStudent())
                .setParameter("studijskoLeto", prijavniPodatki.getStudijskoLeto())
                .setParameter("predmet", prijavniPodatki.getPredmet())
                .getSingleResult();
        Long countAll = em.createNamedQuery("entitete.izpit.PrijavaIzpit.stejPrijave", Long.class)
                .setParameter("student", prijavniPodatki.getStudent())
                .setParameter("predmet", prijavniPodatki.getPredmet())
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

    private IzpitniRok checkDates(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {
        IzpitniRok izpitniRok;
        try {
            izpitniRok = em.createQuery("SELECT i FROM IzpitniRok i WHERE " +
                    "i.izvajanjePredmeta.predmet.sifra = :predmet AND " +
                    "i.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto AND " +
                    "i.datumCasIzvajanja = :datum", IzpitniRok.class)
                    .setParameter("predmet", prijavniPodatki.getPredmet())
                    .setParameter("studijskoLeto", prijavniPodatki.getStudijskoLeto())
                    .setParameter("datum", prijavniPodatki.getDatumIzvajanja())
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new Exception("Ni razpisanega roka.");
        }

        LocalDateTime lastValidDate = izpitniRok
                .getDatumCasIzvajanja()
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

        return izpitniRok;
    }

    private void checkForPassedExam(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {
        logger.info("Preverjam, ce obstaja pozitivna ocena za izpit");
        Izpit izpit;
        try {
            izpit = em.createNamedQuery("entitete.izpit.PrijavaIzpit.preveriZaOpravljenIzpit", Izpit.class)
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
        PrijavaIzpit stored;
        try {
            stored = em.createNamedQuery("entitete.izpit.PrijavaIzpit.aktivnePrijave", PrijavaIzpit.class)
                    .setParameter("predmet", prijavniPodatki.getPredmet())
                    .setParameter("studijskoLeto", prijavniPodatki.getStudijskoLeto())
                    .setParameter("student", prijavniPodatki.getStudent())
                    .setParameter("datumCas", prijavniPodatki.getDatumIzvajanja())
                    .getSingleResult();
        } catch (NoResultException e) {
            stored = null;
        }

        if (stored != null) {
            logger.info("Prijava obstaja!");
            throw new Exception("Prijava že obstaja");
        }

    }

    private BigDecimal setPayment() {
        return null;
    }

    private void createApplication(IzpitniRok rok, PrijavniPodatkiIzpit prijavniPodatki) {
        BigDecimal cena = setPayment();
        PredmetStudent predmetStudent = getPredmetStudent(prijavniPodatki);

        PrijavaIzpit prijavaIzpit = new PrijavaIzpit();
        prijavaIzpit.setCasPrijave(LocalDateTime.now());
        prijavaIzpit.setRok(rok);
        prijavaIzpit.setPredmetStudent(predmetStudent);
        prijavaIzpit.setCena(cena);

        try {
            userTransaction.begin();
            em.persist(prijavaIzpit);
            userTransaction.commit();
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
    }
    @Transactional
    public void returnApplication(PrijavniPodatkiIzpit prijavniPodatki) throws Exception {
        logger.info("Odjava od izpita");
        IzpitniRok izpitniRok = getIzpitniRok(prijavniPodatki);
        LocalDateTime lastValidDate = izpitniRok
                .getDatumCasIzvajanja()
                .minusDays(2)
                .withHour(23)
                .withMinute(59)
                .withSecond(0);

        if (lastValidDate.isBefore(LocalDateTime.now())) {
            throw new Exception("");
        }
        PrijavaIzpit prijavaIzpit = getPrijavaIzpit(prijavniPodatki);

        OdjavaIzpit odjavaIzpit = new OdjavaIzpit();
        odjavaIzpit.setCasOdjave(LocalDateTime.now());
        odjavaIzpit.setPrijavaIzpit(prijavaIzpit);

        em.persist(odjavaIzpit);

        logger.info("Označujem prijavo kot brisano...");
        prijavaIzpit.setBrisana(true);

        em.persist(prijavaIzpit);
        logger.info("Prijava uspešno vrnjena");
    }

    private PredmetStudent getPredmetStudent(PrijavniPodatkiIzpit prijavniPodatkiIzpit) {
        return em.find(PredmetStudent.class, getPredmetStudentId(prijavniPodatkiIzpit));
    }

    private IzpitniRok getIzpitniRok(PrijavniPodatkiIzpit prijavniPodatkiIzpit) {
        return em.find(IzpitniRok.class, getIzpitniRokId(prijavniPodatkiIzpit));
    }

    private PrijavaIzpit getPrijavaIzpit(PrijavniPodatkiIzpit prijavniPodatkiIzpit) {
        PrijavaIzpitId id = new PrijavaIzpitId();
        id.setPredmetStudent(getPredmetStudentId(prijavniPodatkiIzpit));
        id.setRok(getIzpitniRokId(prijavniPodatkiIzpit));

        return em.find(PrijavaIzpit.class, id);
    }

    private PredmetStudentId getPredmetStudentId(PrijavniPodatkiIzpit prijavniPodatkiIzpit) {
        VpisId vpis = new VpisId();
        vpis.setStudent(prijavniPodatkiIzpit.getStudent());
        vpis.setStudijskoLeto(prijavniPodatkiIzpit.getStudijskoLeto());

        PredmetStudentId id = new PredmetStudentId();
        id.setPredmet(prijavniPodatkiIzpit.getPredmet());
        id.setVpis(vpis);
        return id;
    }

    private IzpitniRokId getIzpitniRokId(PrijavniPodatkiIzpit prijavniPodatkiIzpit) {
        IzvajanjePredmetaId izvajanjePredmetaId = new IzvajanjePredmetaId();
        izvajanjePredmetaId.setPredmet(prijavniPodatkiIzpit.getPredmet());
        izvajanjePredmetaId.setStudijskoLeto(prijavniPodatkiIzpit.getStudijskoLeto());

        IzpitniRokId id = new IzpitniRokId();
        id.setDatumCasIzvajanja(prijavniPodatkiIzpit.getDatumIzvajanja());
        id.setIzvajanjePredmeta(izvajanjePredmetaId);
        return id;
    }

    public List<IzpitniRok> vrniIzpitneRoke(Integer uporabnikId, Integer sifraPredmeta, Integer studijskoLeto) {
        Uporabnik u = em.find(Uporabnik.class, uporabnikId);
        if (!u.getTip().equalsIgnoreCase("student")) {
            logger.info("Pridobivanje izpitnih rokov za predmet " + sifraPredmeta + " in studijsko leto " + studijskoLeto);
            return em.createNamedQuery("entitete.izpit.IzpitniRok.vrniIzpitneRokeZaPredmet", IzpitniRok.class)
                    .setParameter("sifraPredmeta", sifraPredmeta)
                    .setParameter("studijskoLeto", studijskoLeto)
                    .getResultList();
        } else {
            logger.info("Pridobivanje izpitnih rokov za študenta " + uporabnikId);
            List<IzpitniRok> roki = em.createNamedQuery("entitete.izpit.IzpitniRok.vrniIzpitneRoke", IzpitniRok.class)
                    .setParameter("student", uporabnikId)
                    .setParameter("studijskoLeto", studijskoLeto)
                    .getResultList();

            List<Predmet> opravljeniPredmeti = em.createNamedQuery("entitete.izpit.Izpit.opravljeniPredmeti", Predmet.class)
                    .setParameter("student", uporabnikId)
                    .getResultList();


            return roki.stream().filter(rok -> !opravljeniPredmeti.contains(rok.getIzvajanjePredmeta().getPredmet()))
                    .collect(Collectors.toList());
        }
    }

    public List<PrijavaIzpit> vrniPrijaveNaIzpit(Uporabnik uporabnik, Integer studijskoLeto) {
        return em.createNamedQuery("entitete.izpit.PrijavaIzpit.prijaveZaStudenta", PrijavaIzpit.class)
                .setParameter("student", uporabnik.getId())
                .setParameter("studijskoLeto", studijskoLeto)
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

//    public boolean vrniPrijavoZaPredmet(int sifraPredmeta, int studentId, int studijskoLeto) {
//        logger.info("Vracanje prijave");
//
//        PrijavaIzpit prijavaIzpit =  em.createNamedQuery("entities.izpit.PrijavaIzpit.vrniPrijavo", PrijavaIzpit.class)
//                 .setParameter("sifraPredmeta", sifraPredmeta)
//                 .setParameter("studentId", studentId)
//                 .setParameter("studijskoLeto", studijskoLeto)
//                 .getSingleResult();
//
//        if (prijavaIzpit == null) return false;
//
//        long casIzvajanjaIzpita = prijavaIzpit.getRok().getDatumCasIzvajanja().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//        long trenutniCas = System.currentTimeMillis();
//        if (trenutniCas + 24 * 60 * 60 * 1000 >= casIzvajanjaIzpita) return false;
//        prijavaIzpit.setBrisana(true);
//        em.merge(prijavaIzpit);
//        OdjavaIzpit odjavaIzpit = new OdjavaIzpit();
//        odjavaIzpit.setCasOdjave(LocalDateTime.now());
//        odjavaIzpit.setPrijavaIzpit(prijavaIzpit);
//        odjavaIzpit.setOdjavitelj(prijavaIzpit.getPredmetStudent().getVpis().getStudent());
//        em.merge(odjavaIzpit);
//        return true;
//    }

    public List<PrijavaIzpit> vrniPrijavljeneStudente(int sifraPredmeta, int studijskoLeto, LocalDateTime datumCas) {
        logger.info("Iskanje vseh prijavljenih studentov na izpit. Predmet: " + sifraPredmeta +
        ", studijsko leto: " + studijskoLeto + ", datum: " + datumCas);
        return   em.createNamedQuery("entitete.izpit.PrijavaIzpit.prijavljeniStudentje", PrijavaIzpit.class)
                .setParameter("predmet", sifraPredmeta)
                .setParameter("studijskoLeto", studijskoLeto)
                .setParameter("datumCas", datumCas)
                .getResultList();
    }

    public boolean vnesiRezultatIzpita(Izpit izpit, int sifraPredmeta, int studentId, int studijskoLeto) {
        PrijavaIzpit prijavaIzpit =  null;
        try{
            prijavaIzpit = em.createNamedQuery("entities.izpit.PrijavaIzpit.vrniPrijavo", PrijavaIzpit.class)
              .setParameter("sifraPredmeta", sifraPredmeta)
              .setParameter("studentId", studentId)
              .setParameter("studijskoLeto", studijskoLeto)
              .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (prijavaIzpit == null
            || prijavaIzpit.isBrisana()
                || izpit.getOcenaPisno() > 100 || izpit.getOcenaPisno() < 0) return false;
        em.merge(izpit);
        return true;
    }

    public List<Izpit> vrniZeVneseneRezultateIzpita(int sifraPredmeta, int studijskoLeto) {
        List<Izpit> izpiti = null;
        try {
            izpiti = em.createNamedQuery("entities.izpit.Izpit.vrniIzpiteZZeVpisanoOceno", Izpit.class)
                    .setParameter("sifraPredmeta", sifraPredmeta)
                    .setParameter("studijskoLeto", studijskoLeto)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return izpiti;
    }

    public boolean razveljaviOceno(int sifraPredmeta, int studentId, int studijskoLeto) {
        Izpit izpit =  null;
        try{
            izpit = em.createNamedQuery("entities.izpit.Izpit.vrniIzpitZaLeto", Izpit.class)
                             .setParameter("sifraPredmeta", sifraPredmeta)
                             .setParameter("studentId", studentId)
                             .setParameter("studijskoLeto", studijskoLeto)
                             .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (izpit == null) return false;
        izpit.setOcenaPisno(-1);
        em.merge(izpit);
        return true;
    }

    @Transactional
    public StatusRazpisaRoka vnesiIzpitniRok(int sifraPredmeta, int studijskoLeto, IzpitniRok rok, int vnasalecId) {
        if (rok == null
                || rok.getDatumCasIzvajanja() == null
                || sifraPredmeta <= 0
                || studijskoLeto <= 0
                || vnasalecId <= 0
                || rok.getIzvajalec() == null
                || rok.getProstor() == null) return StatusRazpisaRoka.MANJKAJO_PODATKI;
        // Pogledamo, ce je datum vecji od trenutnega
        if (rok.getDatumCasIzvajanja().isBefore(LocalDateTime.now())) {
            return StatusRazpisaRoka.DATUM_ZE_PRETECEN;
        }
        // Pogledamo, ce je datum sobota ali nedelja
        if (rok.getDatumCasIzvajanja().getDayOfWeek() == DayOfWeek.SUNDAY
                   || rok.getDatumCasIzvajanja().getDayOfWeek() == DayOfWeek.SATURDAY) {
            return StatusRazpisaRoka.DATUM_VIKEND;
        }
        // Pogledamo, ce na ta datum ni praznika
        boolean jePraznikNaTaDan;
        Praznik praznik = null;
        try {
            praznik = em.createNamedQuery("entities.sifranti.Praznik.vrniPraznikZaTaDatum", Praznik.class)
              .setParameter("datum", rok.getDatumCasIzvajanja().toLocalDate())
              .getSingleResult();
        } catch (Exception e) {
        }
        jePraznikNaTaDan = praznik != null;
        if (jePraznikNaTaDan) {
            return StatusRazpisaRoka.DATUM_PRAZNIK;
        }

        // Pogledamo, ce za to uro ze obstaja kaksen izpit
        List<IzpitniRok> izpitniRoki = null;
        try {
            izpitniRoki = em.createNamedQuery("entities.izpit.Izpit.vrniIzpitneRokeZaTaDan", IzpitniRok.class)
                           .setParameter("studijskoLeto", studijskoLeto)
                           .setParameter("datumCas", rok.getDatumCasIzvajanja())
                           .getResultList();
        } catch (Exception e) {
        }
        if (izpitniRoki != null && !izpitniRoki.isEmpty()) {
            return StatusRazpisaRoka.IZPIT_ZA_URO_OBSTAJA;
        }
        // Pogledamo, ce je vnasalec ucitelj ali referent
        Uporabnik vnasalec = null;
        try {
            logger.info("vnasalecId = " + vnasalecId);
            vnasalec = em.createNamedQuery("entitete.vloge.Uporabnik.vrniUporabnika", Uporabnik.class)
                    .setParameter("uporabnikId", vnasalecId)
                    .getSingleResult();
        } catch (Exception e) {
        }
        if (vnasalec == null) {
            logger.info("uporabnik neobstaja");
            return StatusRazpisaRoka.UPORABNIK_NEOBSTAJA;
        }
        // Pogledamo, ce ucitelj uci predmet za katerega razpisuje rok
        // ali, ce referent razpisuje rok za dolocen predmet za dolocenega ucitelja, ki uci predmet
        IzvajanjePredmeta predmet = null;
        if (vnasalec.getTip().equals("Ucitelj") || vnasalec.getTip().equals("Referent")) {
            try {
                predmet = em.createNamedQuery("entitete.izpit.IzvajanjePredmeta.vrniPredmet", IzvajanjePredmeta.class)
                        .setParameter("ucitelj", vnasalec.getTip().equals("Referent") ? rok.getIzvajalec().getId() : vnasalecId)
                        .setParameter("studijskoLeto", studijskoLeto)
                        .setParameter("predmet", sifraPredmeta)
                        .getSingleResult();
            } catch (Exception e) {

            }
            if (predmet == null) return StatusRazpisaRoka.UCITELJ_NE_UCI_PREDMETA;
        } else {
            return StatusRazpisaRoka.NAPACEN_TIP_UPORABNIKA;
        }
        rok.setIzvajanjePredmeta(predmet);
        em.merge(rok);
        return StatusRazpisaRoka.VELJAVEN_VNOS;
    }
}

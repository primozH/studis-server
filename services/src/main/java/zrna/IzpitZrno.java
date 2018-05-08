package zrna;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import izpit.*;
import izpit.PrijavaRok;
import sifranti.Praznik;
import sifranti.Predmet;
import vloge.Uporabnik;

@ApplicationScoped
public class IzpitZrno {

    private final static Logger logger = Logger.getLogger(IzpitZrno.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction userTransaction;

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

    public List<PrijavaRok> vrniPrijaveNaIzpit(Uporabnik uporabnik, Integer studijskoLeto) {
        return em.createNamedQuery("entitete.izpit.PrijavaIzpit.prijaveZaStudenta", PrijavaRok.class)
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

    public PrijavaRok vrniZadnjoPrijavoZaPredmet(int sifraPredmeta, int studentId) {
        return em.createNamedQuery("entities.izpit.PrijavaRok.vrniZadnjoPrijavo", PrijavaRok.class)
                 .setParameter("sifraPredmeta", sifraPredmeta)
                 .setParameter("studentId", studentId)
                 .getSingleResult();
    }

    public List<PrijavaRok> vrniPrijavljeneStudente(int sifraPredmeta, int studijskoLeto, LocalDate datum) {
        logger.info("Iskanje vseh prijavljenih studentov na izpit. Predmet: " + sifraPredmeta +
        ", studijsko leto: " + studijskoLeto + ", datum: " + datum);
        return   em.createNamedQuery("entitete.izpit.PrijavaRok.prijavljeniStudentje", PrijavaRok.class)
                .setParameter("predmet", sifraPredmeta)
                .setParameter("studijskoLeto", studijskoLeto)
                .setParameter("datum", datum)
                .getResultList();
    }

    public boolean vnesiRezultatIzpita(Izpit izpit, int sifraPredmeta, int studentId, int studijskoLeto) {
        PrijavaRok prijavaRok =  null;
        try{
            prijavaRok = em.createNamedQuery("entities.izpit.PrijavaIzpit.vrniPrijavo", PrijavaRok.class)
              .setParameter("sifraPredmeta", sifraPredmeta)
              .setParameter("studentId", studentId)
              .setParameter("studijskoLeto", studijskoLeto)
              .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (prijavaRok == null
            || prijavaRok.isBrisana()
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
                || rok.getDatum() == null
                || sifraPredmeta <= 0
                || studijskoLeto <= 0
                || vnasalecId <= 0
                || rok.getIzvajalec() == null
                || rok.getProstor() == null) return StatusRazpisaRoka.MANJKAJO_PODATKI;
        // Pogledamo, ce je datum vecji od trenutnega
        if (rok.getDatum().isBefore(LocalDate.now())) {
            return StatusRazpisaRoka.DATUM_ZE_PRETECEN;
        }
        // Pogledamo, ce je datum sobota ali nedelja
        if (rok.getDatum().getDayOfWeek() == DayOfWeek.SUNDAY
                   || rok.getDatum().getDayOfWeek() == DayOfWeek.SATURDAY) {
            return StatusRazpisaRoka.DATUM_VIKEND;
        }
        // Pogledamo, ce na ta datum ni praznika
        boolean jePraznikNaTaDan;
        Praznik praznik = null;
        try {
            praznik = em.createNamedQuery("entities.sifranti.Praznik.vrniPraznikZaTaDatum", Praznik.class)
              .setParameter("datum", rok.getDatum())
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
                           .setParameter("datumCas", rok.getDatum())
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

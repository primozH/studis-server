package zrna.izpit;

import izpit.IzpitniRok;
import izpit.IzvajanjePredmeta;
import izpit.PrijavaRok;
import izpit.StatusRazpisaRoka;
import sifranti.Praznik;
import vloge.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class IzpitniRokZrno {

    private final static Logger log = Logger.getLogger(IzpitniRokZrno.class.getName());

    @PersistenceContext private EntityManager em;

    @Inject
    private UserTransaction ux;

    public List<IzpitniRok> vrniIzpitneRoke(Integer uporabnikId, Integer sifraPredmeta) {
        Uporabnik u = em.find(Uporabnik.class, uporabnikId);
        List<IzpitniRok> roki;
        if (!u.getTip().equalsIgnoreCase("student")) {
            log.info("Pridobivanje izpitnih rokov za predmet " + sifraPredmeta);
            roki = em.createNamedQuery("entitete.izpit.IzpitniRok.vrniIzpitneRokeZaPredmet", IzpitniRok.class)
                    .setParameter("sifraPredmeta", sifraPredmeta)
                    .setParameter("datum", LocalDate.now())
                    .getResultList();
        } else {
            log.info("Pridobivanje izpitnih rokov za študenta " + uporabnikId);
            roki = em.createNamedQuery("entitete.izpit.IzpitniRok.izpitniRokiZaStudenta", IzpitniRok.class)
                    .setParameter("student", uporabnikId)
                    .setParameter("datum", LocalDate.now())
                    .getResultList();

        }

        return roki;
    }

    public StatusRazpisaRoka vnesiIzpitniRok(IzpitniRok rok, Uporabnik vnasalec) {
        if (rok == null
                || rok.getDatum() == null
                || rok.getIzvajanjePredmeta().getPredmet() == null
                || rok.getIzvajanjePredmeta().getStudijskoLeto() == null
                || rok.getIzvajalec() == null) return StatusRazpisaRoka.MANJKAJO_PODATKI;
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
            praznik = em.createNamedQuery("entitete.sifranti.Praznik.vrniPraznikZaTaDatum", Praznik.class)
                    .setParameter("datum", rok.getDatum())
                    .getSingleResult();
        } catch (Exception e) {
        }
        jePraznikNaTaDan = praznik != null;
        if (jePraznikNaTaDan) {
            return StatusRazpisaRoka.DATUM_PRAZNIK;
        }

        // Pogledamo, ce ucitelj uci predmet za katerega razpisuje rok
        // ali, ce referent razpisuje rok za dolocen predmet za dolocenega ucitelja, ki uci predmet
        vnasalec = em.find(Uporabnik.class, vnasalec.getId());
        IzvajanjePredmeta predmet;
        if (vnasalec.getTip().equalsIgnoreCase("referent")) {
            try {
                predmet = em.createNamedQuery("entitete.izpit.IzvajanjePredmeta.vrniPredmet", IzvajanjePredmeta.class)
                        .setParameter("ucitelj", rok.getIzvajalec().getId())
                        .setParameter("studijskoLeto", rok.getIzvajanjePredmeta().getStudijskoLeto().getId())
                        .setParameter("predmet", rok.getIzvajanjePredmeta().getPredmet().getSifra())
                        .getSingleResult();
            } catch (Exception e) {
                return StatusRazpisaRoka.UCITELJ_NE_UCI_PREDMETA;
            }
        } else {
            try {
                predmet = em.createNamedQuery("entitete.izpit.IzvajanjePredmeta.vrniPredmet", IzvajanjePredmeta.class)
                        .setParameter("ucitelj", vnasalec.getId())
                        .setParameter("studijskoLeto", rok.getIzvajanjePredmeta().getStudijskoLeto().getId())
                        .setParameter("predmet", rok.getIzvajanjePredmeta().getPredmet().getSifra())
                        .getSingleResult();
            } catch (NoResultException e) {
                return StatusRazpisaRoka.UCITELJ_NE_UCI_PREDMETA;
            }
        }

        rok.setIzvajanjePredmeta(predmet);
        try {
            ux.begin();
            em.persist(rok);
            ux.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicRollbackException | HeuristicMixedException e) {
            e.printStackTrace();
            return StatusRazpisaRoka.DATUM_RAZPISAN_ROK;
        }
        return StatusRazpisaRoka.VELJAVEN_VNOS;
    }


}

package zrna.izpit;

import izpit.*;
import vloge.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class IzpitniRokZrno {

    private final static Logger log = Logger.getLogger(IzpitniRokZrno.class.getName());

    @PersistenceContext private EntityManager em;

    @Inject
    private UserTransaction ux;

    public List<IzpitniRok> vrniIzpitneRoke(Uporabnik uporabnik, Integer sifraPredmeta) {
        Uporabnik u = em.find(Uporabnik.class, uporabnik.getId());
        List<IzpitniRok> roki;
        if (!u.getTip().equalsIgnoreCase("student")) {
            log.info("Pridobivanje izpitnih rokov za predmet " + sifraPredmeta);
            roki = em.createNamedQuery("entitete.izpit.IzpitniRok.vrniIzpitneRokeZaPredmet", IzpitniRok.class)
                    .setParameter("sifraPredmeta", sifraPredmeta)
                    .setParameter("datum", LocalDate.now())
                    .getResultList();
        } else {
            log.info("Pridobivanje izpitnih rokov za študenta " + uporabnik.getId());
            roki = em.createNamedQuery("entitete.izpit.IzpitniRok.izpitniRokiZaStudenta", IzpitniRok.class)
                    .setParameter("student", uporabnik.getId())
                    .setParameter("datum", LocalDate.now())
                    .getResultList();

        }

        return roki;
    }

    public IzpitniRok vnesiIzpitniRok(IzpitniRok rok, Uporabnik vnasalec) throws Exception {
        log.info("Razpisujem izpitni rok");
        if (rok == null
                || rok.getDatum() == null
                || rok.getIzvajanjePredmeta().getPredmet() == null
                || rok.getIzvajanjePredmeta().getStudijskoLeto() == null
                || rok.getIzvajalec() == null) {
            log.info("Manjkajoci podatki za razpis roka.");
            throw new Exception("Manjkajo podatki, potrebni za razpis roka");
        }
        // Pogledamo, ce je datum vecji od trenutnega
        if (rok.getDatum().isBefore(LocalDate.now())) {
            log.info("Neveljaven datum za razpis.");
            throw new Exception("Neveljaven datum");
        }
        // Pogledamo, ce je datum sobota ali nedelja
        if (rok.getDatum().getDayOfWeek() == DayOfWeek.SUNDAY
                || rok.getDatum().getDayOfWeek() == DayOfWeek.SATURDAY) {
            log.info("Neveljaven dan (vikend).");
            throw new Exception("Neveljaven datum (vikend)");
        }
        // Pogledamo, ce na ta datum ni praznika
        List praznik = null;
        try {
            praznik = em.createNativeQuery("SELECT datum FROM praznik " +
                    "WHERE datum = ? OR " +
                    "(? = MONTH(datum) AND ? = DAY(datum))")
                    .setParameter(1, rok.getDatum().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .setParameter(2, rok.getDatum().getMonthValue())
                    .setParameter(3, rok.getDatum().getDayOfMonth())
                    .getResultList();
        } catch (Exception e) {
            log.warning("Napaka pri iskanju praznika " + e.getMessage());
            praznik = new ArrayList();
        }
        if (praznik.size() != 0) {
            log.info("Neveljaven datum (praznik).");
            throw new Exception("Neveljaven datum (praznik)");
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
                log.info("Neveljaven izvajalec");
                throw new Exception("Neveljaven izvajalec");
            }
        } else {
            try {
                predmet = em.createNamedQuery("entitete.izpit.IzvajanjePredmeta.vrniPredmet", IzvajanjePredmeta.class)
                        .setParameter("ucitelj", vnasalec.getId())
                        .setParameter("studijskoLeto", rok.getIzvajanjePredmeta().getStudijskoLeto().getId())
                        .setParameter("predmet", rok.getIzvajanjePredmeta().getPredmet().getSifra())
                        .getSingleResult();
            } catch (NoResultException e) {
                log.info("Neveljaven izvajalec");
                throw new Exception("Neveljaven izvajalec");
            }
        }

        rok.setIzvajanjePredmeta(predmet);
        try {
            ux.begin();
            em.persist(rok);
            ux.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicRollbackException | HeuristicMixedException e) {
            log.info("Na ta dan je ze razpisan rok za predmet");
            throw new Exception("Na ta dan je že razpisan izpitni rok pri predmetu");
        }

        log.info("Uspesno vnesen izpitni rok");
        return rok;
    }


}

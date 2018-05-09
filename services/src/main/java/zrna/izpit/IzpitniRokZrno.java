package zrna.izpit;

import izpit.IzpitniRok;
import izpit.IzvajanjePredmeta;
import izpit.PrijavaRok;
import izpit.StatusRazpisaRoka;
import sifranti.Praznik;
import vloge.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class IzpitniRokZrno {

    private final static Logger log = Logger.getLogger(IzpitniRokZrno.class.getName());

    @PersistenceContext private EntityManager em;

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
            praznik = em.createNamedQuery("entitete.sifranti.Praznik.vrniPraznikZaTaDatum", Praznik.class)
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
            izpitniRoki = em.createNamedQuery("entitete.izpit.IzpitniRok.vrniIzpitneRokeZaTaDan", IzpitniRok.class)
                    .setParameter("studijskoLeto", studijskoLeto)
                    .setParameter("datum", rok.getDatum())
                    .getResultList();
        } catch (Exception e) {
        }
        if (izpitniRoki != null && !izpitniRoki.isEmpty()) {
            return StatusRazpisaRoka.IZPIT_ZA_URO_OBSTAJA;
        }
        // Pogledamo, ce je vnasalec ucitelj ali referent
        Uporabnik vnasalec = null;
        try {
            log.info("vnasalecId = " + vnasalecId);
            vnasalec = em.createNamedQuery("entitete.vloge.Uporabnik.vrniUporabnika", Uporabnik.class)
                    .setParameter("uporabnikId", vnasalecId)
                    .getSingleResult();
        } catch (Exception e) {
        }
        if (vnasalec == null) {
            log.info("uporabnik neobstaja");
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

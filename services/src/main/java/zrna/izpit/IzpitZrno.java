package zrna.izpit;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import izpit.*;
import izpit.PrijavaRok;
import prijava.Prijava;
import vloge.Student;

@ApplicationScoped
public class IzpitZrno {

    private final static Logger log = Logger.getLogger(IzpitZrno.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction userTransaction;

    @Transactional
    public void vnesiRezultateIzpita(List<Izpit> izpiti, Integer rokId) {
        for (Izpit izpit : izpiti) {
            PrijavaRok prijavaRok;
            try {
                prijavaRok = preveriPrijavo(izpit, rokId);
            } catch (NoResultException e) {
                log.warning("Ne najdem prijave na izpit za studenta " + izpit.getStudent().getId() + " in rok " + rokId);
                continue;
            }

            if (izpit.getOcenaPisno() != null &&
                    (izpit.getOcenaPisno() > 100 || izpit.getOcenaPisno() < 0)) {
                log.warning("Neveljaven vnos točk za pisni del " + izpit.getOcenaPisno());
                continue;
            }

            if (izpit.getKoncnaOcena() != null &&
                    (izpit.getKoncnaOcena() > 10 || izpit.getKoncnaOcena() < 5)) {
                log.warning("Neveljavna končna ocena");
            }

            Izpit stored = izpitObstaja(izpit.getStudent(), rokId);
            stored = vnesiRezultate(stored, izpit, prijavaRok);

            if (stored.getKoncnaOcena() != null) {
                zakljuciPrijavo(prijavaRok);
            }
        }
    }

    public List<Izpit> vrniVneseneRezultate(Integer rokId) {
        List<Izpit> izpiti = null;
        try {
            izpiti = em.createNamedQuery("entitete.izpit.Izpit.vneseneOceneZaRok", Izpit.class)
                    .setParameter("rok", rokId)
                    .getResultList();
        } catch (Exception e) {
            log.warning("Napaka pri pridobivanju izpitnih podatkov. " + e.getMessage());
        }
        return izpiti;
    }

    private PrijavaRok preveriPrijavo(Izpit izpit, Integer rokId) throws NoResultException {
        return em.createNamedQuery("entitete.izpit.PrijavaRok.vrniNebrisanoPrijavo", PrijavaRok.class)
                .setParameter("rok", rokId)
                .setParameter("studentId", izpit.getStudent().getId())
                .getSingleResult();
    }

    private Izpit izpitObstaja(Student student, Integer rokId) {
        try {
            return em.createNamedQuery("entitete.izpit.Izpit.izpitZaStudenta", Izpit.class)
                    .setParameter("student", student.getId())
                    .setParameter("rok", rokId)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info("Izpit še ne obstaja za študenta " + student.getId() + " in rok " + rokId);
            return null;
        }
    }

    @Transactional
    private Izpit vnesiRezultate(Izpit stored, Izpit izpit, PrijavaRok prijavaRok) {
        if (stored == null) {
            stored = new Izpit();
            Integer zapStPolaganja = stejPolaganja(izpit);

            stored.setOcenaPisno(izpit.getOcenaPisno());
            stored.setKoncnaOcena(izpit.getKoncnaOcena());
            stored.setPredmet(izpit.getPredmet());
            stored.setStudent(izpit.getStudent());
            stored.setPrijavaRok(prijavaRok);
            stored.setZapStPolaganja(zapStPolaganja);

            em.persist(stored);
        } else {
            stored.setOcenaPisno(izpit.getOcenaPisno());
            stored.setKoncnaOcena(izpit.getKoncnaOcena());

            em.merge(stored);
        }

        return stored;
    }

    private Integer stejPolaganja(Izpit izpit) {
        List<Izpit> izpiti = em.createNamedQuery("entitete.izpit.Izpit.vrniPolaganja", Izpit.class)
                .setParameter("studentId", izpit.getStudent().getId())
                .setParameter("sifraPredmeta", izpit.getPredmet().getSifra())
                .getResultList();
        if (izpiti.size() == 0) {
            return 1;
        }

        return izpiti.get(0).getZapStPolaganja() + 1;
    }

    @Transactional
    private void zakljuciPrijavo(PrijavaRok prijavaRok) {
        prijavaRok.setZakljucena(true);

        em.merge(prijavaRok);
    }

}

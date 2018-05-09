package zrna.izpit;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import izpit.*;
import izpit.PrijavaRok;

@ApplicationScoped
public class IzpitZrno {

    private final static Logger log = Logger.getLogger(IzpitZrno.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction userTransaction;

    public int vrniSteviloVsehPolaganjPredmetaZaStudenta(int studentId, int sifraPredmeta) {
        return em.createNamedQuery("entitete.izpit.Izpit.vrniSteviloVsehPolaganj", Izpit.class)
                .setParameter("sifraPredmeta", sifraPredmeta)
                .setParameter("studentId", studentId)
                .getResultList().size();
    }

    public Izpit vrniIzpitZaLeto(int sifraPredmeta, int studentId, int studijskoLeto) {
        return em.createNamedQuery("entitete.izpit.Izpit.vrniIzpitZaLeto", Izpit.class)
                 .setParameter("sifraPredmeta", sifraPredmeta)
                 .setParameter("studentId", studentId)
                 .setParameter("studijskoLeto", studijskoLeto)
                 .getSingleResult();
    }

    public boolean vnesiRezultatIzpita(Izpit izpit, int sifraPredmeta, int studentId, int studijskoLeto) {
        PrijavaRok prijavaRok =  null;
        try{
            prijavaRok = em.createNamedQuery("entitete.izpit.PrijavaRok.vrniPrijavo", PrijavaRok.class)
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
            izpiti = em.createNamedQuery("entitete.izpit.Izpit.vrniIzpiteZZeVpisanoOceno", Izpit.class)
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
            izpit = em.createNamedQuery("entitete.izpit.Izpit.vrniIzpitZaLeto", Izpit.class)
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


}

package zrna;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import izpit.Izpit;
import izpit.IzpitniRok;
import izpit.PrijavaIzpit;

@ApplicationScoped
public class IzpitZrno {

    private static final Logger logger = Logger.getLogger(StudentZrno.class.getName());

    @PersistenceContext(name = "studis")
    private EntityManager em;

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

}

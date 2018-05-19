package zrna;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import predmetnik.Predmetnik;
import sifranti.DelPredmetnika;
import sifranti.Letnik;
import sifranti.Predmet;
import sifranti.StudijskiProgram;
import sifranti.StudijskoLeto;
import student.Zeton;
import vpis.Vpis;

@ApplicationScoped
public class PredmetnikStudentZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    @Transactional
    public List<Predmetnik> getModules(Zeton zeton) {
        return em.createNamedQuery("entitete.predmetnik.Predmetnik.moduli", Predmetnik.class)
                .setParameter("letnik", zeton.getLetnik())
                .setParameter("studijskoLeto", zeton.getStudijskoLeto())
                .setParameter("studijskiProgram", zeton.getStudijskiProgram())
                .getResultList();
    }

    @Transactional
    public List<Predmetnik> getOptionals(Zeton zeton) {
        return em.createNamedQuery("entitete.predmetnik.Predmetnik.splosni", Predmetnik.class)
                .setParameter("letnik", zeton.getLetnik())
                .setParameter("studijskoLeto", zeton.getStudijskoLeto())
                .setParameter("studijskiProgram", zeton.getStudijskiProgram())
                .getResultList();
    }

    @Transactional
    public List<Predmetnik> getProf(Zeton zeton) {
        return em.createNamedQuery("entitete.predmetnik.Predmetnik.strokovni", Predmetnik.class)
                .setParameter("letnik", zeton.getLetnik())
                .setParameter("studijskoLeto", zeton.getStudijskoLeto())
                .setParameter("studijskiProgram", zeton.getStudijskiProgram())
                .getResultList();
    }

    @Transactional
    public List<Predmetnik> getOnlyMandatory(Zeton zeton) {
        return em.createNamedQuery("entitete.predmetnik.Predmetnik.obvezniPredmetnik", Predmetnik.class)
                 .setParameter("letnik", zeton.getLetnik())
                 .setParameter("studijskoLeto", zeton.getStudijskoLeto())
                 .setParameter("studijskiProgram", zeton.getStudijskiProgram())
                 .getResultList();
    }

    public List<Predmet> getCourses(Vpis vpis, DelPredmetnika delPredmetnika) {
        Letnik letnik = vpis.getLetnik();
        StudijskoLeto studijskoLeto = vpis.getStudijskoLeto();
        StudijskiProgram studijskiProgram = vpis.getStudijskiProgram();

        return em.createNamedQuery("entitete.predmetnik.Predmetnik.predmetiZaProgram", Predmet.class)
                .setParameter("letnik", letnik)
                .setParameter("studijskoLeto", studijskoLeto)
                .setParameter("studijskiProgram", studijskiProgram)
                .setParameter("delPredmetnika", delPredmetnika)
                .getResultList();
    }

    public List<Predmetnik> getCurriculum(Vpis vpis, DelPredmetnika delPredmetnika) {
        Letnik letnik = vpis.getLetnik();
        StudijskoLeto studijskoLeto = vpis.getStudijskoLeto();
        StudijskiProgram studijskiProgram = vpis.getStudijskiProgram();

        return em.createNamedQuery("entitete.predmetnik.Predmetnik.predmetnikZaProgram", Predmetnik.class)
                .setParameter("letnik", letnik)
                .setParameter("studijskoLeto", studijskoLeto)
                .setParameter("studijskiProgram", studijskiProgram)
                .setParameter("delPredmetnika", delPredmetnika)
                .getResultList();
    }

    public List<Predmetnik> getStudentJoinedInCourses(Integer studijskiProgram,
                                          Integer studijskoLeto,
                                          Integer letnik) {
        return em.createNamedQuery("entitete.predmetnik.Predmetnik.vrniStudenteZaProgramVLetu", Predmetnik.class)
                 .setParameter("letnik", letnik)
                 .setParameter("studijskoLeto", studijskoLeto)
                 .setParameter("studijskiProgram", studijskiProgram)
                .getResultList();
    }

}

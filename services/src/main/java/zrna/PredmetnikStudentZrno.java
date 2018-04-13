package zrna;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    public List<Predmetnik> getAllButMandatory(Zeton zeton) {
        return em.createNamedQuery("entitete.predmetnik.Predmetnik.opcijskiPredmetnik", Predmetnik.class)
                .setParameter("letnik", zeton.getLetnik())
                .setParameter("studijskoLeto", zeton.getStudijskoLeto())
                .setParameter("studijskiProgram", zeton.getStudijskiProgram())
                .getResultList();
    }

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

}

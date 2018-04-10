package zrna;

import predmetnik.Predmetnik;
import sifranti.*;
import student.PredmetStudent;
import vloge.Student;
import vpis.Vpis;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class PredmetnikStudentZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

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

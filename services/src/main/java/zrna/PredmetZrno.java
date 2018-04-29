package zrna;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import izpit.IzvajanjePredmeta;
import vloge.Student;
import vloge.Ucitelj;

@ApplicationScoped
public class PredmetZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    public List<Student> vrniListoStudentovPredmetaZaUcitelja(int sifraPredmeta, Ucitelj ucitelj, int studijskoLeto) {
        IzvajanjePredmeta predmet = em.createNamedQuery("entities.izpit.IzvajanjePredmeta.vrniIzvajanjePredmetaZaPredmet", IzvajanjePredmeta.class)
                                      .setParameter("sifraPredmeta", sifraPredmeta)
                                      .getSingleResult();
        Ucitelj nosilec1 = predmet.getNosilec1();
        Ucitelj nosilec2 = predmet.getNosilec2();
        Ucitelj nosilec3 = predmet.getNosilec3();

        if (nosilec1 != null && ucitelj.getId() == nosilec1.getId()
                || nosilec2 != null && ucitelj.getId() == nosilec2.getId()
                || nosilec3 != null && ucitelj.getId() == nosilec3.getId()) {
            return vrniListoStudentovZaPredmet(sifraPredmeta, studijskoLeto);
        }
        return null;
    }

    public List<Student> vrniListoStudentovZaPredmet(int sifraPredmeta, int studijskoLeto) {
        return em.createNamedQuery("entities.student.PredmetStudent.vrniSeznamStudentovZaPredmetInLeto", Student.class)
                .setParameter("sifraPredmeta", sifraPredmeta)
                .setParameter("studijskoLeto", studijskoLeto)
                .getResultList();
    }
}

package zrna;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import sifranti.DelPredmetnika;
import sifranti.Klasius;
import sifranti.Letnik;
import sifranti.Predmet;
import sifranti.StudijskiProgram;
import sifranti.StudijskoLeto;
import student.PredmetStudent;
import vloge.Student;
import vpis.Stopnja;
import vpis.Vpis;

@ApplicationScoped
public class PredmetnikStudentZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    public List<Predmet> getCourses(Vpis vpis, DelPredmetnika delPredmetnika) {
        Letnik letnik = vpis.getLetnik();
        StudijskoLeto studijskoLeto = vpis.getStudijskoLeto();

        Klasius klasius = new Klasius();
        klasius.setOpis("lala");
        klasius.setRavenIzobrazbe("bllala");
        klasius.setSifra(124124);
        klasius.setStrokovniNaslov("alalla");
        StudijskiProgram studijskiProgram = new StudijskiProgram();
        studijskiProgram.setKlasius(klasius);
        studijskiProgram.setNaziv("lalallaa");
        studijskiProgram.setSifra("12214");
        studijskiProgram.setSifraEVS(241);
        studijskiProgram.setStSemestrov(2);
        studijskiProgram.setStopnja(Stopnja.K);

        List<Predmet> mandatoryCourses = em.createNamedQuery("entitete.predmetnik.Predmetnik.predmetiZaProgram", Predmet.class)
                .setParameter("letnik", 1)
                .setParameter("studijskoLeto", 2018)
                .setParameter("studijskiProgram", studijskiProgram)
                .setParameter("delPredmetnika", delPredmetnika)
                .getResultList();

        return mandatoryCourses;
    }

    public List<DelPredmetnika> getCurriculumTypes() {
        try {
            return em.createNamedQuery("entitete.sifranti.DelPredmetnika.vrniVseDelePredmetnika").getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void createCurriculum(Vpis vpis, List<Predmet> predmeti) {

        StudijskoLeto studijskoLeto = vpis.getStudijskoLeto();
        Student student = vpis.getStudent();
        predmeti.forEach(predmet ->
            new PredmetStudent(student, predmet, studijskoLeto)
        );
    }
}

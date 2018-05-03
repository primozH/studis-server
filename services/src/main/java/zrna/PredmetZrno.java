package zrna;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import izpit.IzvajanjePredmeta;
import vloge.Student;
import vloge.Ucitelj;
import vloge.Uporabnik;

@ApplicationScoped
public class PredmetZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    @Transactional
    public List<IzvajanjePredmeta> izvajaniPredmeti(Uporabnik uporabnik, Integer studijskoLeto) {
        uporabnik = em.find(Uporabnik.class, uporabnik.getId());
        if (uporabnik.getTip().toLowerCase().equals("referent")) {
            return em.createNamedQuery("entitete.izpit.IzvajanjePredmeta.vrniVsePredmete", IzvajanjePredmeta.class)
                    .setParameter("studijskoLeto", studijskoLeto)
                    .getResultList();
        }

        return em.createNamedQuery("entitete.izpit.IzvajanjePredmeta.vrniPredmete", IzvajanjePredmeta.class)
                .setParameter("ucitelj", uporabnik.getId())
                .setParameter("studijskoLeto", studijskoLeto)
                .getResultList();
    }

    @Transactional
    public List<Student> vrniListoStudentovZaPredmet(Uporabnik uporabnik, int sifraPredmeta, int studijskoLeto) throws Exception {
        List<IzvajanjePredmeta> predmeti = izvajaniPredmeti(uporabnik, studijskoLeto);
        predmeti = predmeti.stream().filter(izvajanjePredmeta -> izvajanjePredmeta.getPredmet().getSifra().equals(sifraPredmeta)).collect(Collectors.toList());
        if (predmeti.size() != 0) {
            return em.createNamedQuery("entities.student.PredmetStudent.vrniSeznamStudentovZaPredmetInLeto", Student.class)
                    .setParameter("sifraPredmeta", sifraPredmeta)
                    .setParameter("studijskoLeto", studijskoLeto)
                    .getResultList();
        }
        throw new Exception("Uporabnik nima pravice vpogleda v seznam studentov za predmet " + sifraPredmeta);
    }
}

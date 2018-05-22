package zrna;

import vloge.Kandidat;
import vloge.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.List;

@ApplicationScoped
public class KandidatZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    @Inject
    private UserTransaction ux;

    @Inject private ZetonZrno zetonZrno;

    public List<Kandidat> getKandidats() {
        return em.createNamedQuery("entitete.vloge.Kandidat.vrniKandidate", Kandidat.class)
                .getResultList();
    }

    public Kandidat getKandidat(Integer kandidatId) {
        return em.find(Kandidat.class, kandidatId);
    }

    @Transactional
    public Student createStudentFromCandidate(Kandidat kandidat) throws Exception {
        Student student = new Student();
        student.setVpisnaStevilka(kandidat.getVpisnaStevilka());
        student.setEmail(kandidat.getEmail());
        student.setGeslo(kandidat.getGesloPlain());
        student.setIme(kandidat.getIme());
        student.setPriimek(kandidat.getPriimek());
        student.setUporabniskoIme(kandidat.getUporabniskoIme());

        em.persist(student);

        zetonZrno.createTokenForCandidate(student, kandidat.getStudijskiProgram());

        em.remove(kandidat);
        return student;
    }
}

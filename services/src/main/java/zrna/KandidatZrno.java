package zrna;

import student.Zeton;
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

    public List<Kandidat> getKandidats() {
        return em.createNamedQuery("entitete.vloge.Kandidat.vrniKandidate", Kandidat.class)
                .getResultList();
    }

    public Kandidat getKandidat(Integer kandidatId) {
        return em.find(Kandidat.class, kandidatId);
    }

    public Integer createStudentFromCandidate(Kandidat kandidat) {
        em.createNamedQuery("entitete.vloge.Student.ustvariStudentaIzKandidata")
                .setParameter("id", kandidat.getId())
                .executeUpdate();
        em.createNativeQuery("INSERT INTO student (id_uporabnik, vpisna_stevilka) VALUES (?, ?)")
                .setParameter(1, kandidat.getId())
                .setParameter(2, kandidat.getVpisnaStevilka())
                .executeUpdate();

        return kandidat.getId();
    }
}

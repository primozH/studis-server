package zrna;

import vloge.Kandidat;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class KandidatZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    public List<Kandidat> getKandidats() {
        return em.createNamedQuery("entitete.vloge.Kandidat.vrniKandidate", Kandidat.class)
                .getResultList();
    }

    public Kandidat getKandidat(Integer kandidatId) {
        return em.find(Kandidat.class, kandidatId);
    }
}

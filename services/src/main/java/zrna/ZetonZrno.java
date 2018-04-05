package zrna;

import student.Zeton;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class ZetonZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    public List<Zeton> getTokens() {
        return em.createNamedQuery("entitete.vpis.Zeton.vrniVse")
                .getResultList();
    }
}

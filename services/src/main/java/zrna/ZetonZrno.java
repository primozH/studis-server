package zrna;

import student.Zeton;
import student.ZetonId;

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

    public Zeton getToken(Integer student, Integer vrstaVpisa) {

        ZetonId zetonId = new ZetonId(student, vrstaVpisa);
        return em.find(Zeton.class, zetonId);
    }
}

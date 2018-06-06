package zrna.uporabniki;

import vloge.Skrbnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class AdminZrno {

    @PersistenceContext(name = "studis") private EntityManager em;

    public Skrbnik getAdmin(Integer id) {
        return em.find(Skrbnik.class, id);
    }
}

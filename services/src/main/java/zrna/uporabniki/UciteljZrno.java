package zrna.uporabniki;

import vloge.Ucitelj;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class UciteljZrno {

    @PersistenceContext(name = "studis") private EntityManager em;

    public Ucitelj getUcitelj(Integer id) {
        return em.find(Ucitelj.class, id);
    }
}

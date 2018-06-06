package zrna.uporabniki;

import vloge.Referent;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

@ApplicationScoped
public class ReferentZrno {

    private final static Logger log = Logger.getLogger(ReferentZrno.class.getName());
    @PersistenceContext(name = "studis") private EntityManager em;

    public Referent getReferent(Integer id) {

        return em.find(Referent.class, id);
    }
}

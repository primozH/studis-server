package zrna;

import naslov.Drzava;
import naslov.Posta;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class SifrantiZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    public List<Drzava> getCountryList() {
        return em.createNamedQuery("entitete.naslov.Drzava.vseDrzave", Drzava.class)
                .getResultList();
    }

    public List<Posta> getPostList() {
        return em.createNamedQuery("entitete.naslov.Posta.vsePoste", Posta.class)
                .getResultList();
    }
}

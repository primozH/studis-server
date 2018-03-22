package viri;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import prijava.Uporabnik;

@Path("prijava")

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PrijavaViri {

    @PersistenceContext(unitName = "studis")
    private EntityManager em;

    Logger logger = Logger.getLogger(PrijavaViri.class.getSimpleName());

    @POST
    public Response preveriPrijavo(Uporabnik uporabnik) {
        logger.info("preveriPrijavo");
        // Preveri, ce uporabnik obstaja v bazi
        Uporabnik uporabnikVBazi = (Uporabnik) this.em.createNamedQuery("entitete.Uporabnik.vrniUporabnika")
                                              .setParameter("uporabniskoIme", uporabnik.getUporabniskoIme())
                                              .getSingleResult();
        if (uporabnikVBazi != null) return Response.status(Response.Status.NOT_FOUND).build();
        // Preveri, ce se geslo jema z uporabnikovim geslom iz baze
        if (uporabnik.getGeslo().equals(uporabnikVBazi.getGeslo())) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}

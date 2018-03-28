package viri;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import prijava.Prijava;
import vloge.Uporabnik;

@Path("prijava")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PrijavaVir {

    @PersistenceContext(unitName = "studis")
    private EntityManager em;

    Logger logger = Logger.getLogger(PrijavaVir.class.getSimpleName());

    @POST
    public Response preveriPrijavo(Prijava prijava) {
        logger.info("preveriPrijavo");
        // Preveri, ce oseba obstaja v bazi
        Uporabnik uporabnikVBazi = (Uporabnik) this.em.createNamedQuery("entities.vloge.Uporabnik.prijava")
                                          .setParameter("email", prijava.getEmail()).getSingleResult();
        if (uporabnikVBazi == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Preveri, ce se geslo jema z uporabnikovim geslom iz baze
        // Todo: hashiraj geslo za prijavo
        String hashGesla = "";

        if (hashGesla.equals(uporabnikVBazi.getGeslo())) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    public Response posljiGesloNaMail(Prijava prijava) {
        logger.info("posljiGesloNaMail");
        // Vrni uporabnika iz baze (ce ga ni, error)
        Uporabnik uporabnikVBazi = (Uporabnik) this.em.createNamedQuery("entitete.Uporabnik.prijava")
                                                      .setParameter("email", prijava.getEmail())
                                                      .getSingleResult();
        if (uporabnikVBazi != null) return Response.status(Response.Status.NOT_FOUND).build();

        // Generiraj geslo in ga shrani v bazo
        // todo { ... to be done ... } -> idea: 10digit random string (numbers + chars)

        // Poslji mail (https://stackoverflow.com/a/47452/6819938)
        // todo rabimo mail account

        // Ce je mail uspesno poslan, status OK
        // if (mail != sentOK) return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); todo
        return Response.status(Response.Status.OK).build();
    }
}

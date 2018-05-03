package rest.viri;

import static javax.ws.rs.core.Response.Status.OK;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import authentication.Auth;
import authentication.Role;
import vloge.Ucitelj;
import vloge.Uporabnik;
import zrna.PredmetZrno;

import java.util.logging.Logger;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("predmet")
public class PredmetVir {

    private final static Logger log = Logger.getLogger(PredmetVir.class.getName());

    @Inject
    private PredmetZrno predmetZrno;

    @POST
    public Response vrniPodatkeOPredmetu(Uporabnik uporabnik,
                                         @QueryParam("sifra-predmeta") Integer sifraPredmeta,
                                         @QueryParam("studijsko-leto") Integer studijskoLeto) {
        if (uporabnik.getTip().equals("Referent")) {
            return Response.status(OK).entity(predmetZrno.vrniListoStudentovZaPredmet(sifraPredmeta, studijskoLeto)).build();
        } else if (uporabnik.getTip().equals("Ucitelj")) {

            return Response.status(OK)
                           .entity(predmetZrno.vrniListoStudentovPredmetaZaUcitelja(sifraPredmeta, (Ucitelj) uporabnik, studijskoLeto))
                           .build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @GET
    @Auth(rolesAllowed = {Role.REFERENT, Role.PREDAVATELJ})
    public Response vrniSeznamIzvajanihPredmetov(Uporabnik uporabnik,
                                                 @QueryParam("studijsko-leto") Integer studijskoLeto) {

        return Response.ok(uporabnik).build();
    }
}

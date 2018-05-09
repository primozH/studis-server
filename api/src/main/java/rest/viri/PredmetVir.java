package rest.viri;

import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import authentication.Auth;
import authentication.Role;
import izpit.IzvajanjePredmeta;
import vloge.Student;
import vloge.Ucitelj;
import vloge.Uporabnik;
import zrna.PredmetZrno;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("predmet")
public class PredmetVir {

    private final static Logger log = Logger.getLogger(PredmetVir.class.getName());

    @Inject
    private PredmetZrno predmetZrno;

    @GET
    @Path("studenti")
    @Auth(rolesAllowed = {Role.REFERENT, Role.PREDAVATELJ})
    public Response vrniPodatkeOPredmetu(Uporabnik uporabnik,
                                         @QueryParam("sifra-predmeta") Integer sifraPredmeta,
                                         @QueryParam("studijsko-leto") Integer studijskoLeto) {

        List<Student> students;
        try {
            students = predmetZrno.vrniListoStudentovZaPredmet(uporabnik, sifraPredmeta, studijskoLeto);
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }

        return Response.status(Response.Status.OK)
                .header("X-Total-Count", students != null ? students.size() : 0)
                .entity(students).build();
    }

    @GET
    @Path("izvajanje")
    @Auth(rolesAllowed = {Role.REFERENT, Role.PREDAVATELJ})
    public Response vrniPredmeteVIzvajanju(@Context HttpServletRequest httpServletRequest,
                                           @QueryParam("studijsko-leto") Integer studijskoLeto) {
        Uporabnik uporabnik = (Uporabnik) httpServletRequest.getAttribute("user");
        List<IzvajanjePredmeta> predmeti;

        predmeti = predmetZrno.izvajaniPredmeti(uporabnik, studijskoLeto);

        return Response.ok(predmeti)
                .header("X-Total-Count", predmeti != null ? predmeti.size() : 0)
                .build();
    }
}

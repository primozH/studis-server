package rest.viri;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import authentication.Auth;
import authentication.Role;
import predmetnik.Predmetnik;
import student.Zeton;
import zrna.PredmetnikStudentZrno;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("predmetnik")
public class PredmetnikVir {

    @Inject
    private PredmetnikStudentZrno psz;

    @POST
    @Path("/strokovni")
    public Response getProf(Zeton zeton) {
        List<Predmetnik> predmetList = psz.getProf(zeton);

        return Response.ok(predmetList).build();
    }

    @POST
    @Path("/splosni")
    public Response getOptional(Zeton zeton) {
        List<Predmetnik> predmetList = psz.getOptionals(zeton);

        return Response.ok(predmetList).build();
    }

    @POST
    @Path("/moduli")
    public Response getModule(Zeton zeton) {
        List<Predmetnik> predmetList = psz.getModules(zeton);

        return Response.ok(predmetList).build();
    }

    @POST
    @Path("/obvezni")
    public Response getOnlyMandatory(Zeton zeton) {
        List<Predmetnik> predmetList = psz.getOnlyMandatory(zeton);
        return Response.ok(predmetList).build();
    }

    @GET
    @Path("vpisani-studenti")
    @Auth(rolesAllowed = { Role.REFERENT})
    public Response getEnrolledStudents(@Context HttpServletRequest httpServletRequest,
                                        @QueryParam("studijski-program") Integer studijskiProgram,
                                        @QueryParam("studijsko-leto") Integer studijskoLeto,
                                        @QueryParam("letnik") Integer letnik) {
        List<Predmetnik> predmetnik = psz.getStudentJoinedInCourses(studijskiProgram, studijskoLeto, letnik);
        return Response.ok(predmetnik).build();
    }
}

package rest.viri;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @Path("/neobvezni")
    public Response getCourses(Zeton zeton) {
        List<Predmetnik> predmetList = psz.getAllButMandatory(zeton);

        return Response.ok(predmetList).build();
    }

    @POST
    @Path("/obvezni")
    public Response getOnlyMandatory(Zeton zeton) {
        List<Predmetnik> predmetList = psz.getOnlyMandatory(zeton);
        return Response.ok(predmetList).build();
    }
}

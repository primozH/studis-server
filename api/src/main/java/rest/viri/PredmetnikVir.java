package rest.viri;

import sifranti.Predmet;
import student.Zeton;
import zrna.PredmetnikStudentZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("predmetnik")
public class PredmetnikVir {

    @Inject
    private PredmetnikStudentZrno psz;

    @POST
    public Response getCourses(Zeton zeton) {
        List<Predmet> predmetList = psz.getAllButMandatory(zeton);

        return Response.ok(predmetList).build();
    }
}

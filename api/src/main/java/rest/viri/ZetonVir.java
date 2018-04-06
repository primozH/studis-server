package rest.viri;

import student.Zeton;
import student.ZetonId;
import vloge.Student;
import zrna.ZetonZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("zeton")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ZetonVir {

    @Inject
    private ZetonZrno zetonZrno;

    @GET
    public Response getTokens() {
        List<Zeton> zetoni = zetonZrno.getTokens();
        return Response.ok(zetoni).build();
    }

    @GET
    @Path("{student}/{vpis}")
    public Response getToken(@PathParam("student") Integer student, @PathParam("vpis") Integer vrstaVpisa) {
        Zeton zeton = zetonZrno.getToken(student, vrstaVpisa);
        return Response.ok(zeton).build();
    }

    @POST
    @Path("{id}")
    public Response createTokenForStudent(@PathParam("id")Integer studentId) {
        Zeton zeton;
        try {
            zeton = zetonZrno.createTokenForStudent(studentId);
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(zeton).build();
    }

    @PUT
    public Response modifyToken(Zeton zeton) {
        Zeton updatedToken = zetonZrno.updateToken(zeton);
        return Response.ok(updatedToken).build();
    }

    @DELETE
    public Response deleteToken(@QueryParam("student") Integer student,
                                @QueryParam("vrsta-vpisa") Integer vpis) {
        zetonZrno.deleteToken(student, vpis);

        return Response.noContent().build();
    }
}

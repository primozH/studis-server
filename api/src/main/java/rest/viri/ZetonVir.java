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
    public Response getTokens(@QueryParam("izkoriscen") Boolean izkoriscen) {
        List<Zeton> zetoni = zetonZrno.getTokens(izkoriscen);
        return Response.ok(zetoni).build();
    }

    @GET
    @Path("student/{id}")
    public Response getTokensForStudent(@PathParam("id") Integer studentId,
                             @QueryParam("izkoriscen") Boolean izkoriscen) {

        List<Zeton> zeton = zetonZrno.getTokensForStudent(studentId, izkoriscen);
        return Response.ok(zeton).build();
    }

    @GET
    @Path("{id}")
    public Response getToken(@PathParam("id") Integer id) {
        Zeton zeton = zetonZrno.getToken(id);
        return Response.ok(zeton).build();
    }

    @POST
    @Path("/student/{id}")
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
    @Path("{id}")
    public Response modifyToken(@PathParam("id") Integer zetonId,
                                Zeton zeton) {
        if (!zetonId.equals(zeton.getId()))
            return Response.status(Response.Status.BAD_REQUEST).build();

        Zeton updatedToken = zetonZrno.updateToken(zeton, zetonId);
        return Response.ok(updatedToken).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteToken(@PathParam("id") Integer zetonId) {
        zetonZrno.deleteToken(zetonId);

        return Response.noContent().build();
    }
}

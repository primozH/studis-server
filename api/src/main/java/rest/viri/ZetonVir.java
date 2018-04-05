package rest.viri;

import student.Zeton;
import student.ZetonId;
import zrna.ZetonZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @Path("{id}")
    public Response getToken(ZetonId id) {
        Zeton zeton = zetonZrno.getToken(id);
        return Response.ok(zeton).build();
    }
}

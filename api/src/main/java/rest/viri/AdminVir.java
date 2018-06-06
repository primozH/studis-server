package rest.viri;

import zrna.uporabniki.AdminZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("admin")
@ApplicationScoped
public class AdminVir {

    @Inject private AdminZrno adminZrno;

    @GET
    @Path("{id}")
    public Response getAdmin(@PathParam("id") Integer id) {
        return Response.ok(adminZrno.getAdmin(id)).build();
    }
}

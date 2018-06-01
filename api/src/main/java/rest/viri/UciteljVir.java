package rest.viri;

import zrna.uporabniki.UciteljZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("ucitelj")
@ApplicationScoped
public class UciteljVir {

    @Inject
    private UciteljZrno uciteljZrno;

    @GET
    @Path("{id}")
    public Response getUcitelj(@PathParam("id") Integer id) {
        return Response.ok(uciteljZrno.getUcitelj(id)).build();
    }
}

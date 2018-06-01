package rest.viri;

import zrna.uporabniki.ReferentZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("referent")
@ApplicationScoped
public class ReferentVir {

    @Inject private ReferentZrno referentZrno;

    @GET
    @Path("{id}")
    public Response getReferent(@PathParam("id") Integer id) {

        return Response.ok(referentZrno.getReferent(id)).build();
    }
}

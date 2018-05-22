package rest.viri;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import authentication.Auth;
import authentication.Role;
import common.CustomErrorMessage;
import izpit.Izpit;
import zrna.izpit.IzpitZrno;

@Path("izpit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzpitVir {

    private final static Logger log = Logger.getLogger(IzpitVir.class.getName());

    @Inject private IzpitZrno izpitZrno;

    @POST
    @Path("rok/{id}/rezultati")
    public Response vnesiRezultate(@PathParam("id") Integer rokId, List<Izpit> izpit) {
        try {
            izpitZrno.vnesiRezultateIzpita(izpit, rokId);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("rok/{id}/rezultati")
    public Response vrniZeVpisaneOcene(@PathParam("id") Integer rokId) {
        List<Izpit> izpiti = izpitZrno.vrniVneseneRezultate(rokId);
        return Response.ok().entity(izpiti).build();
    }


    @GET
    @Auth(rolesAllowed = { Role.REFERENT, Role.PREDAVATELJ})
    @Path("prijavljeni-ocene")
    public Response vrniPrijavljeneKandidateZOcenami(@QueryParam("sifra-roka") Integer sifraRoka) {
        try {
            return Response.ok(izpitZrno.vrniPrijavljeneKandidateZOcenami(sifraRoka)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }
    }
}

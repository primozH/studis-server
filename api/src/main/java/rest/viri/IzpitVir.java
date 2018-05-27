package rest.viri;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import authentication.Auth;
import authentication.Role;
import common.CustomErrorMessage;
import helpers.entities.PrijavaNaIzpit;
import izpit.IzpitniRok;
import vloge.Uporabnik;
import zrna.izpit.IzpitZrno;
import zrna.izpit.PrijavaNaIzpitZrno;

@Path("izpit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzpitVir {

    private final static Logger log = Logger.getLogger(IzpitVir.class.getName());

    @Inject private IzpitZrno izpitZrno;
    @Inject private PrijavaNaIzpitZrno prijavaNaIzpitZrno;

    @POST
    @Path("rok/{id}/rezultati")
    public Response vnesiRezultate(@PathParam("id") Integer rokId, List<PrijavaNaIzpit> izpiti) {
        try {
            izpitZrno.vnesiRezultateIzpita(izpiti, rokId);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("rok/{id}/rezultati")
    public Response vrniZeVpisaneOcene(@PathParam("id") Integer rokId,
                                       @QueryParam("count") Boolean count) {
        if (count != null && count) {
            return Response.ok()
                    .header("X-Total-Count", prijavaNaIzpitZrno.vrniPrijavljeneStudenteCount(rokId)).build();
        }

        List<PrijavaNaIzpit> prijaveZRezultati = prijavaNaIzpitZrno.vrniPrijavljeneStudenteZOcenami(rokId);

        return Response.ok(prijaveZRezultati).header("X-Total-Count", prijaveZRezultati.size()).build();
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

    @Auth(rolesAllowed = {Role.REFERENT, Role.PREDAVATELJ})
    @Path("koncna")
    @POST
    public Response vnesiKoncnoOcenoZaIzpit(PrijavaNaIzpit prijavaNaIzpit,
                                            @Context HttpServletRequest httpServletRequest) {
        Uporabnik uporabnik = (Uporabnik) httpServletRequest.getAttribute("user");
        Role uporabnikTip = (Role) httpServletRequest.getAttribute("role");
        if (uporabnikTip == Role.PREDAVATELJ) {
            IzpitniRok izpitniRok = prijavaNaIzpitZrno.getExam(prijavaNaIzpit.getPrijavaRok());
            if (izpitniRok.getIzvajalec().getId() != uporabnik.getId())
                return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            return Response.ok(izpitZrno.vnesiKoncnoOceno(prijavaNaIzpit)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }
    }


}

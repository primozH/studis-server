package rest.viri;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import authentication.Auth;
import authentication.Role;
import common.CustomErrorMessage;
import helpers.entities.PrijavaNaIzpit;
import izpit.Izpit;
import vloge.Student;
import vloge.Ucitelj;
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

    @DELETE
    @Auth(rolesAllowed = {Role.REFERENT, Role.PREDAVATELJ})
    @Path("rok/{id}/rezultati/{student}")
    public Response vrniPrijavo(@PathParam("id") Integer rokId,  @PathParam("student")
            Integer studentId, @Context HttpServletRequest httpServletRequest) {
        try {
            Uporabnik uporabnik = (Uporabnik) httpServletRequest.getAttribute("user");
            izpitZrno.vrniPrijavo(rokId, studentId, uporabnik);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }

        return Response.ok().build();
    }

    @GET
    @Auth(rolesAllowed = { Role.REFERENT, Role.PREDAVATELJ})
    @Path("prijavljeni-ocene")
    public Response vrniPrijavljeneKandidateZOcenami(@QueryParam("sifra-roka") Integer sifraRoka) {
        try {
            return Response.ok(izpitZrno.vrniPrijavljeneKandidateZOcenami(sifraRoka)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }
    }

    @POST
    @Auth(rolesAllowed = {Role.REFERENT, Role.PREDAVATELJ})
    @Path("koncna")
    public Response vnesiKoncnoOcenoZaIzpit(Izpit izpit,
                                            @Context HttpServletRequest httpServletRequest) {
        Uporabnik uporabnik = (Uporabnik) httpServletRequest.getAttribute("user");
        Role uporabnikTip = (Role) httpServletRequest.getAttribute("role");
        try {
            if (uporabnikTip == Role.PREDAVATELJ) {
                izpit.getPrijavaRok().getRok().getIzvajanjePredmeta().setNosilec1((Ucitelj) uporabnik);
                if (prijavaNaIzpitZrno.getExamExecution(izpit.getPrijavaRok().getRok().getIzvajanjePredmeta()) == null)
                    return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            return Response.ok(izpitZrno.vnesiKoncnoOceno(izpit)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }
    }

    @GET
    @Auth(rolesAllowed = {Role.STUDENT, Role.REFERENT, Role.PREDAVATELJ})
    @Path("{id}/opravljeni")
    public Response vrniSeznamOpravljenihIzpitovZaLeto(@PathParam("id") Integer studentId,
                                                 @Context HttpServletRequest httpServletRequest) {
        Uporabnik uporabnik = (Uporabnik) httpServletRequest.getAttribute("user");
        Role uporabnikTip = (Role) httpServletRequest.getAttribute("role");
        try {
            Student student = prijavaNaIzpitZrno.getStudent(studentId);
            if (uporabnikTip == Role.STUDENT) {
                if (student.getId() != uporabnik.getId()) {
                    throw new Exception("Student lahko izpise ocene le zase");
                }
            }
            return Response.ok(izpitZrno.vrniOpravljeneIzpite(student)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }
    }
}

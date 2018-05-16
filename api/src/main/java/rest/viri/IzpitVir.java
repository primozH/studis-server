package rest.viri;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import common.IzpitniRokJson;
import org.json.JSONObject;

import authentication.Auth;
import authentication.Role;
import common.CustomErrorMessage;
import common.PrijavniPodatki;
import helpers.PrijavniPodatkiIzpit;
import izpit.Izpit;
import izpit.IzpitniRok;
import izpit.PrijavaRok;
import izpit.StatusRazpisaRoka;
import prijava.Prijava;
import sifranti.Predmet;
import vloge.Student;
import vloge.Uporabnik;
import zrna.izpit.IzpitZrno;
import zrna.izpit.IzpitniRokZrno;
import zrna.izpit.PrijavaNaIzpitZrno;

@Path("izpit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzpitVir {

    private final static Logger log = Logger.getLogger(IzpitVir.class.getName());

    @Inject private IzpitZrno izpitZrno;

    @POST
    @Path("stevilo-polaganj")
    public Response vrniSteviloVsehPolaganjPredmetaZaStudenta(@QueryParam("predmet") Integer predmet,
                                                              @QueryParam("studijsko-leto") Integer studijskoLeto,
                                                              Student student) {
        int studentId;
        try {
            studentId = student.getId();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().entity(izpitZrno.vrniSteviloVsehPolaganjPredmetaZaStudenta(studentId, predmet)).build();
    }

    @POST
    @Path("izpit-za-leto")
    public Response vrniIzpitZaLeto(@QueryParam("predmet") Integer predmet,
                                    @QueryParam("studijsko-leto") Integer studijskoLeto,
                                    Student student) {
        int studentId;
        try {
            studentId = student.getId();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().entity(izpitZrno.vrniIzpitZaLeto(studentId, predmet, studijskoLeto)).build();
    }

    @POST
    @Path("{id}/rezultati")
    public Response vnesiRezultateVpisnegaDela(@PathParam("id") Integer rokId, Izpit izpit) {
        int studentId;
        try {
            studentId = izpit.getStudent().getId();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (izpitZrno.vnesiRezultatIzpita(izpit, rokId)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @GET
    @Path("vrni-vpisane-ocene")
    public Response vrniZeVpisaneOcene(@QueryParam("predmet") Integer predmet,
                                       @QueryParam("studijsko-leto") Integer studijskoLeto) {
        List<Izpit> izpiti = izpitZrno.vrniZeVneseneRezultateIzpita(predmet, studijskoLeto);
        if (izpiti != null) {
            return Response.ok().entity(izpiti).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @POST
    @Path("razveljavi-oceno")
    public Response razveljaviOceno(@QueryParam("predmet") Integer predmet,
                                    @QueryParam("studijsko-leto") Integer studijskoLeto,
                                    Student student) {
        int studentId;
        try {
            studentId = student.getId();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (izpitZrno.razveljaviOceno(predmet, studentId, studijskoLeto)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
}

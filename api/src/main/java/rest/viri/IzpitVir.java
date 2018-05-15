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
    @Inject private PrijavaNaIzpitZrno prijavaNaIzpitZrno;
    @Inject private IzpitniRokZrno izpitniRokZrno;

    @POST
    @Path("prijava")
    @Auth(rolesAllowed = {Role.STUDENT})
    public Response prijavaNaIzpit(PrijavaRok prijavaRok,
                                   @Context HttpServletRequest httpServletRequest) {
        PrijavaRok prijava;
        try {
            Uporabnik uporabnik = (Uporabnik) httpServletRequest.getAttribute("user");
            prijava = prijavaNaIzpitZrno.applyForExam(prijavaRok, uporabnik);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }

        return Response.status(Response.Status.CREATED).entity(prijava).build();
    }

    @POST
    @Path("odjava")
    @Auth(rolesAllowed = {Role.STUDENT})
    public Response odjavaOdIzpita(PrijavaRok prijavaRok, @Context HttpServletRequest request) {
        try {
            Uporabnik uporabnik = (Uporabnik) request.getAttribute("user");
            prijavaNaIzpitZrno.returnApplication(prijavaRok, uporabnik);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }

        return Response.ok().build();
    }

    @GET
    @Path("prijavljeni")
    public Response vrniPrijavljeneStudente(@QueryParam("predmet") Integer predmet,
                                            @QueryParam("studijsko-leto") Integer studijskoLeto,
                                            @QueryParam("datum") String datum) {
        List<PrijavaRok> prijavaRok = prijavaNaIzpitZrno.vrniPrijavljeneStudente(predmet,
                studijskoLeto,
                LocalDate.parse(datum));
        return Response.ok(prijavaRok).header("X-Total-Count", prijavaRok.size()).build();
    }

    @GET
    @Path("rok")
    @Auth(rolesAllowed = {Role.REFERENT, Role.PREDAVATELJ, Role.STUDENT})
    public Response vrniRokeZaPredmet(@QueryParam("predmet") Integer predmet,
                                      @QueryParam("studijsko-leto") Integer studijskoLeto,
                                      @Context HttpServletRequest httpServletRequest) {

        Uporabnik uporabnik = (Uporabnik) httpServletRequest.getAttribute("user");

        List<IzpitniRok> izpitniRoki = izpitniRokZrno.vrniIzpitneRoke(uporabnik, predmet);

        if (izpitniRoki.size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        Role uporabnikTip = (Role) httpServletRequest.getAttribute("role");

        if (uporabnikTip == Role.REFERENT || uporabnikTip == Role.PREDAVATELJ) {
            return Response.ok(izpitniRoki).header("X-Total-Count", izpitniRoki.size()).build();
        }

        List<PrijavaRok> prijave = prijavaNaIzpitZrno.vrniPrijaveNaIzpit(uporabnik);

        List<IzpitniRokJson> rokiZaStudenta = izpitniRoki.stream()
                .map(rok -> rok.getIzvajanjePredmeta().getPredmet())
                .distinct()
                .map(IzpitniRokJson::new)
                .collect(Collectors.toList());

        for (IzpitniRokJson prijava : rokiZaStudenta) {
            List<IzpitniRok> tmp = izpitniRoki.stream()
                    .filter(rok -> rok
                            .getIzvajanjePredmeta()
                            .getPredmet()
                            .getSifra()
                            .equals(prijava.getPredmet().getSifra()))
                    .collect(Collectors.toList());
            prijava.setRoki(tmp);

            List<IzpitniRok> prijavljen = prijave.stream()
                    .filter(prijavaRok ->
                            prijavaRok
                                    .getRok()
                                    .getIzvajanjePredmeta()
                                    .getPredmet()
                                    .getSifra()
                                    .equals(prijava.getPredmet().getSifra()))
                    .map(PrijavaRok::getRok)
                    .collect(Collectors.toList());
            if (prijavljen.size() != 0) {
                prijava.setPrijavljenId(prijavljen.get(0).getId());
            }
        }

        return Response.ok().entity(rokiZaStudenta).header("X-Total-Count", rokiZaStudenta.size()).build();
    }

    @POST
    @Path("rok")
    @Auth(rolesAllowed = {Role.PREDAVATELJ, Role.REFERENT})
    public Response vnesiRokZaPredmet(IzpitniRok rok,
                                      @Context HttpServletRequest request) {
        Uporabnik uporabnik = (Uporabnik) request.getAttribute("user");
        try {
            IzpitniRok izpitniRok = izpitniRokZrno.vnesiIzpitniRok(rok, uporabnik);
            return Response.status(Response.Status.CREATED).entity(izpitniRok).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new CustomErrorMessage(e.getMessage())).build();
        }
    }

    @PUT
    @Path("rok")
    @Auth(rolesAllowed = {Role.PREDAVATELJ, Role.REFERENT})
    public Response posodobiIzpitniRok(IzpitniRok rok,
                                       @Context HttpServletRequest request) {
        Uporabnik uporabnik = (Uporabnik) request.getAttribute("user");
        try {
            IzpitniRok izpitniRok = izpitniRokZrno.spremeniIzpitniRok(rok, uporabnik);
            return Response.ok(izpitniRok).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("rok")
    @Auth(rolesAllowed = {Role.PREDAVATELJ, Role.REFERENT})
    public Response brisiIzpitniRok(IzpitniRok rok,
                                    @Context HttpServletRequest request) {
        Uporabnik uporabnik = (Uporabnik) request.getAttribute("user");
        try {
            izpitniRokZrno.izbrisiRok(rok, uporabnik);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }
    }

    @GET
    @Path("prijave")
    @Auth(rolesAllowed = {Role.STUDENT})
    public Response vrniPrijaveNaIzpit(@QueryParam("studijsko-leto") Integer studijskoLeto,
                                       @Context HttpServletRequest httpServletRequest) {
        Uporabnik uporabnik = (Uporabnik) httpServletRequest.getAttribute("user");
        List<PrijavaRok> prijave = prijavaNaIzpitZrno.vrniPrijaveNaIzpit(uporabnik);

        return Response.ok(prijave).header("X-Total-Count", prijave != null ? prijave.size() : 0).build();
    }

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
    @Path("zadnja-prijava")
    public Response vrniZadnjoPrijavoZaPredmet(@QueryParam("predmet") Integer predmet,
                                               Student student) {
        int studentId;
        try {
            studentId = student.getId();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().entity(prijavaNaIzpitZrno.vrniZadnjoPrijavoZaPredmet(studentId, predmet)).build();
    }

    @POST
    @Path("vnos-rezultatov")
    public Response vnesiRezultateVpisnegaDela(@QueryParam("predmet") Integer predmet,
                                               @QueryParam("studijsko-leto") Integer studijskoLeto,
                                               Izpit izpit) {
        int studentId;
        try {
            studentId = izpit.getStudent().getId();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (izpitZrno.vnesiRezultatIzpita(izpit, predmet, studentId, studijskoLeto)) {
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

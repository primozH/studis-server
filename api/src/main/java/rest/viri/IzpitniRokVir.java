package rest.viri;

import authentication.Auth;
import authentication.Role;
import common.CustomErrorMessage;
import common.IzpitniRokJson;
import izpit.IzpitniRok;
import izpit.PrijavaRok;
import vloge.Uporabnik;
import zrna.izpit.IzpitniRokZrno;
import zrna.izpit.PrijavaNaIzpitZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("rok")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzpitniRokVir {

    private final static Logger log = Logger.getLogger(IzpitniRokVir.class.getName());

    @Inject private IzpitniRokZrno izpitniRokZrno;
    @Inject private PrijavaNaIzpitZrno prijavaNaIzpitZrno;

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
    @Auth(rolesAllowed = {Role.STUDENT, Role.PREDAVATELJ, Role.REFERENT})
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
    @Auth(rolesAllowed = {Role.REFERENT, Role.PREDAVATELJ, Role.STUDENT})
    public Response vrniRoke(@QueryParam("predmet") Integer predmet,
                                      @QueryParam("studijsko-leto") Integer studijskoLeto,
                                      @Context HttpServletRequest httpServletRequest) {

        Uporabnik uporabnik = (Uporabnik) httpServletRequest.getAttribute("user");

        if (studijskoLeto == null) {
            studijskoLeto = LocalDate.now().getYear();
        }
        List<IzpitniRok> izpitniRoki = izpitniRokZrno.vrniIzpitneRoke(uporabnik, predmet, studijskoLeto);

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
    @Auth(rolesAllowed = {Role.PREDAVATELJ, Role.REFERENT})
    public Response vnesiRokZaPredmet(IzpitniRok rok, @Context HttpServletRequest request) {
        Uporabnik uporabnik = (Uporabnik) request.getAttribute("user");
        try {
            IzpitniRok izpitniRok = izpitniRokZrno.vnesiIzpitniRok(rok, uporabnik);
            return Response.status(Response.Status.CREATED).entity(izpitniRok).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new CustomErrorMessage(e.getMessage())).build();
        }
    }

    @PUT
    @Auth(rolesAllowed = {Role.PREDAVATELJ, Role.REFERENT})
    public Response posodobiIzpitniRok(IzpitniRok rok, @Context HttpServletRequest request) {
        Uporabnik uporabnik = (Uporabnik) request.getAttribute("user");
        try {
            IzpitniRok izpitniRok = izpitniRokZrno.spremeniIzpitniRok(rok, uporabnik);
            return Response.ok(izpitniRok).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Auth(rolesAllowed = {Role.PREDAVATELJ, Role.REFERENT})
    public Response brisiIzpitniRok(@PathParam("id") Integer rokId, @Context HttpServletRequest request) {
        Uporabnik uporabnik = (Uporabnik) request.getAttribute("user");
        try {
            izpitniRokZrno.izbrisiRok(rokId, uporabnik);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }
    }

    @GET
    @Path("{id}/prijavljeni")
    public Response vrniPrijavljeneStudente(@PathParam("id") Integer rokId,
                                            @QueryParam("count") Boolean count) {
        if (count != null && count) {
            return Response.ok()
                    .header("X-Total-Count", prijavaNaIzpitZrno.vrniPrijavljeneStudenteCount(rokId)).build();
        }
        List<PrijavaRok> prijavaRok = prijavaNaIzpitZrno.vrniPrijavljeneStudente(rokId);
        return Response.ok(prijavaRok).header("X-Total-Count", prijavaRok.size()).build();
    }

}

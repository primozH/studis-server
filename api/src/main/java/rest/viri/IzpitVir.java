package rest.viri;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import authentication.Auth;
import authentication.Role;
import common.CustomErrorMessage;
import common.PrijavniPodatki;
import helpers.PrijavniPodatkiIzpit;
import izpit.Izpit;
import izpit.IzpitniRok;
import izpit.PrijavaIzpit;
import vloge.Uporabnik;
import zrna.IzpitZrno;

@Path("izpit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzpitVir {

    @Inject
    private IzpitZrno izpitZrno;

    @POST
    @Path("prijava")
    public Response prijavaNaIzpit(PrijavniPodatki prijavniPodatki) {
        try {
            izpitZrno.applyForExam(new PrijavniPodatkiIzpit(
                    prijavniPodatki.getStudent(),
                    prijavniPodatki.getPredmet(),
                    prijavniPodatki.getStudijskoLeto(),
                    prijavniPodatki.getDatumIzvajanja()));
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }

        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("odjava")
    public Response odjavaOdIzpita(PrijavniPodatki prijavniPodatki) {
        try {
            izpitZrno.returnApplication(new PrijavniPodatkiIzpit(
                    prijavniPodatki.getStudent(),
                    prijavniPodatki.getPredmet(),
                    prijavniPodatki.getStudijskoLeto(),
                    prijavniPodatki.getDatumIzvajanja()));
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomErrorMessage(e.getMessage())).build();
        }

        return Response.ok().build();
    }

    @GET
    @Path("prijavljeni")
    public Response vrniPrijavljeneStudente(@QueryParam("predmet") Integer predmet,
                                            @QueryParam("studijsko-leto") Integer studijskoLeto,
                                            @QueryParam("datum-cas") String datum_cas) {
        List<PrijavaIzpit> prijavaIzpit = izpitZrno.vrniPrijavljeneStudente(predmet,
                studijskoLeto,
                LocalDateTime.parse(datum_cas));
        return Response.ok(prijavaIzpit).build();
    }

    @GET
    @Path("rok")
    @Auth(rolesAllowed = {Role.REFERENT, Role.PREDAVATELJ, Role.STUDENT})
    public Response vrniRokeZaPredmet(@QueryParam("predmet") Integer predmet,
                                      @QueryParam("studijsko-leto") Integer studijskoLeto,
                                      Uporabnik uporabnik) {

        List<IzpitniRok> izpitniRoki = izpitZrno.vrniIzpitneRoke(uporabnik.getId(), predmet, studijskoLeto);
        if (izpitniRoki != null && !izpitniRoki.isEmpty())
            return Response.ok().entity(izpitniRoki).header("X-Total-Count", izpitniRoki.size()).build();
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("prijave")
    @Auth(rolesAllowed = {Role.STUDENT})
    public Response vrniPrijaveNaIzpit(@QueryParam("studijsko-leto") Integer studijskoLeto,
                                       Uporabnik uporabnik) {
        List<PrijavaIzpit> prijave = izpitZrno.vrniPrijaveNaIzpit(uporabnik, studijskoLeto);

        return Response.ok(prijave).header("X-Total-Count", prijave != null ? prijave.size() : 0).build();
    }

    @POST
    @Path("stevilo-polaganj")
    public Response vrniSteviloVsehPolaganjPredmetaZaStudenta(PrijavaIzpit prijavaIzpit) {
        int sifraPredmeta;
        int studentId;
        try {
            sifraPredmeta = prijavaIzpit.getPredmetStudent().getPredmet().getSifra();
            studentId = prijavaIzpit.getPredmetStudent().getVpis().getStudent().getId();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().entity(izpitZrno.vrniSteviloVsehPolaganjPredmetaZaStudenta(studentId, sifraPredmeta)).build();
    }

    @POST
    @Path("izpit-za-leto")
    public Response vrniIzpitZaLeto(PrijavaIzpit prijavaIzpit) {
        int sifraPredmeta, studentId, studijskoLeto;
        try {
            sifraPredmeta = prijavaIzpit.getPredmetStudent().getPredmet().getSifra();
            studentId = prijavaIzpit.getPredmetStudent().getVpis().getStudent().getId();
            studijskoLeto = prijavaIzpit.getPredmetStudent().getVpis().getStudijskoLeto().getId();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().entity(izpitZrno.vrniIzpitZaLeto(studentId, sifraPredmeta, studijskoLeto)).build();
    }

    @POST
    @Path("zadnja-prijava")
    public Response vrniZadnjoPrijavoZaPredmet(PrijavaIzpit prijavaIzpit) {
        int sifraPredmeta;
        int studentId;
        try {
            sifraPredmeta = prijavaIzpit.getPredmetStudent().getPredmet().getSifra();
            studentId = prijavaIzpit.getPredmetStudent().getVpis().getStudent().getId();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().entity(izpitZrno.vrniZadnjoPrijavoZaPredmet(studentId, sifraPredmeta)).build();
    }

//    @POST
//    @Path("brisi-prijavo")
//    public Response brisiPrijavoNaIzpit(PrijavaIzpit prijavaIzpit) {
//        int sifraPredmeta, studentId, studijskoLeto;
//        try {
//            sifraPredmeta = prijavaIzpit.getPredmetStudent().getPredmet().getSifra();
//            studentId = prijavaIzpit.getPredmetStudent().getVpis().getStudent().getId();
//            studijskoLeto = prijavaIzpit.getPredmetStudent().getVpis().getStudijskoLeto().getId();
//        } catch (NullPointerException e) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//        boolean potrjeno = izpitZrno.odjavaOdIzpita(studentId, sifraPredmeta, studijskoLeto);
//        if (!potrjeno) {
//            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
//        }
//        return Response.ok().entity(prijavaIzpit).build();
//    }

    @POST
    @Path("vnos-rezultatov")
    public Response vnesiRezultateVpisnegaDela(Izpit izpit) {
        int sifraPredmeta, studentId, studijskoLeto;
        try {
            sifraPredmeta = izpit.getPrijavaIzpit().getPredmetStudent().getPredmet().getSifra();
            studentId = izpit.getPrijavaIzpit().getPredmetStudent().getVpis().getStudent().getId();
            studijskoLeto = izpit.getPrijavaIzpit().getPredmetStudent().getVpis().getStudijskoLeto().getId();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (izpitZrno.vnesiRezultatIzpita(izpit, sifraPredmeta, studentId, studijskoLeto)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @POST
    @Path("vrni-vpisane-ocene")
    public Response vrniZeVpisaneOcene(Izpit izpit) {
        int sifraPredmeta, studijskoLeto;
        try {
            sifraPredmeta = izpit.getPrijavaIzpit().getPredmetStudent().getPredmet().getSifra();
            studijskoLeto = izpit.getPrijavaIzpit().getPredmetStudent().getVpis().getStudijskoLeto().getId();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<Izpit> izpiti = izpitZrno.vrniZeVneseneRezultateIzpita(sifraPredmeta, studijskoLeto);
        if (izpiti != null) {
            return Response.ok().entity(izpiti).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @POST
    @Path("razveljavi-oceno")
    public Response razveljaviOceno(Izpit izpit) {
        int sifraPredmeta, studentId, studijskoLeto;
        try {
            sifraPredmeta = izpit.getPrijavaIzpit().getPredmetStudent().getPredmet().getSifra();
            studentId = izpit.getPrijavaIzpit().getPredmetStudent().getVpis().getStudent().getId();
            studijskoLeto = izpit.getPrijavaIzpit().getPredmetStudent().getVpis().getStudijskoLeto().getId();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (izpitZrno.razveljaviOceno(sifraPredmeta, studentId, studijskoLeto)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
}

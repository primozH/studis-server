package rest.viri;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import common.CustomErrorMessage;
import common.PrijavniPodatki;
import helpers.PrijavniPodatkiIzpit;
import izpit.IzpitniRok;
import izpit.PrijavaIzpit;
import vloge.Student;
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
    @Path("roki")
    public Response vrniRokeZaPredmet(PrijavaIzpit prijavaIzpit) {
        int sifraPredmeta;
        try {
            sifraPredmeta = prijavaIzpit.getPredmetStudent().getPredmet().getSifra();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<IzpitniRok> izpitniRoki = izpitZrno.vrniRokeZaPredmet(sifraPredmeta);
        if (izpitniRoki != null && !izpitniRoki.isEmpty())
            return Response.ok().entity(izpitniRoki).build();
        return Response.status(Response.Status.NO_CONTENT).build();
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
//        boolean potrjeno = izpitZrno.vrniPrijavoZaPredmet(studentId, sifraPredmeta, studijskoLeto);
//        if (!potrjeno) {
//            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
//        }
//        return Response.ok().entity(prijavaIzpit).build();
//    }

    @POST
    @Path("prijavljeni")
    public Response vrniPrijavljeneStudente(PrijavaIzpit prijavaIzpit) {
        int sifraPredmeta, studentId, studijskoLeto;
        try {
            sifraPredmeta = prijavaIzpit.getPredmetStudent().getPredmet().getSifra();
            studentId = prijavaIzpit.getPredmetStudent().getVpis().getStudent().getId();
            studijskoLeto = prijavaIzpit.getPredmetStudent().getVpis().getStudijskoLeto().getId();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<Student> prijavljeniStudenti = izpitZrno.vrniPrijavljeneStudente(sifraPredmeta, studentId, studijskoLeto);
        if (prijavljeniStudenti != null && !prijavljeniStudenti.isEmpty()) {
            return Response.ok().entity(prijavljeniStudenti).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

//    @POST
//    @Path("odjava")
//    public Response odjavaOdIzpita(PrijavniPodatki prijavniPodatki) {
//
//        izpitZrno.returnApplication(prijavniPodatki);
//    }
}

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

import izpit.Izpit;
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

    @POST
    @Path("brisi-prijavo")
    public Response brisiPrijavoNaIzpit(PrijavaIzpit prijavaIzpit) {
        int sifraPredmeta, studentId, studijskoLeto;
        try {
            sifraPredmeta = prijavaIzpit.getPredmetStudent().getPredmet().getSifra();
            studentId = prijavaIzpit.getPredmetStudent().getVpis().getStudent().getId();
            studijskoLeto = prijavaIzpit.getPredmetStudent().getVpis().getStudijskoLeto().getId();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        boolean potrjeno = izpitZrno.odjavaOdIzpita(studentId, sifraPredmeta, studijskoLeto);
        if (!potrjeno) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        return Response.ok().entity(prijavaIzpit).build();
    }

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
        List<Student> studenti = izpitZrno.vrniZeVneseneRezultateIzpita(izpit, sifraPredmeta, studijskoLeto);
        if (studenti != null) {
            return Response.ok().entity(studenti).build();
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

package rest.viri;

import static javax.ws.rs.core.Response.Status.OK;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import vloge.Ucitelj;
import vloge.Uporabnik;
import zrna.PredmetZrno;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("predmet")
public class PredmetVir {

    @Inject
    PredmetZrno predmetZrno;

    @POST
    public Response vrniPodatkeOPredmetu(Uporabnik uporabnik,
                                         @QueryParam("sifra-predmeta") Integer sifraPredmeta,
                                         @QueryParam("studijsko-leto") Integer studijskoLeto) {
        if (uporabnik.getTip().equals("Referent")) {
            return Response.status(OK).entity(predmetZrno.vrniListoStudentovZaPredmet(sifraPredmeta, studijskoLeto)).build();
        } else if (uporabnik.getTip().equals("Ucitelj")) {

            return Response.status(OK)
                           .entity(predmetZrno.vrniListoStudentovPredmetaZaUcitelja(sifraPredmeta, (Ucitelj) uporabnik, studijskoLeto))
                           .build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}

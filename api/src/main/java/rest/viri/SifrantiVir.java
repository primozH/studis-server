package rest.viri;

import naslov.Drzava;
import naslov.Posta;
import zrna.SifrantiZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("sifranti")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SifrantiVir {

    @Inject
    private SifrantiZrno sifrantiZrno;

    @Path("drzava")
    @GET
    public Response getCountryList() {
        List<Drzava> drzave = sifrantiZrno.getCountryList();
        return Response.ok(drzave).build();
    }

    @Path("posta")
    @GET
    public Response getPostsList() {
        List<Posta> poste = sifrantiZrno.getPostList();
        return Response.ok(poste).build();
    }

}

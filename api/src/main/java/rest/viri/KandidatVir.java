package rest.viri;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import student.Zeton;
import vloge.Kandidat;
import zrna.KandidatZrno;
import zrna.UvozPodatkov;
import zrna.VpisZrno;
import zrna.ZetonZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.logging.Logger;

@Path("/kandidat")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class KandidatVir {

    private static final Logger logger = Logger.getLogger(KandidatVir.class.getName());

    @Inject
    private UvozPodatkov uvozPodatkov;

    @Inject
    private KandidatZrno kandidatZrno;

    @Inject
    private ZetonZrno zetonZrno;

    @GET
    public Response getKandidati() {
        List<Kandidat> kandidati = kandidatZrno.getKandidats();
        return Response.ok(kandidati).build();
    }

    @GET
    @Path("{kandidat}")
    public Response getKandidat(@PathParam("kandidat") Integer studentId) {
        Kandidat kandidat = kandidatZrno.getKandidat(studentId);

        return Response.ok(kandidat).build();
    }

    @GET
    @Path("neuspesni")
    @Produces(MediaType.TEXT_PLAIN)
    public Response downloadImportedCandidates() {
        File file = uvozPodatkov.downloadFile();
        return Response.ok(file).header("Content-Disposition", "attachment; filename=\"neuspesno_uvozeni.txt\"").build();
    }

    @POST
    @Path("/nalozi")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadFile(
            @FormDataParam("file") InputStream content,
            @FormDataParam("file") FormDataContentDisposition fdcd) {

        List<Kandidat> kandidatList = uvozPodatkov.importData(content);

        return Response.accepted(kandidatList).header("X-Total-Count", kandidatList.size()).build();

    }
}



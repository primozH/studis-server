package rest.viri;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import zrna.UvozPodatkov;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.logging.Logger;

@Path("/kandidat")
@ApplicationScoped
public class KandidatVir {

    private static final Logger logger = Logger.getLogger(KandidatVir.class.getName());

    private static final String FILE_LOCATION = "./podatki_kandidat.txt";

    @Inject
    private UvozPodatkov uvozPodatkov;

    @GET
    @Path("seznam")
    public Response getImportStatus() {
        return Response.ok().build();
    }

    @GET
    @Path("uvozeni")
    @Produces(MediaType.TEXT_PLAIN)
    public Response downloadImportedCandidates() {
        File file = new File(FILE_LOCATION);
        return Response.ok(file).build();
    }

    @POST
    @Path("/nalozi")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response postOctetStream(@FormDataParam("file") InputStream content,
                                    @FormDataParam("file")FormDataContentDisposition fdcd) {

        InputStreamReader reader = new InputStreamReader(content);
        int count = 0;
        char [] buffer = new char[1024];
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(FILE_LOCATION));

            while ( (count = reader.read(buffer)) > 0) {
                out.write(buffer);
            }

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        uvozPodatkov.parseFile(new File(FILE_LOCATION));

        return Response.accepted().build();

    }
}



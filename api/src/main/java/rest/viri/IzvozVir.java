package rest.viri;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import orodja.FileExporter;
import orodja.export.Document;

@Path("izvoz")
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzvozVir {
    private final static Logger log = Logger.getLogger(IzvozVir.class.getName());

    @Inject private FileExporter fileExporter;

    @POST
    public Response exportToPdf(Document document) {
        log.info("Zahteva za izvoz podatkov v datoteko");
        File file = fileExporter.createFile(document);

        String contentType = "";
        switch (document.getDocumentType()) {
            case PDF:
                contentType = "application/pdf";
                break;
            case CSV:
                contentType = "text/csv";
                break;
        }

        return Response.ok(file)
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .header("Content-Type", contentType)
                .build();
    }

    @POST
    @Path("multiple")
    public Response exportDocumentsToPdf(List<Document> documents) {
        log.info("Zahteva za izvoz vec dokumentov v datoteko");
        File file = fileExporter.createFile(documents);
        String contentType = "";
        switch (documents.get(0).getDocumentType()) {
            case PDF:
                contentType = "application/pdf";
                break;
            case CSV:
                contentType = "text/csv";
                break;
        }

        return Response.ok(file)
                       .header("Content-Disposition", "attachment; filename=" + file.getName())
                       .header("Content-Type", contentType)
                       .build();
    }

    @GET
    @Path("kartoteka/{id}")
    public Response exportKartoteka(@PathParam("id") Integer studentId,
                                    @QueryParam("expanded") Boolean expanded) {
        log.info("Zahteva za izvoz kartotecnega lista");
        File file = fileExporter.createKartoteka(studentId, expanded);

        return Response.ok(file)
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .header("Content-Type", "application/csv")
                .build();
    }
}

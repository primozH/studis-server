package rest.viri;

import orodja.export.Document;
import orodja.FileExporter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("izvoz")
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzvozVir {

    @Inject private FileExporter fileExporter;

    @POST
    public Response exportToPdf(Document document) {
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
}

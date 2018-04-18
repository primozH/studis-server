package orodja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.ws.rs.core.Response;

import org.xhtmlrenderer.pdf.ITextRenderer;

public class HtmlToPdfConverter {

    public static Response generatePDF(String inputHtmlPath, String outputPdfPath) {
        try {
            String url = new File(inputHtmlPath).toURI().toURL().toString();
            System.out.println("URL: " + url);

            OutputStream out = new FileOutputStream(outputPdfPath);

            //Flying Saucer part
            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocument(url);
            renderer.layout();
            renderer.createPDF(out);

            out.close();

            File file = new File(outputPdfPath);
            FileInputStream fileInputStream = new FileInputStream(file);
            javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
            responseBuilder.type("application/pdf");
            responseBuilder.header("Content-Disposition", "filename=test.pdf");
            return responseBuilder.build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

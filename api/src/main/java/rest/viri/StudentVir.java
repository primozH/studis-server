package rest.viri;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import common.CustomErrorMessage;
import orodja.PotrdiloVpisaHTML;
import vloge.Student;
import vpis.Vpis;
import vpis.VpisniList;
import zrna.StudentZrno;
import zrna.VpisZrno;

@Path("/student")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class StudentVir {

    private static final Logger logger = Logger.getLogger(StudentVir.class.getSimpleName());

    @Inject
    private StudentZrno studentZrno;
    @Inject
    private VpisZrno vpisZrno;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") Integer id) {
        logger.info("Iscem studenta z vpisno st. : " + id.toString());
        return Response.ok(studentZrno.getStudent(id)).build();
    }

    @GET
    public Response getStudentsByNameSurnameEnrollmentNumber(@QueryParam("filter") String searchParam) {
        List<Student> students = studentZrno.getStudentsByNSN(searchParam);
        return Response.ok(students).header("X-Total-Count", students.size()).build();
    }

    @PUT
    @Path("{id}")
    public Response updateStudentData(@PathParam("id") Integer studentId, Student student) {
        if (!studentId.equals(student.getId())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            logger.info("Updating student with new data...");
            student = studentZrno.updateStudent(student);
        } catch (Exception e){
            return Response.status(Response.Status.CONFLICT).entity(new CustomErrorMessage(e.getMessage())).build();
        }
        return Response.ok(student).build();
    }

    @POST
    @Path("{id}/vpis")
    public Response vpisiStudenta(@PathParam("id") Integer studentId, VpisniList vpisniList) {
        if (!studentId.equals(vpisniList.getZeton().getStudent().getId()))
            return Response.status(Response.Status.CONFLICT).build();

        Vpis vpis;
        try {
            vpis = vpisZrno.enrollmentProcedure(vpisniList);
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).entity(new CustomErrorMessage(e.getMessage())).build();
        }
        return Response.ok(vpis).build();
    }

    @GET
    @Path("{id}/vpis")
    public Response vrniVpiseZaStudenta(@PathParam("id") Integer studentId) {
        List<Vpis> vpisi = vpisZrno.getVpisi(studentId);

        return Response.ok(vpisi).build();
    }

    @GET
    @Path("{id}/zadnji-vpis")
    public Response vrniZadnjiVpisZaStudenta(@PathParam("id") Integer studentId) {
        Vpis zadnjiVpis = vpisZrno.getVpisi(studentId).get(0);
        if (zadnjiVpis != null) {
            if (zadnjiVpis.isPotrjen()) {
                if (zadnjiVpis.getNacinStudija().getSifra() == 2) {
                    return Response.status(Response.Status.CONFLICT).entity(new JSONObject().put("err", 10).put("msg","izredni").toString()).build();
                }
                return Response.ok(zadnjiVpis).build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity(new JSONObject().put("err", 20).put("msg","nepotrjen").toString()).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("seznam-vpisanih")
    public Response vrniSeznamVsehVpisanih() {
        List<Student> vpisaniStudenti = vpisZrno.getVpisaniStudenti();
        if (vpisaniStudenti != null && !vpisaniStudenti.isEmpty()) {
            return Response.ok(vpisaniStudenti).header("X-Total-Count", vpisaniStudenti.size()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("potrdilo")
    @Produces("application/pdf")
    public Response vrniPotrdiloOVpisuZaVpis(Vpis vpis) {
        try {
            String html = PotrdiloVpisaHTML.html;
            html = vpisZrno.zamenjajPodatkeZaPotrdiloVpisa(html, vpis);


            ByteArrayOutputStream baos = new ByteArrayOutputStream(html.getBytes().length);
            baos.write(html.getBytes(), 0, html.getBytes().length);
            PdfDocument pdfDocument = new PdfDocument();
            PdfWriter writer = PdfWriter.getInstance(pdfDocument, baos);
            pdfDocument.addWriter(writer);
            pdfDocument.setPageSize(PageSize.A4);
            if (!pdfDocument.isOpen()) pdfDocument.open();
            pdfDocument.add(new Paragraph(html));
            XMLWorkerHelper workerHelper = XMLWorkerHelper.getInstance();
            ByteArrayInputStream pdfStream = new ByteArrayInputStream(baos.toByteArray());
            workerHelper.parseXHtml(writer, pdfDocument, pdfStream, Charset.forName("UTF-8"));
            pdfDocument.close();
            writer.close();

            javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok(html);
            responseBuilder.type("application/pdf");
            responseBuilder.header("Content-Disposition", "attachment;filename=test.pdf");
            return responseBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @POST
    @Path("potrdi-vpis")
    public Response potrdiVpisZaStudenta(@QueryParam("potrjevalec") Integer potrjevalec,
            Vpis vpis){
        if (!vpisZrno.potrdiVpis(vpis, potrjevalec)) return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        return Response.ok().build();
    }

}

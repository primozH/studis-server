package rest.viri;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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

import authentication.Auth;
import authentication.Role;
import helpers.entities.KartotecniList;
import org.json.JSONObject;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import authentication.Auth;
import authentication.Role;
import common.CustomErrorMessage;
import orodja.PotrdiloVpisaHTML;
import vloge.Student;
import vloge.Uporabnik;
import vpis.Vpis;
import vpis.VpisniList;
import zrna.KartotecniListZrno;
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

    @Inject private KartotecniListZrno kartotecniListZrno;

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

    @GET
    @Path("{id}/potrdilo")
    @Auth(rolesAllowed = { Role.REFERENT})
    @Produces("application/pdf")
    public Response vrniPotrdiloOVpisuZaVpis(@PathParam("id") Integer studentId,
                                             @QueryParam("studijsko-leto") Integer studijskoLeto) {
        try {
            String html = PotrdiloVpisaHTML.html;
            Vpis vpis = vpisZrno.vrniVpis(studentId, studijskoLeto);
            html = vpisZrno.zamenjajPodatkeZaPotrdiloVpisa(html, vpis);

            String imeDatoteke = "potrdilo" + LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss")) + ".pdf";

            Document pdfDocument = new Document();
            PdfWriter writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream(imeDatoteke));
            pdfDocument.setPageSize(PageSize.A4);
            pdfDocument.open();
            CSSResolver cssResolver =
                    XMLWorkerHelper.getInstance().getDefaultCssResolver(false);
            HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
            htmlContext.autoBookmark(false);

            PdfWriterPipeline pdf = new PdfWriterPipeline(pdfDocument, writer);
            HtmlPipeline htmlP = new HtmlPipeline(htmlContext, pdf);
            CssResolverPipeline css = new CssResolverPipeline(cssResolver, htmlP);
            ByteArrayInputStream pdfStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));

            XMLWorker worker = new XMLWorker(css, true);
            XMLParser p = new XMLParser(worker);
            p.parse(pdfStream);

            pdfDocument.close();
            return Response.ok(new File(imeDatoteke))
                    .header("Content-Disposition", "attachment; filename=" + imeDatoteke)
                    .header("Content-Type", "application/pdf")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("{id}/potrdi-vpis")
    @Auth(rolesAllowed = { Role.REFERENT})
    public Response potrdiVpisZaStudenta(@PathParam("id") Integer studentId,
                                         @QueryParam("studijsko-leto") Integer studijskoLeto){
        if (!vpisZrno.potrdiVpis(studentId, studijskoLeto)) return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        return Response.ok().build();
    }

    @GET
    @Path("vrni-nepotrjene-vpise")
    @Auth(rolesAllowed = { Role.REFERENT})
    public Response vrniNepotrjeneVpise() {
        List<Vpis> nepotrjeniVpisi = vpisZrno.vrniNepotrjene();
        if (nepotrjeniVpisi == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().entity(nepotrjeniVpisi).build();
    }

    @GET
    @Path("{id}/kartoteka")
    @Auth(rolesAllowed = {Role.PREDAVATELJ, Role.REFERENT, Role.STUDENT})
    public Response kartotecniList(@PathParam("id") Integer studentId, @Context HttpServletRequest request) {
        KartotecniList kartotecniList;
        boolean allowed = false;
        switch ((Role)request.getAttribute("role")) {
            case PREDAVATELJ:
            case REFERENT:
                allowed = true;
                break;
            case STUDENT:
                Uporabnik student = (Uporabnik) request.getAttribute("user");
                if (studentId.equals(student.getId())) {
                    allowed = true;
                }
        }

        if (allowed) {
            try {
                kartotecniList = kartotecniListZrno.pripraviKartotecniList(studentId);
                return Response.ok(kartotecniList).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return  Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}

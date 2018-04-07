package rest.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.QueryStringDefaults;
import student.Zeton;
import vloge.Student;
import vpis.Vpis;
import vpis.VpisniList;
import zrna.StudentZrno;
import zrna.VpisZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

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

    @POST
    @Path("{id}")
    public Response updateStudentData(@PathParam("id") Integer studentId, Student student) {
        try {
            student = studentZrno.updateStudent(student);
        } catch (Exception e){
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.ok(student).build();
    }

    @POST
    @Path("{id}/vpis")
    public Response vpisiStudenta(@PathParam("id") Integer studentId, Zeton zeton) {
        if (studentId.equals(zeton.getStudent().getId()))
            return Response.status(Response.Status.CONFLICT).build();

        Vpis vpis;
        try {
            vpis = vpisZrno.vpisiStudenta(zeton);
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.ok(vpis).build();
    }
}

package rest.viri;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sifranti.DelPredmetnika;
import sifranti.Predmet;
import vloge.Student;
import vpis.Vpis;
import zrna.PredmetnikStudentZrno;
import zrna.StudentZrno;

@Path("/izbirni")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzbirniPredmetiVir {

    @Inject
    PredmetnikStudentZrno predmetnikStudentZrno;

    @Inject
    StudentZrno studentZrno;

    @GET
    @Path("/{id}")
    public Response vrniUstreznePredmete(@PathParam("id") Integer id) {
        Student student = studentZrno.getStudent(id);
        Vpis zadnjiVpis = student.getVpisi().get(student.getVpisi().size() - 1);
        List<DelPredmetnika> delPredmetnikaList = predmetnikStudentZrno.getCurriculumTypes();
        List<Predmet> strokovniIzbirni = predmetnikStudentZrno.getCourses(zadnjiVpis, delPredmetnikaList.get(1));
        List<Predmet> splosniIzbirni = predmetnikStudentZrno.getCourses(zadnjiVpis, delPredmetnikaList.get(2));
        List<Predmet> modulskiIzbirni = predmetnikStudentZrno.getCourses(zadnjiVpis, delPredmetnikaList.get(3));
        return null;
    }

}

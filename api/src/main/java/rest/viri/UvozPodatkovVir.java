package rest.viri;

import static rest.viri.PrijavaVir.generirajRandomGeslo;

import java.util.Date;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import student.PodatkiZaUvoz;
import vloge.Student;

@Path("uvoz-podatkov")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UvozPodatkovVir {

    private static Logger logger = Logger.getLogger(PrijavaVir.class.getSimpleName());

    @PersistenceContext(unitName = "studis")
    private EntityManager em;

    @Path("generiraj/geslo")
    @GET
    public Response generirajGeslo() {
        return Response.ok(new JSONObject().put("geslo", generirajRandomGeslo()).toString()).build();
    }

    @Path("generiraj/email")
    @GET
    public Response generirajEmail(PodatkiZaUvoz podatkiZaUvoz) {
        String uporabniskoIme = podatkiZaUvoz.getIme().charAt(0) + podatkiZaUvoz.getPriimek().charAt(0) + "";
        int sifra;
        while (true) {
            sifra = 1 + (int) (Math.random() * ((9998) + 1));
            String sifraStr = String.format("%04d", sifra);
            logger.info("sifraStr = " + sifraStr);
            try {
                Student student = (Student) em.createNamedQuery("entitete.vloge.Student.vrniStudentaPoUporabniskemImenu")
                                              .setParameter("uporabniskoIme", uporabniskoIme + sifraStr)
                                              .getSingleResult();
            } catch (javax.persistence.NoResultException e) {
                uporabniskoIme = uporabniskoIme + sifraStr;
                logger.info("uporabnik se ne obstaja, kreiramo uporabnisko ime " + uporabniskoIme);
                break;
            }
        }
        String email = uporabniskoIme + "@student.fri.si";
        return Response.ok(new JSONObject().put("email", email).toString()).build();
    }

    @Path("generiraj/vpisno")
    @GET
    public Response generirajVpisno(PodatkiZaUvoz podatkiZaUvoz) {
        Date date = new Date();
        String vpisnaStevilka = date.getYear() + "";
        while (true) {
            String sifra = String.format("63%d%04d",new Date().getYear(), (1 + (int) (Math.random() * ((8999) + 1))));
            try {
                Student student = (Student) em.createNamedQuery("entitete.vloge.Student.vrniStudentaPoVpisniStevilki")
                                              .setParameter("vpisna_stevilka", vpisnaStevilka + sifra)
                                              .getSingleResult();
            } catch (javax.persistence.NoResultException e) {
                vpisnaStevilka = vpisnaStevilka + sifra;
                logger.info("uporabnik se ne obstaja, kreiramo vpisno stevilko " + vpisnaStevilka);
                break;
            }
        }
        return Response.ok(new JSONObject().put("vpisna_stevilka", vpisnaStevilka).toString()).build();
    }
}

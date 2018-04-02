package rest.viri;

import static rest.viri.PrijavaVir.generirajRandomGeslo;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @Path("")
    public Response uvoziPodatke (PodatkiZaUvoz podatki) {
        String ime = podatki.getIme();
        String priimek = podatki.getPriimek();
        String geslo = generirajRandomGeslo();
        String uporabniskoIme = ime.charAt(0) + priimek.charAt(0) + "";
        int sifra;
        while (true) {
            sifra = 1000 + (int)(Math.random() * ((8999) + 1));
            try {
                Student student = (Student) em.createNamedQuery("entitete.vloge.Student.vrniStudentaPoUporabniskemImenu")
                                              .setParameter("uporabniskoIme", uporabniskoIme + sifra)
                                              .getSingleResult();
            } catch (javax.persistence.NoResultException e) {
                uporabniskoIme = uporabniskoIme + sifra;
                logger.info("uporabnik se ne obstaja, kreiramo uporabnisko ime " + uporabniskoIme);
                break;
            }
        }
        // todo generiraj vpisno stevilka
        // todo dodaj uporabnisko_ime za vse narejene primerke v StudentZrno
        return null;
    }

}

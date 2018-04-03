package zrna;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import vloge.Student;

@ApplicationScoped
public class StudentZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    @Inject
    UserTransaction utx;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object o) {
        createStudents();
    }

    public Student getStudent(Integer id) {
        return em.find(Student.class, id);
    }

    /***
     * Returns list of Students that match given criteria
     * @param queryParameters
     * @return
     */
    public List<Student> getStudents(QueryParameters queryParameters) {
        return JPAUtils.queryEntities(em, Student.class, queryParameters);
    }

    public Long getStudentsCount(QueryParameters queryParameters) {
        return JPAUtils.queryEntitiesCount(em, Student.class, queryParameters);
    }

    private List<String> imena = Arrays.asList(
            "Janez", "Marko", "Miha", "Matjaž", "Gašper", "Vilko", "Slavko", "Matija", "Peter", "Luka", "Pavel",
            "Gregor", "Žan", "Andraž", "Sonja", "Eva", "Ana", "Ema", "Klara", "Tina", "Mirjam", "Mojca", "Maruša");
    private List<String> priimki = Arrays.asList(
            "Zagorc", "Kovač", "Novak", "Horvat", "Hrovat", "Šuštar", "Omrzel", "Pirnar", "Čebašević", "Krpan");

    private String geslo = "123456";
    private String tel_st = "070123123";

    private String EMAIL_DOMENA = "@student.fri.si";

    private void createStudents() {
        int vpisnaStart = 63150001;
        int nVpisnih = 20;
        Random r = new Random();

        try {
            utx.begin();
            for (int i = vpisnaStart; i < vpisnaStart + nVpisnih; i++) {
                String ime = imena.get(r.nextInt(imena.size()));
                String priimek = priimki.get(r.nextInt(priimki.size()));
                String email = generateEmail(ime, priimek);
                String uporabniskoIme = ime.charAt(0) + priimek.charAt(0) + (1000 + (int)(Math.random() * ((8999) + 1))) + "";
                storeStudent(new Student(email, geslo, i, uporabniskoIme, ime, priimek,
                        LocalDate.now(), tel_st));
            }
            utx.commit();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }

    }

    private void storeStudent(Student student) {
        em.persist(student);
    }

    private String generateEmail(String ime, String priimek) {
        return replaceAllNonASCII(ime.toLowerCase())
                + "." + replaceAllNonASCII(priimek.toLowerCase()) + EMAIL_DOMENA;
    }

    private String replaceAllNonASCII(String string) {
        return string
                .replace("č", "c")
                .replace("š", "s")
                .replace("ž", "z")
                .replace("ć", "c");
    }
}

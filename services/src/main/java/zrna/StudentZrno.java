package zrna;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

import orodja.GeneratorPodatkov;
import vloge.Student;

@ApplicationScoped
public class StudentZrno {

    private static final Logger logger = Logger.getLogger(StudentZrno.class.getName());

    @PersistenceContext(name = "studis")
    private EntityManager em;

    @Inject
    UserTransaction utx;

    @Inject
    GeneratorPodatkov generator;

    public Student getStudent(Integer id) {
        return em.find(Student.class, id);
    }

    /***
     * Returns list of Students that match given criteria
     * @param param
     * @return
     */
    public List<Student> getStudentsByNSN(String param) {
        if (param == null || param.length() == 0)
            return em.createNamedQuery("entitete.vloge.Student.vrniVse", Student.class)
                    .getResultList();

        return em.createNamedQuery("entitete.vloge.Student.isciStudentaPoImenuPriimkuVpisni", Student.class)
                .setParameter("parameter", param + "%")
                .getResultList();
    }

    @Transactional
    public Student updateStudent(Student newStudent) throws Exception{
        Student student = em.find(Student.class, newStudent.getId());

        if (newStudent.getSpol() != null) {
            student.setSpol(newStudent.getSpol());
        }
        if (newStudent.getDatumRojstva() != null) {
            student.setDatumRojstva(newStudent.getDatumRojstva());
        }
        if (newStudent.getEmso() != null) {
            student.setEmso(newStudent.getEmso());
        }
        if (newStudent.getDavcnaStevilka() != null) {
            student.setDavcnaStevilka(newStudent.getDavcnaStevilka());
        }

        /*
        Kraj, obcina, drzava rojstva
         */
        if (newStudent.getDrzavaRojstva() != null) {
            student.setDrzavaRojstva(newStudent.getDrzavaRojstva());
        }
        if (newStudent.getKrajRojstva() != null) {
            student.setKrajRojstva(newStudent.getKrajRojstva());
        }
        if (newStudent.getObcinaRojstva() != null) {
            student.setObcinaRojstva(newStudent.getObcinaRojstva());
        }

        /*
        Stalno prebivalisce
         */
        if (newStudent.getDrzavaStalno() != null) {
            student.setDrzavaStalno(newStudent.getDrzavaStalno());
        }
        if (newStudent.getObcinaStalno() != null) {
            student.setObcinaStalno(newStudent.getObcinaStalno());
        }
        if (newStudent.getNaslovStalno() != null) {
            student.setNaslovStalno(newStudent.getNaslovStalno());
        }
        if (newStudent.getPostaStalno() != null) {
            student.setPostaStalno(newStudent.getPostaStalno());
        }

        /*
        Zacasno prebivalisce
         */
        if (newStudent.getDrzavaZacasno() != null) {
            student.setDrzavaZacasno(newStudent.getDrzavaZacasno());
        }
        if (newStudent.getObcinaZacasno() != null) {
            student.setObcinaZacasno(newStudent.getObcinaZacasno());
        }
        if (newStudent.getPostaZacasno() != null) {
            student.setPostaZacasno(newStudent.getPostaZacasno());
        }
        if (newStudent.getNaslovZacasno() != null) {
            student.setNaslovZacasno(newStudent.getNaslovZacasno());
        }

        /*
        Kontaktni podatki
         */
        if (newStudent.getTelefonskaStevilka() != null) {
            student.setTelefonskaStevilka(newStudent.getTelefonskaStevilka());
        }
        if (newStudent.getDavcnaStevilka() != null) {
            student.setDavcnaStevilka(newStudent.getDavcnaStevilka());
        }
        if (newStudent.getIme() != null) {
            student.setIme(newStudent.getIme());
        }
        if (newStudent.getPriimek() != null) {
            student.setPriimek(newStudent.getPriimek());
        }
        if (newStudent.getNaslovZaPosiljanjePoste() != null) {
            student.setNaslovZaPosiljanjePoste(newStudent.getNaslovZaPosiljanjePoste());
        }
        if (newStudent.getEmail() != null) {
            student.setEmail(newStudent.getEmail());
        }

        em.merge(student);
        return student;
    }

    private List<String> imena = Arrays.asList(
            "Janez", "Marko", "Miha", "Matjaž", "Gašper", "Vilko", "Slavko", "Matija", "Peter", "Luka", "Pavel",
            "Gregor", "Žan", "Andraž", "Sonja", "Eva", "Ana", "Ema", "Klara", "Tina", "Mirjam", "Mojca", "Maruša");
    private List<String> priimki = Arrays.asList(
            "Zagorc", "Kovač", "Novak", "Horvat", "Hrovat", "Šuštar", "Omrzel", "Pirnar", "Čebašević", "Krpan");

    private String geslo = "123456";
    private String tel_st = "070123123";


    /**
     * Helper method for populating users
     */
//    private void createStudents() {
//        int nVpisnih = 50;
//        Random r = new Random();
//
//        try {
//            for (int i = 0; i < nVpisnih; i++) {
//                utx.begin();
//                String ime = imena.get(r.nextInt(imena.size()));
//                String priimek = priimki.get(r.nextInt(priimki.size()));
//                String email = generator.generirajEmail(ime, priimek);
//                String uporabniskoIme = generator.generirajUporabniskoIme(ime, priimek);
//                int vpisnaStevilka = generator.generirajVpisnoStevilko();
//
//                if (i >= 30)
//                    storeOseba(new Student(email, geslo, vpisnaStevilka, uporabniskoIme, ime, priimek,
//                        LocalDate.now(), tel_st));
//                else if (i < 1)
//                    storeOseba(new Skrbnik(email, geslo, uporabniskoIme, ime, priimek));
//                else if (i < 5) {
//                    storeOseba(new Referent(email, geslo, uporabniskoIme, ime, priimek));
//                }
//                else
//                    storeOseba(new Ucitelj(email, geslo, uporabniskoIme, ime, priimek));
//
//                utx.commit();
//            }
//        } catch (NotSupportedException e) {
//            e.printStackTrace();
//        } catch (SystemException e) {
//            e.printStackTrace();
//        } catch (HeuristicMixedException e) {
//            e.printStackTrace();
//        } catch (HeuristicRollbackException e) {
//            e.printStackTrace();
//        } catch (RollbackException e) {
//            e.printStackTrace();
//        }
//
//    }

    @Transactional
    private void storeOseba(Object oseba) {
        em.persist(oseba);
    }

}

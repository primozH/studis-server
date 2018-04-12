package zrna;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

import naslov.Drzava;
import orodja.GeneratorPodatkov;
import vloge.Student;

@ApplicationScoped
public class StudentZrno {

    private static final Logger logger = Logger.getLogger(StudentZrno.class.getName());

    @PersistenceContext(name = "studis")
    private EntityManager em;

    @Inject private UserTransaction utx;

    @Inject private GeneratorPodatkov generator;

    private final static Integer NUMERICNA_OZNAKA_SLOVENIJA = 705;

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

        if (student.getSpol() == null && newStudent.getSpol() == null) {
            throw new Exception("Spol je obvezno polje");
        } else if (newStudent.getSpol() != null) {
            student.setSpol(newStudent.getSpol());
        }

        if (student.getDatumRojstva() == null && newStudent.getDatumRojstva() == null) {
            throw new Exception("Datum rojstva je obvezno polje");
        } else if (newStudent.getDatumRojstva() != null) {
            student.setDatumRojstva(newStudent.getDatumRojstva());
        }

        if (student.getEmso() == null && newStudent.getEmso() == null) {
            throw new Exception("EMSO je obvezno polje");
        } else if (newStudent.getEmso() != null) {
            student.setEmso(newStudent.getEmso());
        }

        if (student.getDavcnaStevilka() == null && newStudent.getDavcnaStevilka() == null) {
            throw new Exception("Davcna stevilka je obvezno polje");
        } else if (student.getDavcnaStevilka() != null) {
            if (student.getDavcnaStevilka().length() != 8)
                throw new Exception("Davčna številka mora vsebovati 8 števk");
            student.setDavcnaStevilka(newStudent.getDavcnaStevilka());
        }

        /*
        Kraj, obcina, drzava rojstva
         */
        if (student.getDrzavaRojstva() == null && newStudent.getDrzavaRojstva() == null) {
            throw new Exception("Drzava rojstva je obvezno polje");
        } else if (newStudent.getDrzavaRojstva() != null) {
            student.setDrzavaRojstva(newStudent.getDrzavaRojstva());
        }

        if (student.getKrajRojstva() == null && newStudent.getKrajRojstva() == null) {
            throw new Exception("Kraj rojstva je obvezno polje");
        } else if (newStudent.getKrajRojstva() != null) {
            student.setKrajRojstva(newStudent.getKrajRojstva());
        }

        if (newStudent.getObcinaRojstva() != null) {
            student.setObcinaRojstva(newStudent.getObcinaRojstva());
        }

        /*
        Stalno prebivalisce
         */
        if (student.getDrzavaStalno() == null && newStudent.getDrzavaStalno() == null) {
            throw new Exception("Drzava stalnega prebivališča je obvezno polje");
        } else if (newStudent.getDrzavaStalno() != null) {
            student.setDrzavaStalno(newStudent.getDrzavaStalno());
        }

        Integer numDrzava = student.getDrzavaStalno().getNumericnaOznaka();

        if (numDrzava.equals(NUMERICNA_OZNAKA_SLOVENIJA) && student.getObcinaStalno() == null &&
                newStudent.getObcinaStalno() == null) {
            throw new Exception("Za Slovenijo je občina stalnega prebivališča obvezno polje");
        } else if (numDrzava.equals(NUMERICNA_OZNAKA_SLOVENIJA) && newStudent.getObcinaStalno() != null) {
            student.setObcinaStalno(newStudent.getObcinaStalno());
        }

        if (numDrzava.equals(NUMERICNA_OZNAKA_SLOVENIJA) && student.getPostaStalno() == null &&
                newStudent.getPostaStalno() == null) {
            throw new Exception("Za Slovenijo je pošta stalnega prebivališča obvezno polje");
        } else if (numDrzava.equals(NUMERICNA_OZNAKA_SLOVENIJA) && newStudent.getPostaStalno() != null) {
            student.setPostaStalno(newStudent.getPostaStalno());
        }

        if (student.getNaslovStalno() == null && newStudent.getNaslovStalno() == null) {
            throw new Exception("Naslov stalnega bivališča je obvezno polje");
        } else if (newStudent.getNaslovStalno() != null) {
            student.setNaslovStalno(newStudent.getNaslovStalno());
        }

        /*
        Zacasno prebivalisce
         */
        if (newStudent.getDrzavaZacasno() != null) {
            student.setDrzavaZacasno(newStudent.getDrzavaZacasno());
        }

        numDrzava = student.getDrzavaZacasno() == null ? 0 : student.getDrzavaZacasno().getNumericnaOznaka();
        if (newStudent.getObcinaZacasno() != null && numDrzava.equals(NUMERICNA_OZNAKA_SLOVENIJA)) {
            student.setObcinaZacasno(newStudent.getObcinaZacasno());
        }

        if (newStudent.getPostaZacasno() != null && numDrzava.equals(NUMERICNA_OZNAKA_SLOVENIJA)) {
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
        if (newStudent.getIme() != null) {
            student.setIme(newStudent.getIme());
        }
        if (newStudent.getPriimek() != null) {
            student.setPriimek(newStudent.getPriimek());
        }
        if (student.getNaslovZaPosiljanjePoste() == null && newStudent.getNaslovZaPosiljanjePoste() == null) {
            throw new Exception("Naslov za posiljanje poste je obvezen podatek");
        } else if (newStudent.getNaslovZaPosiljanjePoste() != null) {
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

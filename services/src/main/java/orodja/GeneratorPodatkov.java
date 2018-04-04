package orodja;

import vloge.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class GeneratorPodatkov {

    private static Logger logger = Logger.getLogger(GeneratorPodatkov.class.getSimpleName());
    private static final String ALFANUMERICNI_ZNAKI = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int DOLZINA_RANDOM_GESLA = 10;
    private static final String EMAIL_DOMENA = "@student.fri.si";

    @PersistenceContext(unitName = "studis")
    private EntityManager em;


    /***
     * Generira vpisno stevilko tipa 63LLXXXX (LL - zadnji stevki leta vpisa (2017 - 17), XXXX - zaporedna stevilka vpisa)
     * @return vpisnaStevilka
     */
    public Integer generirajVpisnoStevilko() {
        int year = LocalDate.now().getYear();
        int lastTwoDigitsYear = year % 100;
        int vpisnaStevilkaBase = 63000000;

        int vpisna = vpisnaStevilkaBase + lastTwoDigitsYear * (int) Math.pow(10, 4);
        List<Student> s = em.createNamedQuery("entitete.vloge.Student.vrniNajvisjoZaporednoVpisnoStevilko")
                .setParameter("vpisnaStevilka", Integer.toString(vpisna / 10000) + "%")
                .getResultList();

        if (s.isEmpty()) {
            return vpisna + 1;
        }

        return vpisna + s.get(0).getVpisnaStevilka() % 10000 + 1;
    }

    /**
     * Se uporablja v kolikor uporabnik pozabi geslo
     * oz. ob prvi prijavi v sistem.
     *
     * @return
     */
    public String generirajGeslo() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < DOLZINA_RANDOM_GESLA; i++) {
            stringBuilder.append(ALFANUMERICNI_ZNAKI.charAt(random.nextInt(ALFANUMERICNI_ZNAKI.length())));
        }
        return stringBuilder.toString();
    }


    public String generirajEmail(String ime, String priimek) {
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

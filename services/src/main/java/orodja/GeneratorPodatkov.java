package orodja;

import java.time.LocalDate;
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
    private static final String EMAIL_DOMENA = "student.fri.si";

    @PersistenceContext(unitName = "studis")
    private static EntityManager em;


    public static Integer generirajVpisnoStevilko() {
        int year = LocalDate.now().getYear();
        int lastTwoDigitsYear = year % 100;
        int vpisnaStevilkaBase = 63000000;

        int zapSt = em.createNamedQuery("entitete.vloge.Student.vrniNajvisjoZaporednoVpisnoStevilko").getFirstResult();
        logger.info(Integer.toString(zapSt));
        return vpisnaStevilkaBase + lastTwoDigitsYear * 10^6 + zapSt + 1;
    }

    /**
     * Se uporablja v kolikor uporabnik pozabi geslo
     * oz. ob prvi prijavi v sistem.
     *
     * @return
     */
    public static String generirajGeslo() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < DOLZINA_RANDOM_GESLA; i++) {
            stringBuilder.append(ALFANUMERICNI_ZNAKI.charAt(random.nextInt(ALFANUMERICNI_ZNAKI.length())));
        }
        return stringBuilder.toString();
    }


    public static String generirajEmail(String ime, String priimek) {
        return replaceAllNonASCII(ime.toLowerCase())
                + "." + replaceAllNonASCII(priimek.toLowerCase()) + EMAIL_DOMENA;
    }

    private static String replaceAllNonASCII(String string) {
        return string
                .replace("č", "c")
                .replace("š", "s")
                .replace("ž", "z")
                .replace("ć", "c");
    }
}

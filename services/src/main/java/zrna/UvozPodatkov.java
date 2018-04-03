package zrna;

import orodja.GeneratorPodatkov;
import vpis.Kandidat;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.util.Scanner;

@ApplicationScoped
public class UvozPodatkov {

    @PersistenceContext(name = "studis")
    private static EntityManager em;

    public static void parseFile(File file) {
        String ime, priimek, program, email;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                ime = line.substring(0, 30);
                priimek = line.substring(30, 60);
                program = line.substring(60, 67);
                email = line.substring(67, 127);

                Kandidat k = createKandidat(ime, priimek, program, email);
                em.persist(k);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Kandidat createKandidat(String ime, String priimek, String program, String email) {
        Kandidat k = new Kandidat();
        k.setIme(ime);
        k.setPriimek(priimek);
        k.setEmail(email);
        k.setGeslo(GeneratorPodatkov.generirajGeslo());
        k.setVpisnaStevilka(GeneratorPodatkov.generirajVpisnoStevilko());

        k.setStudijskiProgram(Integer.parseInt(program));

        return k;
    }
}

package zrna;

import orodja.GeneratorPodatkov;
import vpis.Kandidat;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;

@ApplicationScoped
public class UvozPodatkov {

    private String ERROR_IMPORT = "./napaka_uvoz.txt";

    @PersistenceContext(name = "studis")
    private static EntityManager em;

    @Inject
    private GeneratorPodatkov generator;

    @Inject
    private UserTransaction ux;

    private static final Logger logger = Logger.getLogger(UvozPodatkov.class.getName());

    public void parseFile(File file) {
        String ime, priimek, program, email;
        int imeL = 30;
        int priimekL = 30;
        int programL = 7;
        int emailL = 60;

        BufferedReader br;
        BufferedWriter out;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            out = new BufferedWriter(new FileWriter(ERROR_IMPORT));

            String line = null;

            br.mark(1);
            if (br.read() != 0xFEFF)
                br.reset();

            while (br.ready()) {
                line = br.readLine();
                int length = line.length();
                if (length == 0)
                    break;

                ime = line.substring(0, 30).trim();
                priimek = line.substring(30, 60).trim();
                program = line.substring(60, 67);
                email = line.substring(67, length).trim();

                logger.info(Integer.toString(length));
                Kandidat k = createKandidat(ime, priimek, program, email);

                ux.begin();
                em.persist(k);
                ux.commit();
            }

            br.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        }
    }

    private Kandidat createKandidat(String ime, String priimek, String program, String email) {
        Kandidat k = new Kandidat();
        k.setIme(ime);
        k.setPriimek(priimek);
        k.setEmail(email);
        k.setGeslo(generator.generirajGeslo());
        k.setVpisnaStevilka(generator.generirajVpisnoStevilko());

        k.setStudijskiProgram(Integer.parseInt(program));

        return k;
    }
}

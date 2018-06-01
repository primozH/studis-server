package zrna;

import orodja.GeneratorPodatkov;
import sifranti.StudijskiProgram;
import vloge.Kandidat;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UvozPodatkov {

    private static final String FILE_LOCATION = "./";
    private static final String ERROR_FILE_NAME = "napaka_uvoz.txt";
    private final static int IME_L = 30;
    private final static int PRIIMEK_L = 30;
    private final static int PROGRAM_L = 7;
    private final static int EMAIL_L = 60;
    private static final Logger logger = Logger.getLogger(UvozPodatkov.class.getName());

    @PersistenceContext(name = "studis")
    private static EntityManager em;

    @Inject
    private GeneratorPodatkov generator;

    @Inject
    private UserTransaction ux;

    @Transactional
    public List<Kandidat> importData(InputStream content) {
        try {
            emptyTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileName = FILE_LOCATION + "kandidati.txt";
        InputStreamReader reader = new InputStreamReader(content);
        int count = 0;
        char [] buffer = new char[1024];
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileName));

            while ((count = reader.read(buffer)) > 0) {
                out.write(buffer);
            }

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parseFile(new File(fileName));
    }

    public File downloadFile() {
        return new File(ERROR_FILE_NAME);
    }

    private List<Kandidat> parseFile(File file) {
        String ime, priimek, program, email;
        List<Kandidat> kandidati = new ArrayList<>();

        BufferedReader br;
        BufferedWriter out;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            out = new BufferedWriter(new FileWriter(ERROR_FILE_NAME));

            String line = null;

            br.mark(1);
            if (br.read() != 0xFEFF)
                br.reset();

            while (br.ready()) {
                line = br.readLine();
                int length = line.length();
                logger.info(line + " " + Integer.toString(length));
                if (length == 0)
                    break;
                if (length < IME_L + PRIIMEK_L + PROGRAM_L){
                    logger.info("Short line!");
                    break;
                }

                ime = line.substring(0, IME_L).trim();
                priimek = line.substring(IME_L, IME_L + PRIIMEK_L).trim();
                program = line.substring(IME_L + PRIIMEK_L, IME_L + PRIIMEK_L + PROGRAM_L);
                email = line.substring(IME_L + PRIIMEK_L + PROGRAM_L, length).trim();

                Kandidat k;
                if ((k = createKandidat(ime, priimek, program, email, out)) != null) {
                    kandidati.add(k);
                }
            }

            br.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kandidati;
    }

    @Transactional
    private Kandidat createKandidat(String ime, String priimek, String program, String email, BufferedWriter out) throws IOException {
        Kandidat k = new Kandidat();
        k.setIme(ime);
        k.setPriimek(priimek);
        k.setEmail(email);
        String geslo = generator.generirajGeslo();
        k.setGesloPlain(geslo);
        k.setVpisnaStevilka(generator.generirajVpisnoStevilko());

        try {
            Integer sifra = Integer.parseInt(program);
            StudijskiProgram studijskiProgram = em.find(StudijskiProgram.class, sifra);
            if (studijskiProgram == null) {
                throw new NotSupportedException("Studijski program s sifro " + sifra.toString() + " ne obstaja.");
            }

            k.setStudijskiProgram(studijskiProgram);
            em.persist(k);
            return k;
        } catch (NumberFormatException | NotSupportedException e) {
            logger.log(Level.WARNING, e.getMessage());

            StringBuilder sb = new StringBuilder();
            String newIme = String.format("%-" + IME_L + "." + IME_L + "s", ime);
            String newPriimek = String.format("%-" + PRIIMEK_L + "." + PRIIMEK_L + "s", priimek);
            String newProgram = String.format("%-" + PROGRAM_L + "." + PROGRAM_L + "s", program);
            String newEmail = String.format("%-" + EMAIL_L + "." + EMAIL_L + "s", email);

            out.write(newIme + newPriimek + newProgram + newEmail);
            return null;
        }
    }

    @Transactional
    private void emptyTable() {
        em.createNativeQuery("DELETE FROM kandidat").executeUpdate();

    }
}

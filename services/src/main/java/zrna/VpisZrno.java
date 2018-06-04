package zrna;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import predmetnik.Modul;
import predmetnik.Predmetnik;
import sifranti.DelPredmetnika;
import sifranti.Letnik;
import sifranti.NacinStudija;
import sifranti.OblikaStudija;
import sifranti.Predmet;
import sifranti.StudijskiProgram;
import sifranti.StudijskoLeto;
import sifranti.VrstaVpisa;
import student.PredmetStudent;
import student.Zeton;
import student.ZetonId;
import vloge.Student;
import vpis.Vpis;
import vpis.VpisniList;

@ApplicationScoped
public class VpisZrno {

    private final List<String> coursesTypeSearchStrings =
            new ArrayList<>(Arrays.asList("obvezni%", "strokovni%", "splošni%", "modul%"));

    private static final Logger logger = Logger.getLogger(VpisZrno.class.getName());

    @PersistenceContext(name = "studis")
    private EntityManager em;

    @Inject
    private PredmetnikStudentZrno psz;

    @Inject
    private UserTransaction ux;

    private Map<String, DelPredmetnika> curriculumPart = new HashMap<>();

    @PostConstruct
    private void init() {
        coursesTypeSearchStrings.forEach(s -> {
            List<DelPredmetnika> delPredmetnika = em.createNamedQuery("entitete.sifranti.DelPredmetnika.vrniDelPredmetnika", DelPredmetnika.class)
                    .setParameter("tip", s)
                    .getResultList();

            if (!delPredmetnika.isEmpty()) {
                curriculumPart.put(s, delPredmetnika.get(0));
            }
        });
    }

    public Vpis enrollmentProcedure(Integer studentId, VpisniList vpisniList) throws Exception {
        logger.info("Začenjam postopek vpisa. Pridobivanje podatkov...");
        Integer zetonId = vpisniList.getZeton();

        Zeton token = em.find(Zeton.class, zetonId);
        if (!studentId.equals(token.getStudent().getId())) {
            throw new Exception("Podatki o študentu se ne ujemajo");
        }

        List<Predmet> profCourses = vpisniList.getStrokovniPredmeti();
        List<Predmet> optionalCourses = vpisniList.getSplosniPredmeti();
        List<Predmet> moduleCourses = vpisniList.getModulskiPredmeti();

        // confirm enrollment
        Vpis enrollment;
        try {
            logger.info("Kreiranje vpisa za študenta");
            enrollment = vpisiStudenta(token);
        } catch (Exception e) {
            throw e;
        }

        // retrieve mandatory curriculum

        logger.info("Pridobivanje obveznega dela predmetnika...");
        List<Predmet> courses = new ArrayList<>(psz.getCourses(enrollment, curriculumPart.get(coursesTypeSearchStrings.get(0))));

        // confirm optional curriculum
        logger.info("Preverjanje veljavnosti izbire za izbirne predmete...");
        List<Predmet> optional;
        switch (enrollment.getLetnik().getLetnik()) {
            case 1:
                break;
            case 2:
                List<Predmet> prof = hasProfCourse(enrollment, profCourses);
                optional = hasOptionalCourse(enrollment, optionalCourses);
                if (!prof.isEmpty()) {
                    courses.addAll(prof);
                }
                if (optional != null && optional.size() != 0) {
                    courses.addAll(optional);
                }
                break;
            case 3:
                List<Predmet> module = hasModuleCourse(enrollment, moduleCourses, token.isProstaIzbira());
                optional = hasOptionalCourse(enrollment, optionalCourses);

                if (optional != null && optional.size() != 0) {
                    courses.addAll(optional);
                }
                if (module != null && module.size() != 0) {
                    courses.addAll(module);
                }
        }

        Integer ECTSSum = courses.stream().mapToInt(Predmet::getECTS).sum();
        if (ECTSSum < 60) {
            throw new Exception("Izbrani predmetnik mora obsegati točno 60 ECTS točk! Število zbranih točk: "
                    + ECTSSum.toString());
        }

        // end
        List<PredmetStudent> studentCourses = endEnrollmentProcedure(enrollment, courses);
        if (studentCourses == null) {
            return null;
        }
        logger.info("Uspešno zaključen vpis");

        return enrollment;
    }

    public List<Vpis> getVpisi(Integer studentId) {
        return em.createNamedQuery("entitete.vpis.Vpis.vpisiZaStudenta", Vpis.class)
                .setParameter("student", studentId)
                .getResultList();
    }

    public List<Student> getVpisaniStudenti() {
        StudijskoLeto zadnjeStudijskoLeto = em.createNamedQuery("entitete.sifranti.StudijskoLeto.vrniZadnjeStudijskoLeto", StudijskoLeto.class).setMaxResults(1).getSingleResult();
        try {
            return em.createNamedQuery("entitete.vpis.Vpis.vrniVseVpisaneStudente", Student.class)
                     .setParameter("studijskoLeto", zadnjeStudijskoLeto.getId())
                     .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    private Vpis vpisiStudenta(Zeton zeton) throws Exception{

        if (zeton == null || zeton.isIzkoriscen()) {
            throw new Exception("Student nima pravice vpisa");
        }

        Letnik letnik = zeton.getLetnik();
        StudijskoLeto studijskoLeto = zeton.getStudijskoLeto();
        StudijskiProgram studijskiProgram = zeton.getStudijskiProgram();
        VrstaVpisa vrstaVpisa = zeton.getVrstaVpisa();
        NacinStudija nacinStudija = zeton.getNacinStudija();
        OblikaStudija oblikaStudija = zeton.getOblikaStudija();
        Student student = zeton.getStudent();

        return new Vpis(student, studijskoLeto, studijskiProgram, vrstaVpisa, nacinStudija, oblikaStudija, letnik);
    }

    public List<PredmetStudent> endEnrollmentProcedure(Vpis enrollment, List<Predmet> courses) {

        logger.info("Priprava na končno potrditev vpisa...");
        Student student = enrollment.getStudent();
        StudijskoLeto yearOfStudy = enrollment.getStudijskoLeto();

        try {
            ux.begin();
            persistEnrollment(enrollment);

            em.createNamedQuery("entitete.vpis.Zeton.nastaviIzkoriscenostZetona")
                    .setParameter("student", student)
                    .setParameter("izkoriscen", true)
                    .executeUpdate();

            List<PredmetStudent> studentCourses = new ArrayList<>(persistStudentsCourses(enrollment, courses));
            ux.commit();
            return studentCourses;
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
        return null;
    }

    private List<Predmet> hasProfCourse(Vpis enrollment, List<Predmet> courses) {
        if (courses == null || courses.size() == 0) {
            return null;
        }
        logger.info("Strokovno izbirni predmet...");
        List<Predmet> availableCourses = psz.getCourses(enrollment, curriculumPart.get(coursesTypeSearchStrings.get(1)));

        return availableCourses.stream().filter(courses::contains).collect(Collectors.toList());
    }

    private List<Predmet> hasOptionalCourse(Vpis enrollment, List<Predmet> courses) {
        if (courses.isEmpty()) {
            return null;
        }

        logger.info("Splošno izbirni predmeti...");
        List<Predmet> availableCourses = psz.getCourses(enrollment, curriculumPart.get(coursesTypeSearchStrings.get(2)));
        return availableCourses.stream().filter(courses::contains).collect(Collectors.toList());
    }

    private List<Predmet> hasModuleCourse(Vpis enrollment, List<Predmet> courses, boolean openChoice) {
        if (courses.isEmpty()) {
            return null;
        }

        logger.info("Moduli...");
        if (openChoice) {
            logger.info("Prosta izbira modulov...");
            List<Predmet> availableCourses = psz.getCourses(enrollment, curriculumPart.get(coursesTypeSearchStrings.get(3)));
            // filter selected courses
            List<Predmet> moduleCourses = availableCourses.stream().filter(courses::contains).collect(Collectors.toList());

            if (moduleCourses.size() < 6) {
                return null;
            }
            return moduleCourses;
        }

        logger.info("Preverjanje veljavnosti izbire modulov");
        List<Predmet> module1 = new ArrayList<>();

        List<Predmetnik> curriculum = psz.getCurriculum(enrollment, curriculumPart.get(coursesTypeSearchStrings.get(3)));

        Map<Modul, List<Predmetnik>> filteredCurriculum = curriculum.stream()
                .filter(predmetnik -> courses.contains(predmetnik.getPredmet()))
                .collect(Collectors.groupingBy(Predmetnik::getModul));

        filteredCurriculum.forEach((modul, predmetnik) -> {
            for (Predmetnik p : predmetnik) {
                module1.add(p.getPredmet());
            }
        });

        if (module1.size() < 6) {
            return null;
        }

        return module1;
    }

    private List<PredmetStudent> persistStudentsCourses(Vpis enrollment, List<Predmet> courses) {
        logger.info("Shranjevanje izbranega predmetnika");
        List<PredmetStudent> studentCourses = new ArrayList<>();
        courses.forEach(course -> {
            PredmetStudent studentCourse = new PredmetStudent();
            studentCourse.setPredmet(course);
            studentCourse.setVpis(enrollment);

            em.persist(studentCourse);

            studentCourses.add(studentCourse);
        });

        return studentCourses;
    }

    private void persistEnrollment(Vpis enrollment) {
        em.persist(enrollment);
    }

    public String zamenjajPodatkeZaPotrdiloVpisa (String html, Vpis vpis) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String strDate = sdf.format(now);
        Date datumRojstvaDate = java.sql.Date.valueOf(vpis.getStudent().getDatumRojstva());
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        String datumRojstva = sdf.format(datumRojstvaDate);
        return html.replace("PRIIMEK", vpis.getStudent().getPriimek())
                .replace("IME", " " + vpis.getStudent().getIme())
                .replace("ULICA", vpis.getStudent().getNaslovZacasno() != null ? vpis.getStudent().getNaslovZacasno() : vpis.getStudent().getNaslovStalno())
                .replace("MESTO", vpis.getStudent().getObcinaZacasno() != null ? vpis.getStudent().getObcinaZacasno().getIme() : vpis.getStudent().getObcinaStalno().getIme())
                .replace("DRZAVA", vpis.getStudent().getDrzavaStalno().getSlovenskiNaziv())
                .replace("DATUMURA", " " + strDate)
                .replace("EMSO", vpis.getStudent().getEmso())
                .replace("VPISNA", " " + vpis.getStudent().getVpisnaStevilka() + "")
                .replace("DATUMROJSTVA", datumRojstva)
                .replace("KRAJROJSTVA", vpis.getStudent().getKrajRojstva())
                .replace("LETNIK", vpis.getLetnik().getLetnik().toString() + ". letnik")
                .replace("SOLSKOLETO", vpis.getStudijskoLeto().getStudijskoLeto())
                .replace("NACINSTUDIJA", vpis.getNacinStudija().getOpis())
                .replace("STUDIJSKIPROGRAM", vpis.getStudijskiProgram().getNaziv())
                .replace("Č", "C")
                .replace("č", "c");
    }

    public Vpis vrniVpis(int studentId, int studijskoLeto) {
        try {
            return  em.createNamedQuery("entitete.vpis.Vpis.vpisZaStudentaVLetu", Vpis.class)
                      .setParameter("student", studentId)
                      .setParameter("studijskoLeto", studijskoLeto)
                      .setMaxResults(1)
                      .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public boolean potrdiVpis(int studentId, int studijskoLeto) {
        Vpis vpis = vrniVpis(studentId, studijskoLeto);
        if (vpis == null) return false;
        vpis.setPotrjen(true);
        em.merge(vpis);


        return true;
    }

    public List<Vpis> vrniNepotrjene() {
        try {
            return em.createNamedQuery("entitete.vpis.Vpis.vrniSeNepotrjene", Vpis.class)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Vpis> vrniVpisaneVLetnik(Integer leto, Integer letnik) {
        return em.createNamedQuery("entitete.vpis.Vpis.vrniPotrjeneVpiseVLetnik", Vpis.class)
                .setParameter("leto", leto)
                .setParameter("letnik", letnik)
                .getResultList();
    }

}

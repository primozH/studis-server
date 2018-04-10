package zrna;

import predmetnik.Predmetnik;
import sifranti.*;
import student.PredmetStudent;
import student.Zeton;
import student.ZetonId;
import vloge.Student;
import vpis.Vpis;
import vpis.VpisniList;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class VpisZrno {

    private final List<String> coursesTypeSearchStrings =
            new ArrayList<>(Arrays.asList("obvezni%", "strokovni%", "splošni%", "modul%"));

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

    public Vpis enrollmentProcedure(VpisniList vpisniList) throws Exception {
        Integer studentId = vpisniList.getZeton().getStudent().getId();
        Integer enrollmentType = vpisniList.getZeton().getVrstaVpisa().getSifraVpisa();
        ZetonId zetonId = new ZetonId(studentId, enrollmentType);

        Zeton token = em.find(Zeton.class, zetonId);
        Predmet profCourse = vpisniList.getStrokovniPredmet();
        List<Predmet> optionalCourses = vpisniList.getSplosniPredmeti();
        List<Predmet> moduleCourses = vpisniList.getModulskiPredmeti();

        // confirm enrollment
        Vpis enrollment;
        try {
            enrollment = vpisiStudenta(token);
        } catch (Exception e) {
            throw e;
        }

        // retrieve mandatory curriculum

        List<Predmet> courses = new ArrayList<>(psz.getCourses(enrollment, curriculumPart.get(coursesTypeSearchStrings.get(0))));

        // confirm optional curriculum
        List<Predmet> optional;
        switch (enrollment.getLetnik().getLetnik()) {
            case 1:
                break;
            case 2:
                Predmet prof = hasProfCourse(enrollment, profCourse);
                optional = hasOptionalCourse(enrollment, optionalCourses);
                if (prof != null) {
                    courses.add(prof);
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
            throw new Exception("Izbrani predmetnik ne vsebuje dovolj ECTS točk");
        }

        // end
        List<PredmetStudent> studentCourses = endEnrollmentProcedure(enrollment, courses);
        if (studentCourses == null) {
            return null;
        }

        return enrollment;
    }

    public List<Vpis> getVpisi(Integer studentId) {
        return em.createNamedQuery("entitete.vpis.Vpis.vrniVpiseZaStudenta", Vpis.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    private Vpis vpisiStudenta(Zeton vpis) throws Exception{
        ZetonId zetonId = new ZetonId(vpis.getStudent().getId(), vpis.getVrstaVpisa().getSifraVpisa());
        Zeton zeton = em.find(Zeton.class, zetonId);

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

        Student student = enrollment.getStudent();
        StudijskoLeto yearOfStudy = enrollment.getStudijskoLeto();

        try {
            ux.begin();
            persistEnrollment(enrollment);

            em.createNamedQuery("entitete.vpis.Zeton.nastaviIzkoriscenostZetona")
                    .setParameter("student", student)
                    .setParameter("izkoriscen", true)
                    .executeUpdate();

            List<PredmetStudent> studentCourses = new ArrayList<>(persistStudentsCourses(student, yearOfStudy, courses));
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

    private Predmet hasProfCourse(Vpis enrollment, Predmet course) {
        if (course == null) {
            return null;
        }
        List<Predmet> availableCourses = psz.getCourses(enrollment, curriculumPart.get(coursesTypeSearchStrings.get(1)));

        for (Predmet c : availableCourses) {
            if (course.getSifra().equals(c.getSifra()))
                return c;
        }
        return null;
    }

    private List<Predmet> hasOptionalCourse(Vpis enrollment, List<Predmet> courses) {
        if (courses.isEmpty()) {
            return null;
        }

        List<Predmet> availableCourses = psz.getCourses(enrollment, curriculumPart.get(coursesTypeSearchStrings.get(2)));
        return availableCourses.stream().filter(courses::contains).collect(Collectors.toList());
    }

    private List<Predmet> hasModuleCourse(Vpis enrollment, List<Predmet> courses, boolean openChoice) {
        if (courses.isEmpty()) {
            return null;
        }

        if (openChoice) {
            List<Predmet> availableCourses = psz.getCourses(enrollment, curriculumPart.get(coursesTypeSearchStrings.get(3)));
            // filter selected courses
            List<Predmet> moduleCourses = availableCourses.stream().filter(courses::contains).collect(Collectors.toList());

            if (moduleCourses.size() != 6) {
                return null;
            }
            return moduleCourses;
        }

        List<Predmet> module1 = new ArrayList<>();
        List<Predmet> module2 = new ArrayList<>();
        int module1Code = -1;
        int module2Code = -1;

        List<Predmetnik> curriculum = psz.getCurriculum(enrollment, curriculumPart.get(coursesTypeSearchStrings.get(3)));

        curriculum = curriculum.stream().filter(predmetnik -> courses.contains(predmetnik.getPredmet())).collect(Collectors.toList());

        for(Predmetnik p : curriculum) {
            if (module1Code < 0) {
                module1Code = p.getModul().ordinal();
            }
            if (p.getModul().ordinal() != module1Code && module2Code < 0) {
                module2Code = p.getModul().ordinal();
            }

            // course must be part of module1 or module2
            if (module1Code == p.getModul().ordinal()) {
                module1.add(p.getPredmet());
            } else if (module2Code == p.getModul().ordinal()){
                module2.add(p.getPredmet());
            } else {
                return null;
            }
        }

        module1.addAll(module2);

        if (module1.size() != 6) {
            return null;
        }

        return module1;
    }

    private List<PredmetStudent> persistStudentsCourses(Student student, StudijskoLeto yearOfStudy, List<Predmet> courses) {
        List<PredmetStudent> studentCourses = new ArrayList<>();
        courses.forEach(course -> {
            PredmetStudent studentCourse = new PredmetStudent();
            studentCourse.setPredmet(course);
            studentCourse.setStudent(student);
            studentCourse.setStudijskoLeto(yearOfStudy);

            em.persist(studentCourse);

            studentCourses.add(studentCourse);
        });

        return studentCourses;
    }

    private void persistEnrollment(Vpis enrollment) {
        em.persist(enrollment);
    }

}
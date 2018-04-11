package zrna;

import jdk.nashorn.internal.parser.Token;
import sifranti.*;
import student.Zeton;
import student.ZetonId;
import vloge.Kandidat;
import vloge.Student;
import vpis.Vpis;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ZetonZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    @Inject
    private KandidatZrno kandidatZrno;

    @Inject
    private UserTransaction ux;

    public List<Zeton> getTokens(Integer student) {
        if (student != null) {
            return em.createNamedQuery("entitete.vpis.Zeton.vrniZetoneZaStudenta", Zeton.class)
                    .setParameter("student", student)
                    .getResultList();
        }
        return em.createNamedQuery("entitete.vpis.Zeton.vrniVse", Zeton.class)
                .getResultList();
    }

    public Zeton getToken(Integer student, Integer vrstaVpisa) {
        ZetonId zetonId = new ZetonId(student, vrstaVpisa);
        return em.find(Zeton.class, zetonId);
    }

    public Zeton createTokenForCandidate(Integer id) throws Exception {
        Kandidat kandidat = em.find(Kandidat.class, id);
        if (kandidat == null) {
            throw new Exception("Kandidat ne obstaja");
        }
        ux.begin();

        kandidatZrno.createStudentFromCandidate(kandidat);

        em.flush();
        em.clear();
        Student student = em.find(Student.class, id);
        Zeton zeton = new Zeton();
        Letnik letnik = em.find(Letnik.class, 1);
        NacinStudija nacinStudija = em.find(NacinStudija.class, 1);
        OblikaStudija oblikaStudija = em.find(OblikaStudija.class, 1);
        StudijskoLeto studijskoLeto = (StudijskoLeto) em.createNamedQuery("entitete.sifranti.StudijskoLeto.vrniStudijkoLeto")
                .setParameter("studijskoLeto", Integer.toString(LocalDate.now().getYear()) + "%")
                .getSingleResult();
        StudijskiProgram studijskiProgram = kandidat.getStudijskiProgram();
        VrstaVpisa vrstaVpisa = em.find(VrstaVpisa.class, 1);

        zeton.setLetnik(letnik);
        zeton.setNacinStudija(nacinStudija);
        zeton.setOblikaStudija(oblikaStudija);
        zeton.setStudent(student);
        zeton.setStudijskiProgram(studijskiProgram);
        zeton.setVrstaVpisa(vrstaVpisa);
        zeton.setStudijskoLeto(studijskoLeto);

        em.persist(zeton);
        ux.commit();
        return zeton;
    }

    @Transactional
    public Zeton createTokenForStudent(Integer studentId) {
        Vpis zadnjiVpis = (Vpis) em.createNamedQuery("entitete.vpis.Vpis.zadnjiVpisZaStudenta")
                .setParameter("studentId", studentId)
                .getSingleResult();

        if (zadnjiVpis == null) {
            throw new EntityNotFoundException("Vpis ne obstaja!");
        }

        Zeton zeton = new Zeton();

        Integer leto = zadnjiVpis.getLetnik().getLetnik() + 1;
        Letnik letnik = em.find(Letnik.class, leto);

        NacinStudija nacin = zadnjiVpis.getNacinStudija();
        OblikaStudija oblika = zadnjiVpis.getOblikaStudija();
        boolean prostaIzbira = false;
        StudijskiProgram studijskiProgram = zadnjiVpis.getStudijskiProgram();
        StudijskoLeto studijskoLeto = (StudijskoLeto) em.createNamedQuery("entitete.sifranti.StudijskoLeto.vrniStudijkoLeto")
                .setParameter("studijskoLeto", Integer.toString(LocalDate.now().getYear()) + "%")
                .getSingleResult();

        zeton.setLetnik(letnik);
        zeton.setNacinStudija(nacin);
        zeton.setOblikaStudija(oblika);
        zeton.setProstaIzbira(prostaIzbira);
        zeton.setStudent(zadnjiVpis.getStudent());
        zeton.setStudijskiProgram(studijskiProgram);
        zeton.setVrstaVpisa(zadnjiVpis.getVrstaVpisa());
        zeton.setStudijskoLeto(studijskoLeto);

        em.persist(zeton);

        return zeton;
    }

    @Transactional
    public Zeton updateToken(Zeton zeton) {
        ZetonId zetonId = new ZetonId(zeton.getStudent().getId(), zeton.getVrstaVpisa().getSifraVpisa());
        Zeton newZeton = em.find(Zeton.class, zetonId);

        if (zeton.getVrstaVpisa() != null) {
            newZeton.setVrstaVpisa(zeton.getVrstaVpisa());
        }
        if (zeton.getStudijskoLeto() != null) {
            newZeton.setStudijskoLeto(zeton.getStudijskoLeto());
        }
        if (zeton.getStudijskiProgram() != null) {
            newZeton.setStudijskiProgram(zeton.getStudijskiProgram());
        }
        if (zeton.getOblikaStudija() != null) {
            newZeton.setOblikaStudija(zeton.getOblikaStudija());
        }
        if (zeton.getLetnik() != null) {
            newZeton.setLetnik(zeton.getLetnik());
        }

        zeton = em.merge(newZeton);
        return zeton;
    }

    @Transactional
    public void deleteToken(Integer student, Integer vrstaVpisa) {
        ZetonId zetonId = new ZetonId(student, vrstaVpisa);
        Zeton zeton = em.find(Zeton.class, zetonId);
        em.remove(zeton);
    }
}

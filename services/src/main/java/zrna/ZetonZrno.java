package zrna;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ws.rs.QueryParam;

import sifranti.Letnik;
import sifranti.NacinStudija;
import sifranti.OblikaStudija;
import sifranti.StudijskiProgram;
import sifranti.StudijskoLeto;
import sifranti.VrstaVpisa;
import student.Zeton;
import student.ZetonId;
import vloge.Kandidat;
import vloge.Student;
import vpis.Vpis;

@ApplicationScoped
public class ZetonZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    public List<Zeton> getTokens(Integer student, Boolean izkoriscen) {
        if (student != null) {
            if (izkoriscen != null) {
                return em.createNamedQuery("entitete.vpis.Zeton.vrniZetoneZaStudentaIzkoriscenost", Zeton.class)
                        .setParameter("student", student)
                        .setParameter("izkoriscen", izkoriscen)
                        .getResultList();
            }
            return em.createNamedQuery("entitete.vpis.Zeton.vrniZetoneZaStudenta", Zeton.class)
                    .setParameter("student", student)
                    .getResultList();
        }
        if (izkoriscen == null) {
            return em.createNamedQuery("entitete.vpis.Zeton.vrniVse", Zeton.class)
                    .getResultList();
        } else {
            return em.createNamedQuery("entitete.vpis.Zeton.vrniVseIzkoriscenost", Zeton.class)
                    .setParameter("izkoriscen", izkoriscen)
                    .getResultList();
        }
    }

    public Zeton getToken(Integer student, Integer vrstaVpisa) {
        ZetonId zetonId = new ZetonId(student, vrstaVpisa);
        return em.find(Zeton.class, zetonId);
    }

    @Transactional
    public Zeton createTokenForCandidate(Student student, StudijskiProgram studijskiProgram) throws Exception {
        Zeton zeton = new Zeton();
        Letnik letnik = em.find(Letnik.class, 1);
        NacinStudija nacinStudija = em.find(NacinStudija.class, 1);
        OblikaStudija oblikaStudija = em.find(OblikaStudija.class, 1);
        StudijskoLeto studijskoLeto = (StudijskoLeto) em.createNamedQuery("entitete.sifranti.StudijskoLeto.vrniStudijskoLeto")
                .setParameter("studijskoLeto", Integer.toString(LocalDate.now().getYear() - 1) + "%")
                .getSingleResult();
        VrstaVpisa vrstaVpisa = em.find(VrstaVpisa.class, 1);

        zeton.setLetnik(letnik);
        zeton.setNacinStudija(nacinStudija);
        zeton.setOblikaStudija(oblikaStudija);
        zeton.setStudent(student);
        zeton.setStudijskiProgram(studijskiProgram);
        zeton.setVrstaVpisa(vrstaVpisa);
        zeton.setStudijskoLeto(studijskoLeto);

        em.persist(zeton);
        return zeton;
    }

    @Transactional
    public Zeton createTokenForStudent(Integer studentId) {
        Vpis zadnjiVpis = (Vpis) em.createNamedQuery("entitete.vpis.Vpis.vpisiZaStudenta")
                .setParameter("student", studentId)
                .getResultList().get(0);

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
        StudijskoLeto studijskoLeto = (StudijskoLeto) em.createNamedQuery("entitete.sifranti.StudijskoLeto.vrniStudijskoLeto")
                .setParameter("studijskoLeto", Integer.toString(LocalDate.now().getYear() - 1) + "%")
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
    public Zeton updateToken(Zeton zeton, Integer vrstaVpisa) {
        ZetonId zetonId = new ZetonId(zeton.getStudent().getId(), vrstaVpisa);
        Zeton oldToken = em.find(Zeton.class, zetonId);

        if (oldToken.isIzkoriscen())
            return oldToken;

        if (zeton.getVrstaVpisa() != null && !zeton.getVrstaVpisa().getSifraVpisa().equals(vrstaVpisa)) {
            Zeton newToken = new Zeton();
            newToken.setVrstaVpisa(zeton.getVrstaVpisa());
            newToken.setStudent(zeton.getStudent());
            newToken.setStudijskiProgram(zeton.getStudijskiProgram());
            newToken.setLetnik(zeton.getLetnik());
            newToken.setStudijskoLeto(zeton.getStudijskoLeto());
            newToken.setNacinStudija(zeton.getNacinStudija());
            newToken.setOblikaStudija(zeton.getOblikaStudija());
            newToken.setProstaIzbira(zeton.isProstaIzbira());

            em.remove(oldToken);
            em.persist(newToken);

            em.flush();
            em.clear();
            ZetonId newTokenId = new ZetonId(newToken.getStudent().getId(), newToken.getVrstaVpisa().getSifraVpisa());
            newToken = em.find(Zeton.class, newTokenId);

            return newToken;
        }
        if (zeton.getStudijskoLeto() != null) {
            oldToken.setStudijskoLeto(zeton.getStudijskoLeto());
        }
        if (zeton.getStudijskiProgram() != null) {
            oldToken.setStudijskiProgram(zeton.getStudijskiProgram());
        }
        if (zeton.getOblikaStudija() != null) {
            oldToken.setOblikaStudija(zeton.getOblikaStudija());
        }
        if (zeton.getLetnik() != null) {
            oldToken.setLetnik(zeton.getLetnik());
        }
        oldToken.setProstaIzbira(zeton.isProstaIzbira());

        zeton = em.merge(oldToken);
        return zeton;
    }

    @Transactional
    public void deleteToken(Integer student, Integer vrstaVpisa) {
        ZetonId zetonId = new ZetonId(student, vrstaVpisa);
        Zeton zeton = em.find(Zeton.class, zetonId);
        if (!zeton.isIzkoriscen()) {
            em.remove(zeton);
        }
    }
}

package zrna;

import sifranti.*;
import student.Zeton;
import student.ZetonId;
import vloge.Student;
import vpis.Vpis;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ZetonZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    public List<Zeton> getTokens() {
        return em.createNamedQuery("entitete.vpis.Zeton.vrniVse")
                .getResultList();
    }

    public Zeton getToken(Integer student, Integer vrstaVpisa) {

        ZetonId zetonId = new ZetonId(student, vrstaVpisa);
        return em.find(Zeton.class, zetonId);
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

        em.persist(zeton);

        return zeton;
    }

    @Transactional
    public Zeton updateToken(Zeton zeton) {
        zeton = em.merge(zeton);
        return zeton;
    }

    @Transactional
    public void deleteToken(Integer student, Integer vrstaVpisa) {
        ZetonId zetonId = new ZetonId(student, vrstaVpisa);
        Zeton zeton = em.find(Zeton.class, zetonId);
        em.remove(zeton);
    }
}

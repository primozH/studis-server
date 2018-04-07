package zrna;

import sifranti.*;
import student.Zeton;
import student.ZetonId;
import vloge.Student;
import vpis.Vpis;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class VpisZrno {

    @PersistenceContext(name = "studis")
    private EntityManager em;

    public Vpis vpisiStudenta(Zeton vpis) throws Exception{
        ZetonId zetonId = new ZetonId(vpis.getStudent().getId(), vpis.getVrstaVpisa().getSifraVpisa());
        Zeton zeton = em.find(Zeton.class, zetonId);

        if (zeton == null || zeton.isIzkoriscen()) {
            throw new Exception("Student nima pravice vpisa");
        }

        Letnik letnik = vpis.getLetnik();
        StudijskoLeto studijskoLeto = vpis.getStudijskoLeto();
        StudijskiProgram studijskiProgram = vpis.getStudijskiProgram();
        VrstaVpisa vrstaVpisa = vpis.getVrstaVpisa();
        NacinStudija nacinStudija = vpis.getNacinStudija();
        OblikaStudija oblikaStudija = vpis.getOblikaStudija();
        Student student = vpis.getStudent();

        Vpis vpisPotrjen = new Vpis(student, studijskoLeto, studijskiProgram, vrstaVpisa, nacinStudija, oblikaStudija, letnik);

        return vpisPotrjen;
    }

    public List<Vpis> getVpisi(Integer studentId) {
        return em.createNamedQuery("entitete.vpis.Vpis.vrniVpiseZaStudenta")
                .setParameter("studentId", studentId)
                .getResultList();
    }

    public void zakljuciVpis(Vpis vpis) {
        Student student = vpis.getStudent();
        em.persist(vpis);
        em.createNamedQuery("entitete.vpis.Zeton.nastaviIzkoriscenostZetona")
                .setParameter("student", student)
                .setParameter("izkoriscen", true)
                .executeUpdate();
    }

    public void enrollment() {
        // obvezni predmeti
        String naziv = "%obvezen%";
        DelPredmetnika delPredmetnika = (DelPredmetnika) em.createNamedQuery("entitete.sifranti.DelPredmetnika.vrniDelPredmetnika")
                .setParameter("tip", naziv)
                .getSingleResult();
    }


}

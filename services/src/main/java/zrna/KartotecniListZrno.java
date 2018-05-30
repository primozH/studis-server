package zrna;

import helpers.entities.KartotecniList;
import helpers.entities.Vrstica;
import izpit.Izpit;
import izpit.IzvajanjePredmeta;
import student.PredmetStudent;
import vloge.Student;
import vpis.Vpis;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class KartotecniListZrno {

    private final static Logger log = Logger.getLogger(KartotecniListZrno.class.getName());
    @PersistenceContext(name = "studis") private EntityManager em;

    @Inject
    private VpisZrno vpisZrno;

    public KartotecniList pripraviKartotecniList(Integer studentId) {
        log.info("Ustvarjam kartotecni list za studenta");

        Student student = em.find(Student.class, studentId);

        log.info("Pridobivanje vseh vpisov");
        List<Vpis> vpisi = vpisZrno.getVpisi(studentId);

        List<Vrstica> vrstice = new ArrayList<>();
        /* pridobi predmete za posamezne vpise*/
        for (Vpis vpis: vpisi) {
            log.info("Predmeti za vpis " + vpis.getStudijskoLeto().getStudijskoLeto() + ", " +
            vpis.getLetnik().getLetnik() + ". letnik, student " + studentId);
            List<PredmetStudent> predmeti = em.createNamedQuery("entitete.student.PredmetStudent.predmetiZaVpis", PredmetStudent.class)
                .setParameter("vpis", vpis)
                .getResultList();

            vpis.setStudent(null);
            Vrstica vrstica = new Vrstica();
            vrstica.setVpis(vpis);

            List<IzvajanjePredmeta> predmetiIzvajanje = new ArrayList<>();
            HashMap<Integer, List<Izpit>> ocene = new HashMap<>();
            /* pridobi vse ocene za predmete */
            int sumECTS = 0;
            double sumGrade = 0;
            int countGrade = 0;

            for (PredmetStudent predmet : predmeti) {
                log.info("Ocene in izvajalci predmeta za " + predmet.getPredmet().getSifra() +
                "; student " + studentId);
                IzvajanjePredmeta izvajanjePredmeta =
                        em.createNamedQuery("entitete.izpit.IzvajanjePredmeta.vrniIzvajanje", IzvajanjePredmeta.class)
                        .setParameter("predmet", predmet.getPredmet())
                        .setParameter("studijskoLeto", vpis.getStudijskoLeto())
                        .getSingleResult();

                izvajanjePredmeta.setStudijskoLeto(null);

                List<Izpit> izpiti = em.createNamedQuery("entitete.izpit.Izpit.vrniPolaganja", Izpit.class)
                        .setParameter("sifraPredmeta", predmet.getPredmet().getSifra())
                        .setParameter("studentId", studentId)
                        .getResultList();

                sumGrade += izpiti.stream().filter(izpit -> izpit.getKoncnaOcena() != null && izpit.getKoncnaOcena() > 5)
                        .mapToInt(Izpit::getKoncnaOcena)
                        .sum();
                countGrade += izpiti.stream().filter(izpit -> izpit.getKoncnaOcena() != null && izpit.getKoncnaOcena() > 5)
                        .count();

                sumECTS += izpiti.stream().filter(izpit -> izpit.getKoncnaOcena() != null && izpit.getKoncnaOcena() > 5)
                        .mapToInt(izpit -> izpit.getPredmet().getECTS())
                        .sum();

                predmet.setVpis(null);

                predmetiIzvajanje.add(izvajanjePredmeta);
                ocene.put(predmet.getPredmet().getSifra(), izpiti);
            }

            if (countGrade == 0) {
                vrstica.setPovprecnaOcena(0d);
            } else {
                vrstica.setPovprecnaOcena((sumGrade / countGrade));
            }
            vrstica.setKreditneTocke(sumECTS);

            vrstica.setPredmeti(predmetiIzvajanje);
            vrstica.setOceneZaPredmete(ocene);

            vrstice.add(vrstica);
        }

        KartotecniList kartotecniList = new KartotecniList();
        kartotecniList.setIme(student.getIme());
        kartotecniList.setPriimek(student.getPriimek());
        kartotecniList.setVpisnaStevilka(student.getVpisnaStevilka());
        kartotecniList.setVrstica(vrstice);

        log.info("Kartotecni list za studenta " + studentId + " ustvarjen");
        return kartotecniList;
    }
}

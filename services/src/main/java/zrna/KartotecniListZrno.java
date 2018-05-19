package zrna;

import helpers.entities.KartotecniList;
import helpers.entities.Vrstica;
import izpit.Izpit;
import izpit.IzvajanjePredmeta;
import student.PredmetStudent;
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
        List<Vpis> vpisi = vpisZrno.getVpisi(studentId);

        List<Vrstica> vrstice = new ArrayList<>();
        /* pridobi predmete za posamezne vpise*/
        for (Vpis vpis: vpisi) {
            List<PredmetStudent> predmeti = em.createNamedQuery("entitete.student.PredmetStudent.predmetiZaVpis", PredmetStudent.class)
                .setParameter("vpis", vpis)
                .getResultList();

            vpis.setStudent(null);
            Vrstica vrstica = new Vrstica();
            vrstica.setVpis(vpis);

            List<IzvajanjePredmeta> predmetiIzvajanje = new ArrayList<>();
            HashMap<Integer, List<Izpit>> ocene = new HashMap<>();
            /* pridobi vse ocene za predmete */
            for (PredmetStudent predmet : predmeti) {
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

                predmet.setVpis(null);

                predmetiIzvajanje.add(izvajanjePredmeta);
                ocene.put(predmet.getPredmet().getSifra(), izpiti);
            }

            vrstica.setPredmeti(predmetiIzvajanje);
            vrstica.setOceneZaPredmete(ocene);

            vrstice.add(vrstica);
        }

        KartotecniList kartotecniList = new KartotecniList();
        kartotecniList.setVrstica(vrstice);

        return kartotecniList;
    }
}

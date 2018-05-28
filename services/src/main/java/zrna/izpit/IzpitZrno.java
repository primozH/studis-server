package zrna.izpit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import helpers.entities.PrijavaNaIzpit;
import izpit.Izpit;
import izpit.PrijavaRok;
import sifranti.Predmet;
import vloge.Student;

@ApplicationScoped
public class IzpitZrno {

    private final static Logger log = Logger.getLogger(IzpitZrno.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void vnesiRezultateIzpita(List<PrijavaNaIzpit> izpiti, Integer rokId) {
        for (PrijavaNaIzpit izpit : izpiti) {
            PrijavaRok prijavaRok;
            try {
                prijavaRok = preveriPrijavo(izpit, rokId);
            } catch (NoResultException e) {
                log.warning("Ne najdem prijave na izpit za studenta " + izpit.getPrijavaRok().getStudent().getId()+ " in rok " + rokId);
                continue;
            }

            if (izpit.getOcenaPisno() != null &&
                    (izpit.getOcenaPisno() > 100 || izpit.getOcenaPisno() < 0)) {
                log.warning("Neveljaven vnos točk za pisni del " + izpit.getOcenaPisno());
                continue;
            }

            if (izpit.getKoncnaOcena() != null &&
                    (izpit.getKoncnaOcena() > 10 || izpit.getKoncnaOcena() < 5)) {
                log.warning("Neveljavna končna ocena");
            }

            Izpit stored = izpitObstaja(izpit.getPrijavaRok().getStudent(), rokId);
            stored = vnesiRezultate(stored, izpit, prijavaRok);

            if (stored.getKoncnaOcena() != null) {
                zakljuciPrijavo(prijavaRok);
            }
        }
    }

    private PrijavaRok preveriPrijavo(PrijavaNaIzpit izpit, Integer rokId) throws NoResultException {
        return em.createNamedQuery("entitete.izpit.PrijavaRok.vrniNebrisanoPrijavo", PrijavaRok.class)
                .setParameter("rok", rokId)
                .setParameter("studentId", izpit.getPrijavaRok().getStudent().getId())
                .getSingleResult();
    }

    private Izpit izpitObstaja(Student student, Integer rokId) {
        try {
            return em.createNamedQuery("entitete.izpit.Izpit.izpitZaStudenta", Izpit.class)
                    .setParameter("student", student.getId())
                    .setParameter("rok", rokId)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info("Izpit še ne obstaja za študenta " + student.getId() + " in rok " + rokId);
            return null;
        }
    }

    @Transactional
    private Izpit vnesiRezultate(Izpit stored, PrijavaNaIzpit izpit, PrijavaRok prijavaRok) {
        if (stored == null) {
            stored = new Izpit();
            Izpit zadnjePolaganje = zadnjePolaganje(izpit);

            if (izpit.getOcenaPisno() != null) stored.setOcenaPisno(izpit.getOcenaPisno());
            stored.setKoncnaOcena(izpit.getKoncnaOcena());
            stored.setPredmet(izpit.getPrijavaRok().getRok().getIzvajanjePredmeta().getPredmet());
            stored.setStudent(izpit.getPrijavaRok().getStudent());
            stored.setPrijavaRok(prijavaRok);

            stored.setStPolaganjaLeto((zadnjePolaganje != null ? zadnjePolaganje.getStPolaganjaLeto() : 0) + 1);
            stored.setStPolaganjaSkupno((zadnjePolaganje != null ? zadnjePolaganje.getStPolaganjaSkupno() : 0) + 1);

            em.persist(stored);
        } else {
            if (izpit.getOcenaPisno() != null) stored.setOcenaPisno(izpit.getOcenaPisno());
            stored.setKoncnaOcena(izpit.getKoncnaOcena());

            em.merge(stored);
        }

        return stored;
    }

    private Izpit zadnjePolaganje(PrijavaNaIzpit izpit) {
        List<Izpit> izpiti = em.createNamedQuery("entitete.izpit.Izpit.vrniPolaganja", Izpit.class)
                .setParameter("studentId", izpit.getPrijavaRok().getStudent().getId())
                .setParameter("sifraPredmeta", izpit.getPrijavaRok().getRok().getIzvajanjePredmeta().getPredmet().getSifra())
                .getResultList();
        if (izpiti.size() == 0) {
            return null;
        }

        return izpiti.get(0);
    }

    @Transactional
    private void zakljuciPrijavo(PrijavaRok prijavaRok) {
        prijavaRok.setZakljucena(true);

        em.merge(prijavaRok);
    }

    @Transactional
    public List<Izpit> vrniPrijavljeneKandidateZOcenami(int sifraRoka) throws Exception{
        List<Izpit> izpiti;
        try {
           izpiti = em.createNamedQuery("entitete.izpit.Izpit.vrniPodatkeOIzpituZaRok", Izpit.class)
                                   .setParameter("rok", sifraRoka)
                                   .getResultList();
        } catch (Exception e) {
            izpiti = new ArrayList<>();
        }
        List<PrijavaRok> prijave = em.createNamedQuery("entitete.izpit.PrijavaRok.vrniVsePrijaveZaRok", PrijavaRok.class)
              .setParameter("rok", sifraRoka)
              .getResultList();
        Iterator<PrijavaRok> prijaveIterator = prijave.iterator();
        Iterator<Izpit> izpitiIterator;
        List<Izpit> noviIzpiti = new ArrayList<>();
         log.info("prijavaRok " + Arrays.toString(prijave.toArray()));

        boolean najden;
        while (prijaveIterator.hasNext()) {
            najden = false;
            PrijavaRok prijavaRok = prijaveIterator.next();
             log.info("prijavaRok " + prijavaRok.getStudent().getId() + "  " + prijavaRok.getCasPrijave() + "  " + prijavaRok.isBrisana());
             izpitiIterator = izpiti.iterator();
            while (izpitiIterator.hasNext()) {
                Izpit izpit = izpitiIterator.next();
                // Vrne izpit, ce je enak kot casPrijave pri zadnjem prijavnemRoku
                if (prijavaRok.getStudent().getId() == izpit.getStudent().getId()
                    && (prijavaRok.getCasPrijave().isBefore(izpit.getPrijavaRok().getCasPrijave()) || prijavaRok.getCasPrijave().isEqual(izpit.getPrijavaRok().getCasPrijave()))
                        || izpit.getPrijavaRok() == null) {
                    najden = true;
                    noviIzpiti.add(izpit);
                    break;
                }
            }
            // Vzame zadnjo prijavo in izpit z oceno null, kljub temu, da ima vpisano oceno prejsnjega izpita
            if (!najden) {
                Izpit novIzpit = new Izpit();
                novIzpit.setPrijavaRok(prijavaRok);
                noviIzpiti.add(novIzpit);
                log.info("dodan " + prijavaRok.getStudent().getId() + "  " + prijavaRok.getCasPrijave() + "  " + prijavaRok.isBrisana());
            }
        }
        return noviIzpiti;
    }

    /**
     * Vpise koncno oceno za predmet.
     * @param prijavaNaIzpit V prijavaNaIzpit.prijavaRok so ze podatki IzpitniRok ter id Studenta.
     * @return
     * @throws Exception
     */
    @Transactional
    public Izpit vnesiKoncnoOceno(PrijavaNaIzpit prijavaNaIzpit) throws Exception {
        Izpit izpit = null;
        Integer koncnaOcena = prijavaNaIzpit.getKoncnaOcena();
        if (koncnaOcena == null
                || koncnaOcena < 5
                || koncnaOcena > 10) {
            throw new Exception("Format koncne ocene ni pravilen");
        }
        try {
             izpit = em.createNamedQuery("entitete.izpit.Izpit.vrniIzpitZaPrijavo", Izpit.class)
                            .setParameter("prijavaRokId", prijavaNaIzpit.getPrijavaRok().getId())
                            .getSingleResult();
        } catch (NoResultException | NullPointerException e) {
//            throw new Exception("Izpit za to prijavo ne obstaja");
            log.info("Izpit za studenta se ne obstaja, kreiramo novega.");
            Student student;
            try {
                student = em.createNamedQuery("entitete.vloge.Student.vrniStudentaPoVpisniStevilki",Student.class)
                        .setParameter("vpisnaStevilka", prijavaNaIzpit.getPrijavaRok().getStudent().getVpisnaStevilka())
                        .getSingleResult();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new Exception("Student ni bil najden");
            }
            prijavaNaIzpit.getPrijavaRok().setStudent(student);
            Predmet predmet;
            try {
                predmet = em.find(Predmet.class, prijavaNaIzpit.getPrijavaRok().getRok().getIzvajanjePredmeta().getPredmet().getSifra());
            } catch (Exception exc) {
                throw new Exception("Predmet ne obstaja");
            }
            prijavaNaIzpit.getPrijavaRok().getRok().getIzvajanjePredmeta().setPredmet(predmet);
        }
        return vnesiRezultate(izpit, prijavaNaIzpit, null);
    }

}

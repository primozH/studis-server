package zrna.izpit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import helpers.entities.PrijavaNaIzpit;
import izpit.Izpit;
import izpit.OdjavaIzpit;
import izpit.OpravljeniPredmetiStatistika;
import izpit.PrijavaRok;
import sifranti.Predmet;
import vloge.Student;
import vloge.Uporabnik;

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

            izpit.setIzprasevalec(prijavaRok.getRok().getIzvajalec());

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
            stored.setIzprasevalec(izpit.getIzprasevalec());

            if (zadnjePolaganje != null && prijavaRok != null &&
                    zadnjePolaganje.getPrijavaRok().getRok().getIzvajanjePredmeta().getStudijskoLeto().getId()
                            .equals(prijavaRok.getRok().getIzvajanjePredmeta().getStudijskoLeto().getId())) {
                stored.setStPolaganjaLeto(zadnjePolaganje.getStPolaganjaLeto() + 1);
            } else {
                stored.setStPolaganjaLeto(1);
            }
            stored.setStPolaganjaSkupno((zadnjePolaganje != null ? zadnjePolaganje.getStPolaganjaSkupno() : 0) + 1);

            if (prijavaRok != null) {
                stored.setDatum(prijavaRok.getRok().getDatum());
            }

            em.persist(stored);
        } else {
            if (izpit.getOcenaPisno() != null)
                stored.setOcenaPisno(izpit.getOcenaPisno());
            stored.setKoncnaOcena(izpit.getKoncnaOcena());

            stored.setIzprasevalec(izpit.getIzprasevalec());

            if (prijavaRok != null) {
                stored.setDatum(prijavaRok.getRok().getDatum());
            }
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
                    && (prijavaRok.getCasPrijave().isBefore(izpit.getPrijavaRok().getCasPrijave()) || prijavaRok.getCasPrijave().isEqual(izpit.getPrijavaRok().getCasPrijave()))) {
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

    @Transactional
    public Izpit vnesiKoncnoOceno(Izpit requestIzpit) throws Exception {
        PrijavaNaIzpit prijavaNaIzpit = new PrijavaNaIzpit();
        prijavaNaIzpit.setPrijavaRok(requestIzpit.getPrijavaRok());
        Integer koncnaOcena = requestIzpit.getKoncnaOcena();
        if (koncnaOcena == null
            || koncnaOcena < 5
            || koncnaOcena > 10) {
            throw new Exception("Format koncne ocene ni pravilen");
        }
        prijavaNaIzpit.setKoncnaOcena(requestIzpit.getKoncnaOcena());

        Izpit izpit = null;
        PrijavaRok prijavaRok = null;
        try {
            izpit = em.createNamedQuery("entitete.izpit.Izpit.vrniIzpitZaPrijavo", Izpit.class)
                      .setParameter("prijavaRokId", prijavaNaIzpit.getPrijavaRok().getId())
                      .getSingleResult();
        } catch (NoResultException | NullPointerException e) {
            log.info("Izpit za studenta se ne obstaja, poiscemo prijavo.");
            try {
                prijavaRok = em.createNamedQuery("entitete.izpit.PrijavaRok.vrniPrijavoZaId", PrijavaRok.class)
                               .setParameter("prijavaRokId", prijavaNaIzpit.getPrijavaRok().getId())
                               .getSingleResult();
                prijavaNaIzpit.setPrijavaRok(prijavaRok);
                log.info("Prijava na izpit obstaja");
            } catch (NoResultException | NullPointerException ex) {
                // throw new Exception("Prijava za ta izpit ne obstaja");
                log.info("Prijava na izpit ne obstaja");
                Student student;
                try {
                    student = em.createNamedQuery("entitete.vloge.Student.vrniStudentaPoVpisniStevilki", Student.class)
                                .setParameter("vpisnaStevilka", prijavaNaIzpit.getPrijavaRok().getStudent().getVpisnaStevilka())
                                .getSingleResult();
                } catch (NullPointerException exc) {
                    ex.printStackTrace();
                    throw new Exception("Student ni bil najden");
                }
                prijavaNaIzpit.getPrijavaRok().setStudent(student);
                Predmet predmet;
                try {
                    predmet =
                            em.find(Predmet.class, prijavaNaIzpit.getPrijavaRok().getRok().getIzvajanjePredmeta().getPredmet().getSifra());
                } catch (NullPointerException exc) {
                    throw new Exception("Predmet ne obstaja");
                }
                prijavaNaIzpit.getPrijavaRok().getRok().getIzvajanjePredmeta().setPredmet(predmet);
            }
        }
        izpit = vnesiRezultate(izpit, prijavaNaIzpit, prijavaRok);
        if (requestIzpit.getDatum() != null) {
            izpit.setDatum(requestIzpit.getDatum());
        }
        if (requestIzpit.getStPolaganjaLeto() != null) {
            log.info("stevilo polaganj leto nastavljeno");
            izpit.setStPolaganjaLeto(requestIzpit.getStPolaganjaLeto());
        }
        if (requestIzpit.getStPolaganjaSkupno() != null) {
            log.info("stevilo polaganj skupno");
            izpit.setStPolaganjaSkupno(requestIzpit.getStPolaganjaSkupno());
        }
        if (izpit.getKoncnaOcena() != null && izpit.getPrijavaRok() != null) {
            log.info("Koncna ocena zakljucena.");
            zakljuciPrijavo(izpit.getPrijavaRok());
        }
        em.merge(izpit);
        return izpit;
    }

    @Transactional
    public void vrniPrijavo(Integer rokId, Integer studentId, Uporabnik odjavitelj) throws Exception {
        log.info("Vracanje prijave");
        odjavitelj = em.find(Uporabnik.class, odjavitelj.getId());

        if (odjavitelj.getTip().equalsIgnoreCase("student")) {
            throw new Exception("Ni pravic za vračanje prijave");
        }

        PrijavaRok prijavaRok;
        try {
            prijavaRok = em.createNamedQuery("entitete.izpit.PrijavaRok.vrniNebrisanoPrijavo", PrijavaRok.class)
                    .setParameter("studentId", studentId)
                    .setParameter("rok", rokId)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new Exception("Ni prijave");
        }

        Izpit izpit = vrniIzpitZaPrijavo(prijavaRok.getId());

        if (izpit != null) {
            em.remove(izpit);
        }

        OdjavaIzpit odjavaIzpit = new OdjavaIzpit();
        odjavaIzpit.setOdjavitelj(odjavitelj);
        odjavaIzpit.setPrijavaRok(prijavaRok);

        em.persist(odjavaIzpit);

        prijavaRok.setBrisana(true);

        log.info("Prijava uspešno vrnjena");
    }


    private Izpit vrniIzpitZaPrijavo(Integer prijavaRokId) {
        try {
            return em.createNamedQuery("entitete.izpit.Izpit.vrniIzpitZaPrijavo", Izpit.class)
                    .setParameter("prijavaRokId", prijavaRokId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public List<OpravljeniPredmetiStatistika> vrniOpravljeneIzpite(Student student) throws Exception {
        try {
            List<Izpit> izpiti;
            izpiti = em.createNamedQuery("entitete.izpit.Izpit.pozitivniPredmeti", Izpit.class)
                       .setParameter("student", student.getId())
                       .getResultList();
            if (izpiti.isEmpty()) throw new NoResultException();
            Iterator<Izpit> iterator = izpiti.iterator();
            Map<Integer, Izpit> zadnjeOceneMap = new HashMap<>();
            while (iterator.hasNext()) {
                Izpit izpit = iterator.next();
                if (!zadnjeOceneMap.containsKey(izpit.getPredmet().getSifra())) {
                    zadnjeOceneMap.put(izpit.getPredmet().getSifra(), izpit);
                } else {
                    Izpit trenutnoNajbolsi = zadnjeOceneMap.get(izpit.getPredmet().getSifra());
                    if (trenutnoNajbolsi.getDatum().isBefore(izpit.getDatum()) ||
                            trenutnoNajbolsi.getDatum().isEqual(izpit.getDatum()) && izpit.getStPolaganjaSkupno() > trenutnoNajbolsi.getStPolaganjaSkupno()) {
                        zadnjeOceneMap.replace(izpit.getPredmet().getSifra(), izpit);
                    }
                }
            }

            Map<Integer, ArrayList<Izpit>> filtriraniIzpiti = new TreeMap<>();

            for (Izpit izpit : zadnjeOceneMap.values()) {
                int studijskoLeto = izpit.getDatum().getYear();
                if (izpit.getDatum().getMonth().getValue() < 10) {
                    studijskoLeto--;
                }

                log.info("studijsko leto = " + studijskoLeto + "  " + izpit.getPredmet().getNaziv() + "  " + izpit.getPredmet().getSifra());
                if (filtriraniIzpiti.containsKey(studijskoLeto)) {
                    ArrayList<Izpit> izpitArrayList = filtriraniIzpiti.get(studijskoLeto);
//                    izpitArrayList.add(izpit);
                    for (int i = 0; i< izpitArrayList.size(); i++) {
                        if (izpit.getDatum().isBefore(izpitArrayList.get(i).getDatum())) {
//                            log.info("trenutni = " + izpit.getDatum() + "  primerjani = " + izpitArrayList.get(i).getDatum());
                            izpitArrayList.add(i, izpit);
                            break;
                        }
                    }
                    if (!izpitArrayList.contains(izpit)) {
                        izpitArrayList.add(izpitArrayList.size(), izpit);
                    }
                    for (Izpit izpit1 : izpitArrayList) {
                        log.info("izpit = " + izpit1.getDatum());
                    }
                    filtriraniIzpiti.replace(studijskoLeto, izpitArrayList);
                } else {
                    ArrayList<Izpit> izpitList = new ArrayList<>();
                    izpitList.add(izpit);
                    filtriraniIzpiti.put(studijskoLeto, izpitList);
                }
                log.info("predmet = " + izpit.getPredmet().getSifra() + "  ocena = " + izpit.getKoncnaOcena() + " " + izpit.getStPolaganjaSkupno() + " " + izpit.getId());
            }

            List<OpravljeniPredmetiStatistika> opravljeniPredmetiStatistika = new ArrayList<>();
            for (int studijskoLeto : filtriraniIzpiti.keySet()) {
                opravljeniPredmetiStatistika.add(new OpravljeniPredmetiStatistika(filtriraniIzpiti.get(studijskoLeto)));
            }
            return opravljeniPredmetiStatistika;
        } catch (NoResultException e) {
            throw new Exception("Študent nima opravljenih izpitov");
        }
    }

}

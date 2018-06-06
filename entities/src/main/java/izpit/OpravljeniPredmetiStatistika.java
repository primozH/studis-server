package izpit;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import sifranti.StudijskoLeto;

public class OpravljeniPredmetiStatistika {

    private List<Izpit> opravljeniPredmeti;
    private int steviloOpravljenihPredmetov = 0;
    private int kreditneTocke = 0;
    private double skupnoPovprecje = 0;
    private StudijskoLeto studijskoLeto;

    public OpravljeniPredmetiStatistika(List<Izpit> opravljeniPredmeti) {
        double sestevekOcen = 0;
        for (Izpit izpit : opravljeniPredmeti) {
            steviloOpravljenihPredmetov ++;
            kreditneTocke += izpit.getPredmet().getECTS();
            sestevekOcen += izpit.getKoncnaOcena();
        }
        this.skupnoPovprecje = Double.valueOf(new DecimalFormat("#.##").format(sestevekOcen / steviloOpravljenihPredmetov));
        this.studijskoLeto = vrniStudijskoLeto(opravljeniPredmeti.get(0).getDatum());
        this.opravljeniPredmeti = opravljeniPredmeti;
    }

    public List<Izpit> getOpravljeniPredmeti() {
        return opravljeniPredmeti;
    }

    public void setOpravljeniPredmeti(List<Izpit> opravljeniPredmeti) {
        this.opravljeniPredmeti = opravljeniPredmeti;
    }

    public int getSteviloOpravljenihPredmetov() {
        return steviloOpravljenihPredmetov;
    }

    public void setSteviloOpravljenihPredmetov(int steviloOpravljenihPredmetov) {
        this.steviloOpravljenihPredmetov = steviloOpravljenihPredmetov;
    }

    public int getKreditneTocke() {
        return kreditneTocke;
    }

    public void setKreditneTocke(int kreditneTocke) {
        this.kreditneTocke = kreditneTocke;
    }

    public double getSkupnoPovprecje() {
        return skupnoPovprecje;
    }

    public void setSkupnoPovprecje(double skupnoPovprecje) {
        this.skupnoPovprecje = skupnoPovprecje;
    }

    public StudijskoLeto getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(StudijskoLeto studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
    }

    private StudijskoLeto vrniStudijskoLeto(LocalDate date) {
        StudijskoLeto studijskoLetoZaIzpit = new StudijskoLeto();
        int studijskoLeto =  date.getYear();
        if (date.getMonth().getValue() < 10) {
            studijskoLeto--;
        }
        studijskoLetoZaIzpit.setId(studijskoLeto);
        studijskoLetoZaIzpit.setStudijskoLeto(studijskoLeto +"/"+ (studijskoLeto + 1));
        return studijskoLetoZaIzpit;
    }
}

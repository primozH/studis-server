package izpit;

import java.util.List;

import sifranti.StudijskoLeto;

public class OpravljeniPredmetiStatistika {

    private List<Izpit> opravljeniPredmeti;
    private int steviloOpravljenihPredmetov = 0;
    private int kreditneTocke = 0;
    private int skupnoPovprecje = 0;
    private StudijskoLeto studijskoLeto;

    public OpravljeniPredmetiStatistika(List<Izpit> opravljeniPredmeti, StudijskoLeto studijskoLeto) {
        int sestevekOcen = 0;
        for (Izpit izpit : opravljeniPredmeti) {
            steviloOpravljenihPredmetov ++;
            kreditneTocke += izpit.getPredmet().getECTS();
            sestevekOcen += izpit.getKoncnaOcena();
        }
        skupnoPovprecje = sestevekOcen / opravljeniPredmeti.size();
        this.studijskoLeto = studijskoLeto;
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

    public int getSkupnoPovprecje() {
        return skupnoPovprecje;
    }

    public void setSkupnoPovprecje(int skupnoPovprecje) {
        this.skupnoPovprecje = skupnoPovprecje;
    }

    public StudijskoLeto getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(StudijskoLeto studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
    }
}

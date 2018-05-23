package helpers.entities;

import izpit.Izpit;
import izpit.IzvajanjePredmeta;
import vpis.Vpis;

import java.util.HashMap;
import java.util.List;

public class Vrstica {

    private Vpis vpis;
    private List<IzvajanjePredmeta> predmeti;
    private HashMap<Integer, List<Izpit>> oceneZaPredmete;
    private Double povprecnaOcena;
    private Integer kreditneTocke;

    public Vpis getVpis() {
        return vpis;
    }

    public void setVpis(Vpis vpis) {
        this.vpis = vpis;
    }

    public List<IzvajanjePredmeta> getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(List<IzvajanjePredmeta> predmeti) {
        this.predmeti = predmeti;
    }

    public HashMap<Integer, List<Izpit>> getOceneZaPredmete() {
        return oceneZaPredmete;
    }

    public void setOceneZaPredmete(HashMap<Integer, List<Izpit>> oceneZaPredmete) {
        this.oceneZaPredmete = oceneZaPredmete;
    }

    public Double getPovprecnaOcena() {
        return povprecnaOcena;
    }

    public void setPovprecnaOcena(Double povprecnaOcena) {
        this.povprecnaOcena = povprecnaOcena;
    }

    public Integer getKreditneTocke() {
        return kreditneTocke;
    }

    public void setKreditneTocke(Integer kreditneTocke) {
        this.kreditneTocke = kreditneTocke;
    }
}

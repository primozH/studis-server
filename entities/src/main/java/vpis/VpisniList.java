package vpis;

import sifranti.Predmet;
import student.Zeton;

import java.util.List;

public class VpisniList {

    private Zeton zeton;
    private Predmet strokovniPredmet;
    private List<Predmet> splosniPredmeti;
    private List<Predmet> modulskiPredmeti;

    public Zeton getZeton() {
        return zeton;
    }

    public void setZeton(Zeton zeton) {
        this.zeton = zeton;
    }

    public Predmet getStrokovniPredmet() {
        return strokovniPredmet;
    }

    public void setStrokovniPredmet(Predmet strokovniPredmet) {
        this.strokovniPredmet = strokovniPredmet;
    }

    public List<Predmet> getSplosniPredmeti() {
        return splosniPredmeti;
    }

    public void setSplosniPredmeti(List<Predmet> splosniPredmeti) {
        this.splosniPredmeti = splosniPredmeti;
    }

    public List<Predmet> getModulskiPredmeti() {
        return modulskiPredmeti;
    }

    public void setModulskiPredmeti(List<Predmet> modulskiPredmeti) {
        this.modulskiPredmeti = modulskiPredmeti;
    }
}

package vpis;

import sifranti.Predmet;
import student.Zeton;

import java.util.List;

public class VpisniList {

    private Integer zeton;
    private List<Predmet> strokovniPredmeti;
    private List<Predmet> splosniPredmeti;
    private List<Predmet> modulskiPredmeti;

    public Integer getZeton() {
        return zeton;
    }

    public void setZeton(Integer zeton) {
        this.zeton = zeton;
    }

    public List<Predmet> getStrokovniPredmeti() {
        return strokovniPredmeti;
    }

    public void setStrokovniPredmeti(List<Predmet> strokovniPredmeti) {
        this.strokovniPredmeti = strokovniPredmeti;
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

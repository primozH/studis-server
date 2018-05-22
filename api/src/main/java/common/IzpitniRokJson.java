package common;

import izpit.IzpitniRok;
import sifranti.Predmet;

import java.io.Serializable;
import java.util.List;

public class IzpitniRokJson implements Serializable {
    private Predmet predmet;
    private List<IzpitniRok> roki;
    private Integer prijavljenId;

    public IzpitniRokJson(Predmet predmet) {
        this(predmet, null);
    }

    public IzpitniRokJson(Predmet predmet, List<IzpitniRok> roki) {
        this.predmet = predmet;
        this.roki = roki;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public List<IzpitniRok> getRoki() {
        return roki;
    }

    public void setRoki(List<IzpitniRok> roki) {
        this.roki = roki;
    }

    public Integer getPrijavljenId() {
        return prijavljenId;
    }

    public void setPrijavljenId(Integer prijavljenId) {
        this.prijavljenId = prijavljenId;
    }
}

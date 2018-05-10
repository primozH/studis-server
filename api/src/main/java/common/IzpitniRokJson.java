package common;

import izpit.IzpitniRok;
import sifranti.Predmet;

import java.io.Serializable;
import java.util.List;

public class IzpitniRokJson implements Serializable {
    private Predmet predmet;
    private List<IzpitniRok> roki;
    private boolean prijavljen;

    public IzpitniRokJson(Predmet predmet) {
        this(predmet, null, false);
    }

    public IzpitniRokJson(Predmet predmet, List<IzpitniRok> roki, boolean prijavljen) {
        this.predmet = predmet;
        this.roki = roki;
        this.prijavljen = prijavljen;
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

    public boolean isPrijavljen() {
        return prijavljen;
    }

    public void setPrijavljen(boolean prijavljen) {
        this.prijavljen = prijavljen;
    }
}

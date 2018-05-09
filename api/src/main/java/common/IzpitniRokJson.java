package common;

import helpers.adapters.LocalDateAdapter;
import izpit.IzvajanjePredmeta;
import vloge.Ucitelj;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;

public class IzpitniRokJson implements Serializable {
    private IzvajanjePredmeta izvajanjePredmeta;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate datum;

    private Time cas;

    private Ucitelj izvajalec;

    private String prostor;

    private boolean prijavljen;

    public IzpitniRokJson(IzvajanjePredmeta izvajanjePredmeta, LocalDate datum, Time cas, Ucitelj izvajalec, String prostor, boolean prijavljen) {
        this.izvajanjePredmeta = izvajanjePredmeta;
        this.datum = datum;
        this.cas = cas;
        this.izvajalec = izvajalec;
        this.prostor = prostor;
        this.prijavljen = prijavljen;
    }

    public IzvajanjePredmeta getIzvajanjePredmeta() {
        return izvajanjePredmeta;
    }

    public void setIzvajanjePredmeta(IzvajanjePredmeta izvajanjePredmeta) {
        this.izvajanjePredmeta = izvajanjePredmeta;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Time getCas() {
        return cas;
    }

    public void setCas(Time cas) {
        this.cas = cas;
    }

    public Ucitelj getIzvajalec() {
        return izvajalec;
    }

    public void setIzvajalec(Ucitelj izvajalec) {
        this.izvajalec = izvajalec;
    }

    public String getProstor() {
        return prostor;
    }

    public void setProstor(String prostor) {
        this.prostor = prostor;
    }

    public boolean isPrijavljen() {
        return prijavljen;
    }

    public void setPrijavljen(boolean prijavljen) {
        this.prijavljen = prijavljen;
    }
}

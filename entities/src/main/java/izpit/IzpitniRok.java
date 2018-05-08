package izpit;

import java.sql.Time;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import helpers.adapters.LocalDateAdapter;
import vloge.Ucitelj;

@Entity
@Table(name = "rok")
@IdClass(IzpitniRokId.class)
@NamedQueries(value = {
        @NamedQuery(name = "entitete.izpit.IzpitniRok.vrniIzpitneRokeZaPredmet", query = "SELECT i FROM IzpitniRok i " +
                "WHERE i.izvajanjePredmeta.predmet.sifra = :sifraPredmeta " +
                "AND i.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto"),
        /* TODO */
        @NamedQuery(name = "entitete.izpit.IzpitniRok.vrniIzpitneRoke", query = "SELECT i " +
                "FROM IzpitniRok i, PredmetStudent p "),
        @NamedQuery(name = "entitete.izpit.IzpitniRok.vrniIzpitneRokeZaTaDan",
        query = "SELECT i FROM IzpitniRok i " +
                "WHERE i.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto " +
                "AND i.datum = :datum")
})
public class IzpitniRok {

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "predmet", referencedColumnName = "predmet"),
            @JoinColumn(name = "studijsko_leto", referencedColumnName = "studijsko_leto")
    })
    private IzvajanjePredmeta izvajanjePredmeta;

    @Id
    @Column(name = "datum")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate datum;

    @Column(name = "cas")
    private Time cas;

    @ManyToOne
    @JoinColumn(name = "izvajalec")
    private Ucitelj izvajalec;

    @Column(name = "prostor")
    private String prostor;

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
}

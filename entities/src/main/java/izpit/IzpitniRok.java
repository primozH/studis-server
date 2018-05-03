package izpit;

import java.time.LocalDateTime;

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

import helpers.adapters.LocalDateTimeAdapter;
import vloge.Ucitelj;

@Entity
@Table(name = "rok")
@IdClass(IzpitniRokId.class)
@NamedQueries(value = {
        @NamedQuery(name = "entities.izpit.IzpitniRok.vrniIzpitneRokeZaPredmet", query = "SELECT i FROM IzpitniRok i WHERE i.izvajanjePredmeta.predmet.sifra = :sifraPredmeta")
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
    @Column(name = "datum_cas")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime datumCasIzvajanja;

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

    public LocalDateTime getDatumCasIzvajanja() {
        return datumCasIzvajanja;
    }

    public void setDatumCasIzvajanja(LocalDateTime datumCasIzvajanja) {
        this.datumCasIzvajanja = datumCasIzvajanja;
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

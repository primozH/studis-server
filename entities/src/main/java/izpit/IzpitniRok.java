package izpit;

import vloge.Ucitelj;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rok")
@IdClass(IzpitniRokId.class)
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

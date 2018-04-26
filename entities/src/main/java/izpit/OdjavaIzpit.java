package izpit;

import vloge.Uporabnik;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "odjava")
@IdClass(OdjavaIzpitId.class)
public class OdjavaIzpit {

    @ManyToOne
    @JoinColumn(name = "odjavitelj")
    private Uporabnik odjavitelj;

    @Column(name = "cas_odjave")
    private LocalDateTime casOdjave;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "predmet", referencedColumnName = "predmet"),
            @JoinColumn(name = "student", referencedColumnName = "student"),
            @JoinColumn(name = "studijsko_leto", referencedColumnName = "studijsko_leto"),
            @JoinColumn(name = "datum_izvajanja", referencedColumnName = "datum_izvajanja")
    })
    private PrijavaIzpit prijavaIzpit;

    @PrePersist
    void setTime() {
        casOdjave = LocalDateTime.now();
    }

    public Uporabnik getOdjavitelj() {
        return odjavitelj;
    }

    public void setOdjavitelj(Uporabnik odjavitelj) {
        this.odjavitelj = odjavitelj;
    }

    public LocalDateTime getCasOdjave() {
        return casOdjave;
    }

    public void setCasOdjave(LocalDateTime casOdjave) {
        this.casOdjave = casOdjave;
    }

    public PrijavaIzpit getPrijavaIzpit() {
        return prijavaIzpit;
    }

    public void setPrijavaIzpit(PrijavaIzpit prijavaIzpit) {
        this.prijavaIzpit = prijavaIzpit;
    }
}

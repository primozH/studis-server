package izpit;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "izpit")
public class Izpit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "student", referencedColumnName = "student"),
            @JoinColumn(name = "predmet", referencedColumnName = "predmet"),
            @JoinColumn(name = "studijsko_leto", referencedColumnName = "studijsko_leto"),
            @JoinColumn(name = "datum_izvajanja", referencedColumnName = "datum_izvajanja")
    })
    private PrijavaIzpit prijavaIzpit;

    @Column(name = "ocena_ustno")
    private Integer ocenaUstno;
    @Column(name = "ocena_pisno")
    private Integer ocenaPisno;
    @Column(name = "koncna_ocena")
    private Integer koncnaOcena;

    @Column(name = "datum")
    private LocalDate datum;

    @PrePersist
    @PreUpdate
    void updateDatum() {
        datum = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PrijavaIzpit getPrijavaIzpit() {
        return prijavaIzpit;
    }

    public void setPrijavaIzpit(PrijavaIzpit prijavaIzpit) {
        this.prijavaIzpit = prijavaIzpit;
    }

    public Integer getOcenaUstno() {
        return ocenaUstno;
    }

    public void setOcenaUstno(Integer ocenaUstno) {
        this.ocenaUstno = ocenaUstno;
    }

    public Integer getOcenaPisno() {
        return ocenaPisno;
    }

    public void setOcenaPisno(Integer ocenaPisno) {
        this.ocenaPisno = ocenaPisno;
    }

    public Integer getKoncnaOcena() {
        return koncnaOcena;
    }

    public void setKoncnaOcena(Integer koncnaOcena) {
        this.koncnaOcena = koncnaOcena;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
}

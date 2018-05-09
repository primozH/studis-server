package izpit;

import vloge.Uporabnik;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "odjava")
public class OdjavaIzpit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "odjavitelj")
    private Uporabnik odjavitelj;

    @Column(name = "cas_odjave")
    private LocalDateTime casOdjave;

    @ManyToOne
    @JoinColumn(name = "prijava_id")
    private PrijavaRok prijavaRok;

    @PrePersist
    void setTime() {
        casOdjave = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public PrijavaRok getPrijavaRok() {
        return prijavaRok;
    }

    public void setPrijavaRok(PrijavaRok prijavaRok) {
        this.prijavaRok = prijavaRok;
    }
}

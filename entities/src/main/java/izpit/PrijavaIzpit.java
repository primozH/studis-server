package izpit;

import helpers.adapters.LocalDateTimeAdapter;
import student.PredmetStudent;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@Entity
@Table(name = "prijava_izpit")
@IdClass(PrijavaIzpitId.class)
public class PrijavaIzpit {

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "predmet", referencedColumnName = "predmet", insertable = false, updatable = false),
            @JoinColumn(name = "studijsko_leto", referencedColumnName = "studijsko_leto", insertable = false, updatable = false),
            @JoinColumn(name = "datum_izvajanja", referencedColumnName = "datum_cas")
    })
    private IzpitniRok rok;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "student", referencedColumnName = "student"),
            @JoinColumn(name = "studijsko_leto", referencedColumnName = "studijsko_leto"),
            @JoinColumn(name = "predmet", referencedColumnName = "predmet")
    })
    private PredmetStudent predmetStudent;

    @Column(name = "cas_prijave")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime casPrijave;

    @Column(name = "brisana")
    private boolean brisana;

    @PrePersist
    void setTime() {
        casPrijave = LocalDateTime.now();
    }

    public IzpitniRok getRok() {
        return rok;
    }

    public void setRok(IzpitniRok rok) {
        this.rok = rok;
    }

    public PredmetStudent getPredmetStudent() {
        return predmetStudent;
    }

    public void setPredmetStudent(PredmetStudent predmetStudent) {
        this.predmetStudent = predmetStudent;
    }

    public LocalDateTime getCasPrijave() {
        return casPrijave;
    }

    public void setCasPrijave(LocalDateTime casPrijave) {
        this.casPrijave = casPrijave;
    }

    public boolean isBrisana() {
        return brisana;
    }

    public void setBrisana(boolean brisana) {
        this.brisana = brisana;
    }
}

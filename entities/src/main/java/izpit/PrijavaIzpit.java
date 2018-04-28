package izpit;

import helpers.adapters.LocalDateTimeAdapter;
import student.PredmetStudent;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prijava_izpit")
@IdClass(PrijavaIzpitId.class)
@NamedQueries({
        @NamedQuery(name = "entitete.izpit.PrijavaIzpit.stejPrijave", query = "SELECT COUNT(p) FROM PrijavaIzpit p WHERE " +
                "p.predmetStudent.vpis.student = :student " +
                "AND p.predmetStudent.predmet = :predmet " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaIzpit.stejPrijaveStudijskoLeto", query = "SELECT COUNT(p) FROM PrijavaIzpit p WHERE " +
                "p.predmetStudent.vpis.student = :student " +
                "AND p.predmetStudent.vpis.studijskoLeto = :studijskoLeto " +
                "AND p.predmetStudent.predmet = :predmet " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaIzpit.preveriZaOpravljenIzpit", query = "SELECT i FROM Izpit i WHERE " +
                "i.predmet = :predmet " +
                "AND i.student = :student"),
        @NamedQuery(name = "entitete.izpit.PrijavaIzpit.aktivnePrijave", query = "SELECT p FROM PrijavaIzpit p WHERE " +
                "p.predmetStudent = :predmetStudent " +
                "AND p.rok = :rok " +
                "AND p.zakljucena = FALSE " +
                "AND p.brisana = FALSE")
})
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

    @Column(name = "cena", precision = 7, scale = 2)
    private BigDecimal cena;

    @Column(name = "valuta")
    private String valuta;

    @Column(name = "brisana")
    private boolean brisana;

    @Column(name = "zakljucena")
    private boolean zakljucena;

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

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public boolean isZakljucena() {
        return zakljucena;
    }

    public void setZakljucena(boolean zakljucena) {
        this.zakljucena = zakljucena;
    }
}


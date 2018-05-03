package izpit;

import java.math.BigDecimal;
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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import helpers.adapters.LocalDateTimeAdapter;
import student.PredmetStudent;

@Entity
@Table(name = "prijava_izpit")
@IdClass(PrijavaIzpitId.class)
@NamedQueries(value = {
        @NamedQuery(name = "entities.izpit.PrijavaIzpit.vrniZadnjoPrijavo",
                query = "SELECT p FROM PrijavaIzpit p WHERE p.predmetStudent.predmet.sifra = :sifraPredmeta " +
                        "AND p.predmetStudent.vpis.student.id = :studentId ORDER BY p.casPrijave DESC"),
        @NamedQuery(name = "entities.izpit.PrijavaIzpit.vrniPrijavo",
                query = "SELECT p FROM PrijavaIzpit p WHERE p.predmetStudent.predmet.sifra = :sifraPredmeta " +
                        "AND p.predmetStudent.vpis.student.id = :studentId " +
                        "AND p.predmetStudent.vpis.studijskoLeto.id = :studijskoLeto"),
        @NamedQuery(name = "entitete.izpit.PrijavaIzpit.stejPrijave", query = "SELECT COUNT(p) FROM PrijavaIzpit p WHERE " +
                "p.predmetStudent.vpis.student.id = :student " +
                "AND p.predmetStudent.predmet.sifra = :predmet " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaIzpit.stejPrijaveStudijskoLeto", query = "SELECT COUNT(p) FROM PrijavaIzpit p WHERE " +
                "p.predmetStudent.vpis.student.id = :student " +
                "AND p.predmetStudent.vpis.studijskoLeto.id = :studijskoLeto " +
                "AND p.predmetStudent.predmet.sifra = :predmet " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaIzpit.preveriZaOpravljenIzpit", query = "SELECT i FROM Izpit i WHERE " +
                "i.predmet.sifra = :predmet " +
                "AND i.student.id = :student " +
                "AND i.koncnaOcena > 5"),
        @NamedQuery(name = "entitete.izpit.PrijavaIzpit.aktivnePrijave", query = "SELECT p FROM PrijavaIzpit p WHERE " +
                "p.predmetStudent.predmet.sifra = :predmet " +
                "AND p.predmetStudent.vpis.student.id = :student " +
                "AND p.predmetStudent.vpis.studijskoLeto.id = :studijskoLeto " +
                "AND p.rok.datumCasIzvajanja = :datumCas " +
                "AND p.zakljucena = FALSE " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaIzpit.prijavljeniStudentje", query = "SELECT p FROM PrijavaIzpit p WHERE " +
                "p.predmetStudent.predmet.sifra = :predmet " +
                "AND p.predmetStudent.vpis.studijskoLeto.id = :studijskoLeto " +
                "AND p.rok.datumCasIzvajanja = :datumCas " +
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


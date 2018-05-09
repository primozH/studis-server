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
import vloge.Student;

@Entity
@Table(name = "prijava_rok")
@IdClass(PrijavaRokId.class)
@NamedQueries(value = {
        @NamedQuery(name = "entitete.izpit.PrijavaRok.vrniZadnjoPrijavo",
                query = "SELECT p FROM PrijavaRok p WHERE p.rok.izvajanjePredmeta.predmet.sifra = :sifraPredmeta " +
                        "AND p.student.id = :studentId ORDER BY p.casPrijave DESC"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.vrniPrijavo",
                query = "SELECT p FROM PrijavaRok p WHERE p.rok.izvajanjePredmeta.predmet.sifra = :sifraPredmeta " +
                        "AND p.student.id = :studentId " +
                        "AND p.rok.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.stejPrijave", query = "SELECT COUNT(p) FROM PrijavaRok p WHERE " +
                "p.student.id = :student " +
                "AND p.rok.izvajanjePredmeta.predmet.sifra = :predmet " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.stejPrijaveStudijskoLeto", query = "SELECT COUNT(p) FROM PrijavaRok p WHERE " +
                "p.student.id = :student " +
                "AND p.rok.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto " +
                "AND p.rok.izvajanjePredmeta.predmet.sifra = :predmet " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.aktivnePrijave", query = "SELECT p FROM PrijavaRok p WHERE " +
                "p.rok.izvajanjePredmeta.predmet.sifra = :predmet " +
                "AND p.student.id = :student " +
                "AND p.rok.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto " +
                "AND p.rok.datum = :datum " +
                "AND p.zakljucena = FALSE " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.prijavljeniStudentje", query = "SELECT p FROM PrijavaRok p WHERE " +
                "p.rok.izvajanjePredmeta.predmet.sifra = :predmet " +
                "AND p.rok.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto " +
                "AND p.rok.datum = :datum " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.prijaveZaStudenta",
                query = "SELECT p FROM PrijavaRok p WHERE " +
                        "p.student.id = :student " +
                        "AND p.brisana = FALSE " +
                        "AND p.zakljucena = FALSE"),
        @NamedQuery(name = "test", query = "SELECT p FROM PrijavaRok p, Vpis v " +
                "WHERE p.rok.izvajanjePredmeta.predmet.sifra = :predmet " +
                "AND v.studijskoLeto = p.rok.izvajanjePredmeta.studijskoLeto " +
                "AND v.vrstaVpisa.sifraVpisa = 1 " +
                "AND p.student.id = :student ")
})
public class PrijavaRok {

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "predmet", referencedColumnName = "predmet"),
            @JoinColumn(name = "studijsko_leto", referencedColumnName = "studijsko_leto"),
            @JoinColumn(name = "datum_izvajanja", referencedColumnName = "datum")
    })
    private IzpitniRok rok;

    @Id
    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDateTime getCasPrijave() {
        return casPrijave;
    }

    public void setCasPrijave(LocalDateTime casPrijave) {
        this.casPrijave = casPrijave;
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

    public boolean isBrisana() {
        return brisana;
    }

    public void setBrisana(boolean brisana) {
        this.brisana = brisana;
    }

    public boolean isZakljucena() {
        return zakljucena;
    }

    public void setZakljucena(boolean zakljucena) {
        this.zakljucena = zakljucena;
    }
}


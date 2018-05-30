package izpit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import helpers.adapters.LocalDateTimeAdapter;
import vloge.Student;

@Entity
@Table(name = "prijava_rok")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.izpit.PrijavaRok.prijavaZaRokInPredmet",
                query = "SELECT p FROM PrijavaRok p WHERE p.rok.id = :rok " +
                        "AND p.student.id = :studentId " +
                        "AND p.brisana = FALSE " +
                        "AND p.zakljucena = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.zakljucenePrijave", query = "SELECT p FROM PrijavaRok p WHERE " +
                "p.rok.izvajanjePredmeta.predmet.sifra = :predmet " +
                "AND p.student.id = :studentId " +
                "AND p.zakljucena = TRUE " +
                "AND p.brisana = FALSE " +
                "ORDER BY p.rok.datum DESC"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.vrniNebrisanoPrijavo", query = "SELECT p FROM PrijavaRok p " +
                "WHERE p.rok.id = :rok " +
                "AND p.student.id = :studentId " +
                "AND p.brisana = FALSE "),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.aktivnePrijave", query = "SELECT p FROM PrijavaRok p WHERE " +
                "p.rok.izvajanjePredmeta.predmet.sifra = :predmet " +
                "AND p.student.id = :student " +
                "AND p.zakljucena = FALSE " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.prijavljeniStudentje", query = "SELECT p FROM PrijavaRok p WHERE " +
                "p.rok.id = :id " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.prijavljeniStudentjeCount", query = "SELECT COUNT(p) FROM PrijavaRok p WHERE " +
                "p.rok.id = :id " +
                "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.prijaveZaStudenta",
                query = "SELECT p FROM PrijavaRok p WHERE " +
                        "p.student.id = :student " +
                        "AND p.brisana = FALSE " +
                        "AND p.zakljucena = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.prijavljeniStudentiZOcenami",
                query = "SELECT p, i FROM PrijavaRok p LEFT OUTER JOIN Izpit i ON p = i.prijavaRok " +
                        "WHERE p.rok.id = :rokId " +
                        "AND p.brisana = FALSE"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.vrniVsePrijaveZaRok",
                query = "SELECT i FROM PrijavaRok i " +
                        "WHERE i.casPrijave = " +
                        "(SELECT MAX(ip.casPrijave) FROM PrijavaRok ip WHERE ip.rok.id = :rok " +
                        "AND ip.student.id = i.student.id GROUP BY ip.student.id)"),
        @NamedQuery(name = "entitete.izpit.PrijavaRok.vrniPrijavoZaId",
                query = "SELECT i FROM PrijavaRok i WHERE i.id = :prijavaRokId")
})
public class PrijavaRok {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "izpitni_rok")
    private IzpitniRok rok;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


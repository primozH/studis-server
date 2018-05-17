package izpit;

import sifranti.Predmet;
import vloge.Student;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "izpit")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.izpit.Izpit.vrniPolaganja",
                query = "SELECT i FROM Izpit i WHERE i.predmet.sifra = :sifraPredmeta " +
                        "AND i.student.id = :studentId " +
                        "ORDER BY i.datum DESC"),
        @NamedQuery(name = "entitete.izpit.Izpit.vrniIzpitZaLeto",
                query = "SELECT i FROM Izpit i WHERE i.predmet.sifra = :sifraPredmeta " +
                        "AND i.student.id = :studentId " +
                        "AND i.prijavaRok.rok.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto"),
        @NamedQuery(name = "entitete.izpit.Izpit.opravljeniIzpiti",
                query = "SELECT i FROM Izpit i WHERE " +
                        "i.student.id = :student " +
                        "AND i.koncnaOcena > 5"),
        @NamedQuery(name = "entitete.izpit.Izpit.opravljenIzpit", query = "SELECT i FROM Izpit i " +
                "WHERE i.student.id = :student " +
                "AND i.predmet.sifra = :predmet " +
                "AND i.koncnaOcena > 5"),
        @NamedQuery(name = "entitete.izpit.Izpit.opravljeniPredmeti",
                query = "SELECT i.predmet FROM Izpit i " +
                        "WHERE i.student.id = :student " +
                        "AND i.koncnaOcena > 5"),
        @NamedQuery(name = "entitete.izpit.Izpit.vneseneOceneZaRok",
                query = "SELECT i FROM Izpit i " +
                        "WHERE i.prijavaRok.rok.id = :rok"),
        @NamedQuery(name = "entitete.izpit.Izpit.izpitZaStudenta",
                query = "SELECT i FROM Izpit i " +
                        "WHERE i.student.id = :student " +
                        "AND i.prijavaRok.rok.id = :rok")
})
public class Izpit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "datum")
    private LocalDate datum;

    @ManyToOne
    @JoinColumn(name = "prijava_id")
    private PrijavaRok prijavaRok;

    @ManyToOne
    @JoinColumn(name = "predmet")
    private Predmet predmet;

    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;

    @Column(name = "zap_st_polaganja")
    private Integer zapStPolaganja;

    @Column(name = "ocena_ustno")
    private Integer ocenaUstno = null;
    @Column(name = "ocena_pisno")
    private Integer ocenaPisno = null;
    @Column(name = "koncna_ocena")
    private Integer koncnaOcena = null;


    @PrePersist
    void updateDatum() {
        datum = LocalDate.now();
    }

    public PrijavaRok getPrijavaRok() {
        return prijavaRok;
    }

    public void setPrijavaRok(PrijavaRok prijavaRok) {
        this.prijavaRok = prijavaRok;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZapStPolaganja() {
        return zapStPolaganja;
    }

    public void setZapStPolaganja(Integer zapStPolaganja) {
        this.zapStPolaganja = zapStPolaganja;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

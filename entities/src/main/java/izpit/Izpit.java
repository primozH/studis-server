package izpit;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import helpers.adapters.LocalDateAdapter;
import sifranti.Predmet;
import vloge.Student;

@Entity
@Table(name = "izpit")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.izpit.Izpit.vrniPolaganja",
                query = "SELECT i FROM Izpit i WHERE i.predmet.sifra = :sifraPredmeta " +
                        "AND i.student.id = :studentId " +
                        "ORDER BY i.prijavaRok.rok.datum DESC"),
        @NamedQuery(name = "entitete.izpit.Izpit.vrniIzpitZaLeto",
                query = "SELECT i FROM Izpit i WHERE i.predmet.sifra = :sifraPredmeta " +
                        "AND i.student.id = :studentId " +
                        "AND i.prijavaRok.rok.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto"),
        @NamedQuery(name = "entitete.izpit.Izpit.opravljeniIzpiti",
                query = "SELECT i FROM Izpit i WHERE " +
                        "i.student.id = :student " +
                        "AND i.koncnaOcena > 5"),
        @NamedQuery(name = "entitete.izpit.Izpit.opravljenIzpit",
                query = "SELECT i FROM Izpit i " +
                        "WHERE i.student.id = :student " +
                        "AND i.predmet.sifra = :predmet " +
                        "AND i.koncnaOcena > 5"),
        @NamedQuery(name = "entitete.izpit.Izpit.vneseneOceneZaRok",
                query = "SELECT i FROM Izpit i " +
                        "WHERE i.prijavaRok.rok.id = :rok"),
        @NamedQuery(name = "entitete.izpit.Izpit.izpitZaStudenta",
                query = "SELECT i FROM Izpit i " +
                        "WHERE i.student.id = :student " +
                        "AND i.prijavaRok.rok.id = :rok"),
        @NamedQuery(name = "entitete.izpit.Izpit.vrniPodatkeOIzpituZaRok",
                query = "SELECT i FROM Izpit i " +
                        "WHERE i.prijavaRok.rok.id = :rok"),
        @NamedQuery(name = "entitete.izpit.Izpit.vrniIzpitZaPrijavo",
                query = "SELECT i FROM Izpit i WHERE i.prijavaRok.id = :prijavaRokId"),
        @NamedQuery(name = "entitete.izpit.Izpit.vrniIzpiteZaPredmet",
                query = "SELECT i FROM Izpit i " +
                        "WHERE i.predmet.sifra = :predmet " +
                        "AND i.student.id = :student " +
                        "ORDER BY i.datum DESC, i.stPolaganjaSkupno DESC, i.id DESC"),
        @NamedQuery(name = "entitete.izpit.Izpit.vrniVseIzpiteZaStudenta",
                query = "SELECT i FROM Izpit i " +
                        "WHERE i.student.id = :student " +
                        "ORDER BY i.predmet.sifra DESC, i.datum DESC, i.stPolaganjaSkupno DESC, i.id DESC")
})
public class Izpit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "datum")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
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

    @Column(name = "st_polaganja_leto", nullable = false)
    private Integer stPolaganjaLeto;

    @Column(name = "st_polaganja_skupno", nullable = false)
    private Integer stPolaganjaSkupno;

    @Column(name = "ocena_ustno")
    private Integer ocenaUstno = null;
    @Column(name = "ocena_pisno")
    private Integer ocenaPisno = null;
    @Column(name = "koncna_ocena")
    private Integer koncnaOcena = null;


    @PrePersist @PreUpdate
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

    public Integer getStPolaganjaLeto() {
        return stPolaganjaLeto;
    }

    public void setStPolaganjaLeto(Integer zapStPolaganja) {
        this.stPolaganjaLeto = zapStPolaganja;
    }

    public Integer getStPolaganjaSkupno() {
        return stPolaganjaSkupno;
    }

    public void setStPolaganjaSkupno(Integer stPolaganjaSkupno) {
        this.stPolaganjaSkupno = stPolaganjaSkupno;
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

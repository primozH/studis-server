package izpit;

import java.time.LocalDate;

import javax.persistence.*;

import sifranti.Predmet;
import sifranti.StudijskoLeto;
import vloge.Student;

@Entity
@Table(name = "izpit")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.izpit.Izpit.vrniSteviloVsehPolaganj",
                query = "SELECT i FROM Izpit i WHERE i.predmet.sifra = :sifraPredmeta " +
                        "AND i.student.id = :studentId "),
        @NamedQuery(name = "entitete.izpit.Izpit.vrniIzpitZaLeto",
                query = "SELECT i FROM Izpit i WHERE i.predmet.sifra = :sifraPredmeta " +
                        "AND i.student.id = :studentId " +
                        "AND i.studijskoLeto.id = :studijskoLeto"),
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
        @NamedQuery(name = "entitete.izpit.Izpit.vrniIzpiteZZeVpisanoOceno",
        query = "SELECT i FROM Izpit i " +
                "WHERE i.ocenaPisno >= 0 " +
                "AND i.predmet.sifra = :sifraPredmeta " +
                "AND i.studijskoLeto.id = :studijskoLeto")
})
public class Izpit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "predmet")
    private Predmet predmet;

    @Column(name = "datum")
    private LocalDate datum;

    @ManyToOne
    @JoinColumn(name = "studijsko_leto")
    private StudijskoLeto studijskoLeto;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "student", referencedColumnName = "student", insertable = false, updatable = false),
            @JoinColumn(name = "predmet", referencedColumnName = "predmet", insertable = false, updatable = false),
            @JoinColumn(name = "studijsko_leto", referencedColumnName = "studijsko_leto", insertable = false, updatable = false),
            @JoinColumn(name = "datum_izvajanja", referencedColumnName = "datum_izvajanja")
    })
    private PrijavaRok prijavaRok;

    @Column(name = "zap_st_polaganja")
    private Integer zapStPolaganja;

    @Column(name = "ocena_ustno")
    private Integer ocenaUstno = -1;
    @Column(name = "ocena_pisno")
    private Integer ocenaPisno = -1;
    @Column(name = "koncna_ocena")
    private Integer koncnaOcena = -1;


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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public StudijskoLeto getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(StudijskoLeto studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
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
}

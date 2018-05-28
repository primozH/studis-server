package izpit;

import java.sql.Time;
import java.time.LocalDate;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import helpers.adapters.LocalDateAdapter;
import vloge.Ucitelj;

@Entity
@Table(name = "izpitni_rok")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.izpit.IzpitniRok.vrniIzpitneRokeZaPredmet", query = "SELECT i FROM IzpitniRok i " +
                "WHERE i.izvajanjePredmeta.predmet.sifra = :sifraPredmeta " +
                "AND i.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto " +
                "ORDER BY i.datum"),
        @NamedQuery(name = "entitete.izpit.IzpitniRok.vrniIzpitneRoke", query = "SELECT i " +
                "FROM IzpitniRok i " +
                "WHERE i.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto " +
                "ORDER BY i.datum ASC"),
        @NamedQuery(name = "entitete.izpit.IzpitniRok.izpitniRokiZaStudenta",
                query = "SELECT DISTINCT i FROM IzpitniRok i, PredmetStudent p " +
                        "WHERE p.predmet NOT IN (" +
                            "SELECT iz.predmet FROM Izpit iz " +
                            "WHERE iz.koncnaOcena > 5 " +
                            "AND iz.student.id = :student) " +
                        "AND p.predmet = i.izvajanjePredmeta.predmet " +
                        "AND p.vpis.student.id = :student " +
                        "AND i.datum > :datum " +
                        "ORDER BY i.datum"),
        @NamedQuery(name = "entitete.izpit.IzpitniRok.vrniIzpitneRokeZaTaDan",
        query = "SELECT i FROM IzpitniRok i " +
                "WHERE i.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto " +
                "AND i.datum = :datum"),
        @NamedQuery(name = "entitete.izpit.IzpitniRok.vrniIzpitniRok",
        query = "SELECT i FROM IzpitniRok i " +
                "WHERE i.izvajanjePredmeta.predmet.sifra = :predmet " +
                "AND i.izvajanjePredmeta.studijskoLeto.id = :studijskoLeto " +
                "AND i.datum = :datum")
})
public class IzpitniRok {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "predmet", referencedColumnName = "predmet"),
            @JoinColumn(name = "studijsko_leto", referencedColumnName = "studijsko_leto")
    })
    private IzvajanjePredmeta izvajanjePredmeta;

    @Column(name = "datum")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate datum;

    @Column(name = "cas")
    private Time cas;

    @ManyToOne
    @JoinColumn(name = "izvajalec")
    private Ucitelj izvajalec;

    @Column(name = "prostor")
    private String prostor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public IzvajanjePredmeta getIzvajanjePredmeta() {
        return izvajanjePredmeta;
    }

    public void setIzvajanjePredmeta(IzvajanjePredmeta izvajanjePredmeta) {
        this.izvajanjePredmeta = izvajanjePredmeta;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Time getCas() {
        return cas;
    }

    public void setCas(Time cas) {
        this.cas = cas;
    }

    public Ucitelj getIzvajalec() {
        return izvajalec;
    }

    public void setIzvajalec(Ucitelj izvajalec) {
        this.izvajalec = izvajalec;
    }

    public String getProstor() {
        return prostor;
    }

    public void setProstor(String prostor) {
        this.prostor = prostor;
    }
}

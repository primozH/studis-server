package sifranti;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "praznik")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.sifranti.Praznik.vrniPraznikZaTaDatum",
                query = "SELECT p FROM Praznik p WHERE " +
                        "p.datum = :datum " +
                        "OR SUBSTRING(p.datum, 1, 4) = '1900' " +
                        "AND SUBSTRING(p.datum, 6, 7) = SUBSTRING(:datum, 6, 7) " +
                        "AND SUBSTRING(p.datum, 9, 10) = SUBSTRING(:datum, 9, 10)")
})
public class Praznik {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "ime")
    private String ime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }
}

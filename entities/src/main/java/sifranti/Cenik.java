package sifranti;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cenik")
@NamedQueries({
        @NamedQuery(name = "entitete.sifranti.Cenik.cenaZaIzpit", query = "SELECT c FROM Cenik c WHERE c.id = 1")
})
public class Cenik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "cena")
    private BigDecimal cena;

    @Column(name = "valuta")
    private String valuta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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
}

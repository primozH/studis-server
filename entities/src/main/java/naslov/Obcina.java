package naslov;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import java.util.List;

@Entity
@Table(name = "obcina")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.naslov.Obcina.vseObcine", query = "SELECT o FROM Obcina o ORDER BY o.ime")
})
public class Obcina {

    @Id
    @Column(name = "sifra")
    private Integer sifra;
    @Column(name = "ime")
    private String ime;

    public Integer getSifra() {
        return sifra;
    }

    public void setSifra(Integer sifra) {
        this.sifra = sifra;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }
}

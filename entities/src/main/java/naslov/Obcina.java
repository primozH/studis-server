package naslov;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "obcina")
public class Obcina {
    private Integer sifra;
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

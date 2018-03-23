package sifranti;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oblika_studija")
public class OblikaStudija {

    @Id
    private Integer sifra;
    private String opis;
    private String opisEng;

    public Integer getSifra() {
        return sifra;
    }

    public void setSifra(Integer sifra) {
        this.sifra = sifra;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getOpisEng() {
        return opisEng;
    }

    public void setOpisEng(String opisEng) {
        this.opisEng = opisEng;
    }
}

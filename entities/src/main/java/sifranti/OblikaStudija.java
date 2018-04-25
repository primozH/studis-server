package sifranti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oblika_studija")
public class OblikaStudija {

    @Id
    @Column(name = "sifra")
    private Integer sifra;
    @Column(name = "opis")
    private String opis;
    @Column(name = "opis_ang")
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

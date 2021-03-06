package sifranti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Klasius")
@Table(name = "klasius")
public class Klasius {

    @Id
    @Column(name = "sifra")
    private Integer sifra;

    @Column(name = "opis")
    private String opis;
    @Column(name = "strokovni_naslov")
    private String strokovniNaslov;
    @Column(name = "raven_izobrazbe")
    private String ravenIzobrazbe;

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

    public String getStrokovniNaslov() {
        return strokovniNaslov;
    }

    public void setStrokovniNaslov(String strokovniNaslov) {
        this.strokovniNaslov = strokovniNaslov;
    }

    public String getRavenIzobrazbe() {
        return ravenIzobrazbe;
    }

    public void setRavenIzobrazbe(String ravenIzobrazbe) {
        this.ravenIzobrazbe = ravenIzobrazbe;
    }
}

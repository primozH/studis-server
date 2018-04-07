package sifranti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

@Entity
@Table(name = "vrsta_vpisa")
public class VrstaVpisa {

    @Id
    @Column(name = "sifra")
    private Integer sifraVpisa;
    @Column(name = "vrsta_vpisa")
    private String vrstaVpisa;

    public Integer getSifraVpisa() {
        return sifraVpisa;
    }

    public void setSifraVpisa(Integer sifraVpisa) {
        this.sifraVpisa = sifraVpisa;
    }

    public String getVrstaVpisa() {
        return vrstaVpisa;
    }

    public void setVrstaVpisa(String vrstaVpisa) {
        this.vrstaVpisa = vrstaVpisa;
    }
}

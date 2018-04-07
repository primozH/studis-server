package naslov;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

@Entity
@Table(name = "posta")
public class Posta {

    @XmlID
    @XmlElement
    @Id
    @Column(name = "postna_stevilka")
    private Integer postnaStevilka;
    @Column(name = "naziv_poste")
    private String nazivPoste;


    public Integer getPostnaStevilka() {
        return postnaStevilka;
    }

    public void setPostnaStevilka(Integer postnaStevilka) {
        this.postnaStevilka = postnaStevilka;
    }

    public String getNazivPoste() {
        return nazivPoste;
    }

    public void setNazivPoste(String nazivPoste) {
        this.nazivPoste = nazivPoste;
    }
}

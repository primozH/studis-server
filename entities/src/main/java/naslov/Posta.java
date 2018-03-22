package naslov;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "posta")
public class Posta {

    private Integer postnaStevilka;
    private String nazivPoste;

    private Drzava drzava;

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

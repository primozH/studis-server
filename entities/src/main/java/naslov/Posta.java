package naslov;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "posta")
public class Posta {

    @Id
    private Integer postnaStevilka;

    @ManyToOne(targetEntity = Obcina.class)
    @Id
    private Obcina obcina;

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

    public Obcina getObcina() {
        return obcina;
    }

    public void setObcina(Obcina obcina) {
        this.obcina = obcina;
    }
}

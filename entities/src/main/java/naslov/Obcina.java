package naslov;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "obcina")
public class Obcina {

    @Id
    private Integer sifra;
    private String ime;

    @ManyToOne(targetEntity = Drzava.class)
    private Drzava drzava;

    @OneToMany(targetEntity = Posta.class)
    private List<Posta> poste;

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

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    public List<Posta> getPoste() {
        return poste;
    }

    public void setPoste(List<Posta> poste) {
        this.poste = poste;
    }
}

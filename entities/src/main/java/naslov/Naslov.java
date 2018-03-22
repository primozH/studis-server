package naslov;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Naslov {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sifra;

    @ManyToOne(targetEntity = Posta.class)
    private Posta posta;

    private String ulica;
    private Integer hisnaStevilka;
    private String hisnaStevilkaDod;

    public Integer getSifra() {
        return sifra;
    }

    public void setSifra(Integer sifra) {
        this.sifra = sifra;
    }

    public Posta getPosta() {
        return posta;
    }

    public void setPosta(Posta posta) {
        this.posta = posta;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public Integer getHisnaStevilka() {
        return hisnaStevilka;
    }

    public void setHisnaStevilka(Integer hisnaStevilka) {
        this.hisnaStevilka = hisnaStevilka;
    }

    public String getHisnaStevilkaDod() {
        return hisnaStevilkaDod;
    }

    public void setHisnaStevilkaDod(String hisnaStevilkaDod) {
        this.hisnaStevilkaDod = hisnaStevilkaDod;
    }
}

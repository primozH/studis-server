package helpers.entities;

import java.util.List;

public class KartotecniList {

    private String ime;
    private String priimek;
    private Integer vpisnaStevilka;
    private List<Vrstica> vrstica;

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public Integer getVpisnaStevilka() {
        return vpisnaStevilka;
    }

    public void setVpisnaStevilka(Integer vpisnaStevilka) {
        this.vpisnaStevilka = vpisnaStevilka;
    }

    public List<Vrstica> getVrstica() {
        return vrstica;
    }

    public void setVrstica(List<Vrstica> vrstica) {
        this.vrstica = vrstica;
    }
}

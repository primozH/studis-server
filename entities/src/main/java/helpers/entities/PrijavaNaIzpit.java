package helpers.entities;

import izpit.PrijavaRok;
import vloge.Ucitelj;

public class PrijavaNaIzpit {
    private Integer koncnaOcena;
    private Integer ocenaPisno;

    private Ucitelj izprasevalec;

    private PrijavaRok prijavaRok;

    public Integer getKoncnaOcena() {
        return koncnaOcena;
    }

    public void setKoncnaOcena(Integer koncnaOcena) {
        this.koncnaOcena = koncnaOcena;
    }

    public Integer getOcenaPisno() {
        return ocenaPisno;
    }

    public void setOcenaPisno(Integer ocenaPisno) {
        this.ocenaPisno = ocenaPisno;
    }

    public Ucitelj getIzprasevalec() {
        return izprasevalec;
    }

    public void setIzprasevalec(Ucitelj izprasevalec) {
        this.izprasevalec = izprasevalec;
    }

    public PrijavaRok getPrijavaRok() {
        return prijavaRok;
    }

    public void setPrijavaRok(PrijavaRok prijavaRok) {
        this.prijavaRok = prijavaRok;
    }
}

package helpers.entities;

import izpit.PrijavaRok;

public class PrijavaNaIzpit {
    private Integer koncnaOcena;
    private Integer ocenaPisno;

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

    public PrijavaRok getPrijavaRok() {
        return prijavaRok;
    }

    public void setPrijavaRok(PrijavaRok prijavaRok) {
        this.prijavaRok = prijavaRok;
    }
}

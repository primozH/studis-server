package vpis;

import sifranti.StudijskiProgram;
import vloge.Uporabnik;

import javax.persistence.*;

@Entity
@Table(name = "kandidat")
public class Kandidat extends Uporabnik {

    @Column(name = "vpisna_stevilka")
    private Integer vpisnaStevilka;

    @ManyToOne
    @JoinColumn(name = "studijski_program")
    private StudijskiProgram studijskiProgram;

    @Column(name = "izkoriscen")
    private boolean izkoriscen;

    public Integer getVpisnaStevilka() {
        return vpisnaStevilka;
    }

    public void setVpisnaStevilka(Integer vpisnaStevilka) {
        this.vpisnaStevilka = vpisnaStevilka;
    }

    public StudijskiProgram getStudijskiProgram() {
        return studijskiProgram;
    }

    public void setStudijskiProgram(StudijskiProgram studijskiProgram) {
        this.studijskiProgram = studijskiProgram;
    }

    public boolean isIzkoriscen() {
        return izkoriscen;
    }

    public void setIzkoriscen(boolean izkoriscen) {
        this.izkoriscen = izkoriscen;
    }
}

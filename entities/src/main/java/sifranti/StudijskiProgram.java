package sifranti;

import vpis.Stopnja;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "studijski_program")
public class StudijskiProgram {

    @Id
    @Column(name = "sifra_evs")
    private Integer sifraEVS;
    @Column(name = "sifra")
    private String sifra;
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "stopnja")
    private Stopnja stopnja;
    @Column(name = "stevilo_semestrov")
    private Integer stSemestrov;

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Stopnja getStopnja() {
        return stopnja;
    }

    public void setStopnja(Stopnja stopnja) {
        this.stopnja = stopnja;
    }

    public Integer getStSemestrov() {
        return stSemestrov;
    }

    public void setStSemestrov(Integer stSemestrov) {
        this.stSemestrov = stSemestrov;
    }

    public Integer getSifraEVS() {
        return sifraEVS;
    }

    public void setSifraEVS(Integer sigraEVS) {
        this.sifraEVS = sigraEVS;
    }
}

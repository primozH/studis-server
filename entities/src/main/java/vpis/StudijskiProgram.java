package vpis;

public class StudijskiProgram {
    private String sifra;
    private String naziv;
    private Stopnja stopnja;
    private Integer stSemestrov;
    private Integer sigraEVS;

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

    public Integer getSigraEVS() {
        return sigraEVS;
    }

    public void setSigraEVS(Integer sigraEVS) {
        this.sigraEVS = sigraEVS;
    }
}

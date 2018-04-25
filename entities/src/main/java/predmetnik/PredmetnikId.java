package predmetnik;

import java.io.Serializable;

public class PredmetnikId implements Serializable {

    private Integer delPredmetnika;
    private Integer predmet;
    private Integer studijskoLeto;
    private Integer letnik;
    private Integer studijskiProgram;

    public Integer getDelPredmetnika() {
        return delPredmetnika;
    }

    public void setDelPredmetnika(Integer delPredmetnika) {
        this.delPredmetnika = delPredmetnika;
    }

    public Integer getPredmet() {
        return predmet;
    }

    public void setPredmet(Integer predmet) {
        this.predmet = predmet;
    }

    public Integer getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(Integer studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
    }

    public Integer getLetnik() {
        return letnik;
    }

    public void setLetnik(Integer letnik) {
        this.letnik = letnik;
    }

    public Integer getStudijskiProgram() {
        return studijskiProgram;
    }

    public void setStudijskiProgram(Integer studijskiProgram) {
        this.studijskiProgram = studijskiProgram;
    }
}

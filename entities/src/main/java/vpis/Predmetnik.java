package vpis;

import sifranti.*;
import vloge.Student;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "predmetnik")
public class Predmetnik {

    @Id private Integer delPredmetnika;
    @Id private Integer predmet;
    @Id private Integer letnik;
    @Id private Integer studijskiProgram;
    @Id private Integer studijskoLeto;

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

    public Integer getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(Integer studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
    }
}

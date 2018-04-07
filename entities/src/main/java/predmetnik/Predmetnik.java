package predmetnik;

import sifranti.*;
import vloge.Student;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "predmetnik")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.predmetnik.Predmetnik.predmetiZaProgram", query = "SELECT p.predmet FROM Predmetnik p" +
                " WHERE p.studijskoLeto = :studijskoLeto AND p.letnik = :letnik AND p.studijskiProgram = :studijskiProgram AND " +
                "p.delPredmetnika = :delPredmetnika")
})
@IdClass(PredmetnikId.class)
public class Predmetnik {

    @Id
    @ManyToOne
    @JoinColumn(name = "del_predmetnika")
    private DelPredmetnika delPredmetnika;
    @Id
    @ManyToOne
    @JoinColumn(name = "predmet")
    private Predmet predmet;
    @Id
    @ManyToOne
    @JoinColumn(name = "letnik")
    private Letnik letnik;
    @Id
    @ManyToOne
    @JoinColumn(name = "studijski_program")
    private StudijskiProgram studijskiProgram;
    @Id
    @ManyToOne
    @JoinColumn(name = "studijsko_leto")
    private StudijskoLeto studijskoLeto;

    public DelPredmetnika getDelPredmetnika() {
        return delPredmetnika;
    }

    public void setDelPredmetnika(DelPredmetnika delPredmetnika) {
        this.delPredmetnika = delPredmetnika;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Letnik getLetnik() {
        return letnik;
    }

    public void setLetnik(Letnik letnik) {
        this.letnik = letnik;
    }

    public StudijskiProgram getStudijskiProgram() {
        return studijskiProgram;
    }

    public void setStudijskiProgram(StudijskiProgram studijskiProgram) {
        this.studijskiProgram = studijskiProgram;
    }

    public StudijskoLeto getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(StudijskoLeto studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
    }
}

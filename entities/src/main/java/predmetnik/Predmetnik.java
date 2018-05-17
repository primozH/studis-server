package predmetnik;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import sifranti.DelPredmetnika;
import sifranti.Letnik;
import sifranti.Predmet;
import sifranti.StudijskiProgram;
import sifranti.StudijskoLeto;

@Entity
@Table(name = "predmetnik")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.predmetnik.Predmetnik.predmetiZaProgram", query = "SELECT p.predmet FROM Predmetnik p" +
                " WHERE p.studijskoLeto = :studijskoLeto AND p.letnik = :letnik AND p.studijskiProgram = :studijskiProgram AND " +
                "p.delPredmetnika = :delPredmetnika"),
        @NamedQuery(name = "entitete.predmetnik.Predmetnik.predmetnikZaProgram", query = "SELECT p FROM Predmetnik p " +
                " WHERE p.studijskoLeto = :studijskoLeto AND p.letnik = :letnik AND p.studijskiProgram = :studijskiProgram AND " +
                "p.delPredmetnika = :delPredmetnika"),
        @NamedQuery(name = "entitete.predmetnik.Predmetnik.moduli", query = "SELECT p FROM Predmetnik p " +
                "WHERE p.studijskiProgram = :studijskiProgram AND p.letnik = :letnik AND p.studijskoLeto = :studijskoLeto AND " +
                "p.delPredmetnika.tip LIKE 'modul%' ORDER BY p.modul"),
        @NamedQuery(name = "entitete.predmetnik.Predmetnik.splosni", query = "SELECT p FROM Predmetnik p " +
                "WHERE p.studijskiProgram = :studijskiProgram AND p.letnik = :letnik AND p.studijskoLeto = :studijskoLeto AND " +
                "p.delPredmetnika.tip LIKE 'splošni%'"),
        @NamedQuery(name = "entitete.predmetnik.Predmetnik.strokovni", query = "SELECT p FROM Predmetnik p " +
                "WHERE p.studijskiProgram = :studijskiProgram AND p.letnik = :letnik AND p.studijskoLeto = :studijskoLeto AND " +
                "p.delPredmetnika.tip LIKE 'strokovni%'"),
        @NamedQuery(name = "entitete.predmetnik.Predmetnik.obvezniPredmetnik", query = "SELECT p FROM Predmetnik p " +
                "WHERE p.studijskiProgram = :studijskiProgram AND p.letnik = :letnik AND p.studijskoLeto = :studijskoLeto AND " +
                "p.delPredmetnika.tip LIKE 'obvezni%'")
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

    @Enumerated
    @Column(name = "modul")
    private Modul modul;

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

    public Modul getModul() {
        return modul;
    }

    public void setModul(Modul modul) {
        this.modul = modul;
    }
}

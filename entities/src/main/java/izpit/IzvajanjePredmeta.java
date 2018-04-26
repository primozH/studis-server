package izpit;

import sifranti.Predmet;
import sifranti.StudijskoLeto;
import vloge.Ucitelj;

import javax.persistence.*;


@Entity
@Table(name = "predmet_izvajanje")
@IdClass(IzvajanjePredmetaId.class)
public class IzvajanjePredmeta {

    @Id
    @ManyToOne
    @JoinColumn(name = "predmet")
    private Predmet predmet;
    @Id
    @ManyToOne
    @JoinColumn(name = "studijsko_leto")
    private StudijskoLeto studijskoLeto;

    @ManyToOne
    @JoinColumn(name = "nosilec1")
    private Ucitelj nosilec1;
    @ManyToOne
    @JoinColumn(name = "nosilec2")
    private Ucitelj nosilec2;
    @ManyToOne
    @JoinColumn(name = "nosilec3")
    private Ucitelj nosilec3;

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public StudijskoLeto getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(StudijskoLeto studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
    }

    public Ucitelj getNosilec1() {
        return nosilec1;
    }

    public void setNosilec1(Ucitelj nosilec1) {
        this.nosilec1 = nosilec1;
    }

    public Ucitelj getNosilec2() {
        return nosilec2;
    }

    public void setNosilec2(Ucitelj nosilec2) {
        this.nosilec2 = nosilec2;
    }

    public Ucitelj getNosilec3() {
        return nosilec3;
    }

    public void setNosilec3(Ucitelj nosilec3) {
        this.nosilec3 = nosilec3;
    }
}



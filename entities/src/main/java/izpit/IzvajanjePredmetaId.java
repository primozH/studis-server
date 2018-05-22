package izpit;

import java.io.Serializable;

public class IzvajanjePredmetaId implements Serializable {
    private Integer predmet;
    private Integer studijskoLeto;

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
}
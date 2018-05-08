package izpit;

import java.io.Serializable;

public class OdjavaIzpitId implements Serializable {
    private PrijavaRokId prijavaRok;

    public PrijavaRokId getPrijavaRok() {
        return prijavaRok;
    }

    public void setPrijavaRok(PrijavaRokId prijavaRok) {
        this.prijavaRok = prijavaRok;
    }
}

package izpit;

import java.io.Serializable;

public class OdjavaIzpitId implements Serializable {
    private PrijavaIzpitId prijavaIzpit;

    public PrijavaIzpitId getPrijavaIzpit() {
        return prijavaIzpit;
    }

    public void setPrijavaIzpit(PrijavaIzpitId prijavaIzpit) {
        this.prijavaIzpit = prijavaIzpit;
    }
}

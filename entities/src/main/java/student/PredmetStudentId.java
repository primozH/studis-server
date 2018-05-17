package student;

import vpis.Vpis;
import vpis.VpisId;

import java.io.Serializable;

public class PredmetStudentId implements Serializable {

    private Integer predmet;
    private VpisId vpis;

    public Integer getPredmet() {
        return predmet;
    }

    public void setPredmet(Integer predmet) {
        this.predmet = predmet;
    }

    public VpisId getVpis() {
        return vpis;
    }

    public void setVpis(VpisId vpis) {
        this.vpis = vpis;
    }
}

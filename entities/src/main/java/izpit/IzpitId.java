package izpit;

import java.io.Serializable;

public class IzpitId implements Serializable {

    private Integer student;
    private Integer predmet;

    public Integer getStudent() {
        return student;
    }

    public void setStudent(Integer student) {
        this.student = student;
    }

    public Integer getPredmet() {
        return predmet;
    }

    public void setPredmet(Integer predmet) {
        this.predmet = predmet;
    }
}

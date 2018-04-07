package vpis;

import java.io.Serializable;

public class VpisId implements Serializable {

    private Integer student;
    private Integer studijskoLeto;

    public Integer getStudent() {
        return student;
    }

    public void setStudent(Integer studentId) {
        this.student = studentId;
    }

    public Integer getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(Integer studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
    }
}

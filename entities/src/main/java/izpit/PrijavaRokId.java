package izpit;

import student.PredmetStudentId;
import vloge.Student;

import java.io.Serializable;

public class PrijavaRokId implements Serializable {
    private IzpitniRokId rok;
    private Integer student;

    public IzpitniRokId getRok() {
        return rok;
    }

    public void setRok(IzpitniRokId rok) {
        this.rok = rok;
    }

    public Integer getStudent() {
        return student;
    }

    public void setStudent(Integer student) {
        this.student = student;
    }
}
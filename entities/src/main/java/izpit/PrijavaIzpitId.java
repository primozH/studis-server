package izpit;

import student.PredmetStudentId;

import java.io.Serializable;

public class PrijavaIzpitId implements Serializable {
    private IzpitniRokId rok;
    private PredmetStudentId predmetStudent;

    public IzpitniRokId getRok() {
        return rok;
    }

    public void setRok(IzpitniRokId rok) {
        this.rok = rok;
    }

    public PredmetStudentId getPredmetStudent() {
        return predmetStudent;
    }

    public void setPredmetStudent(PredmetStudentId predmetStudent) {
        this.predmetStudent = predmetStudent;
    }
}
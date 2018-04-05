package student;

import sifranti.VrstaVpisa;
import vloge.Student;

import java.io.Serializable;
import java.util.Objects;

public class ZetonId implements Serializable {
    private VrstaVpisa vrstaVpisa;
    private Student student;

    public VrstaVpisa getVrstaVpisa() {
        return vrstaVpisa;
    }

    public void setVrstaVpisa(VrstaVpisa vrstaVpisa) {
        this.vrstaVpisa = vrstaVpisa;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof ZetonId) &&
                this.student == ((ZetonId) obj).getStudent() &&
                this.vrstaVpisa ==((ZetonId) obj).getVrstaVpisa());
    }

    @Override
    public int hashCode() {

        return Objects.hash(vrstaVpisa, student);
    }
}

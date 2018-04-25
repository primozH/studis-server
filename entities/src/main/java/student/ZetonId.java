package student;

import java.io.Serializable;
import java.util.Objects;

public class ZetonId implements Serializable {
    private Integer vrstaVpisa;
    private Integer student;

    public ZetonId(Integer student, Integer vrstaVpisa) {
        this.student = student;
        this.vrstaVpisa = vrstaVpisa;
    }

    public Integer getVrstaVpisa() {
        return vrstaVpisa;
    }

    public void setVrstaVpisa(Integer vrstaVpisa) {
        this.vrstaVpisa = vrstaVpisa;
    }

    public Integer getStudent() {
        return student;
    }

    public void setStudent(Integer student) {
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

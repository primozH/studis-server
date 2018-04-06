package student;

import sifranti.Predmet;
import sifranti.StudijskoLeto;
import vloge.Student;

import javax.persistence.*;

@Entity
@Table(name = "predmet_student")
public class PredmetStudent {

    @Id
    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "predmet")
    private Predmet predmet;

    @Id
    @ManyToOne
    @JoinColumn(name = "studijsko_leto")
    private StudijskoLeto studijskoLeto;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public StudijskoLeto getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(StudijskoLeto studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
    }
}

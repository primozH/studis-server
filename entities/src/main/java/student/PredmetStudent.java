package student;

import sifranti.Predmet;
import sifranti.StudijskoLeto;
import vloge.Student;
import vpis.Vpis;

import javax.persistence.*;

@Entity
@Table(name = "predmet_student")
@IdClass(PredmetStudentId.class)
public class PredmetStudent {

    @Id
    @ManyToOne
    @JoinColumn(name = "predmet")
    private Predmet predmet;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "student", referencedColumnName = "student"),
            @JoinColumn(name = "studijsko_leto", referencedColumnName = "studijsko_leto")
    })
    private Vpis vpis;

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Vpis getVpis() {
        return vpis;
    }

    public void setVpis(Vpis vpis) {
        this.vpis = vpis;
    }
}

package student;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import sifranti.Predmet;
import vpis.Vpis;

@Entity
@Table(name = "predmet_student")
@IdClass(PredmetStudentId.class)
@NamedQueries(value = {
        @NamedQuery(name = "entitete.student.PredmetStudent.vrniSeznamStudentovZaPredmetInLeto",
                query = "SELECT p.vpis FROM PredmetStudent p WHERE p.predmet.sifra = :sifraPredmeta AND p.vpis.studijskoLeto.id = :studijskoLeto")
})
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

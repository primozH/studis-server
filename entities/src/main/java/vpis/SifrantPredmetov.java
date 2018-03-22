package vpis;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "predmet")
public class SifrantPredmetov {

    @Id
    private Integer sifra;
    private String naziv;
    private Semester semester;
    private Integer ECTS;

    private PredmetTip tipPredmeta;
}

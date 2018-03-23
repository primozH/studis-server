package sifranti;

import vpis.Semester;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "predmet")
public class Predmet {

    @Id
    private Integer sifra;
    private String naziv;
    private Semester semester;
    private Integer ECTS;

}

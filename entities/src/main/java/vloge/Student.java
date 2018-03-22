package vloge;

import naslov.Drzava;
import naslov.Naslov;
import naslov.Obcina;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "student")
public class Student {

    @Id
    private Integer vpisnaStevilka;
    private String ime;
    private String priimek;
    private Date datumRojstva;

    private Obcina obcinaRojstva;
    private Drzava drzavaRojstva;
    private Naslov stalnoBivalisce;
    private Naslov zacasnoBivallisce;

    private String telefonskaStevilka;
    private String elektronskaPosta;
}

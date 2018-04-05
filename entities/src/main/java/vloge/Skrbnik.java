package vloge;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "skrbnik")
public class Skrbnik extends Uporabnik {

    public Skrbnik() {
        super();
    }

    public Skrbnik(String email, String geslo, String uporabniskoIme,
                   String ime, String priimek) {
        super(email, geslo, uporabniskoIme);
        this.setIme(ime);
        this.setPriimek(priimek);
    }

}

package vloge;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "referent")
public class Referent extends Uporabnik {

    public Referent() {
    }

    public Referent(String email, String geslo,
                   String ime, String priimek) {
        super(email, geslo);
        this.setIme(ime);
        this.setPriimek(priimek);
    }
}

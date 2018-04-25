package vloge;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ucitelj")
public class Ucitelj extends Uporabnik {

    public Ucitelj() {
    }

    public Ucitelj(String email, String geslo, String uporabniskoIme,
                   String ime, String priimek) {
        super(email, geslo, uporabniskoIme);
        this.setIme(ime);
        this.setPriimek(priimek);
    }
}

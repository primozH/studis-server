package vloge;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ucitelj")
public class Ucitelj extends Uporabnik {

    public Ucitelj() {
    }

    public Ucitelj(String email, String geslo) {
        super(email, geslo);
    }
}

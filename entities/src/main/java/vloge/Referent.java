package vloge;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "referent")
public class Referent extends Uporabnik {

    public Referent() {
    }

}

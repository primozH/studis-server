package vloge;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "skrbnik")
public class Skrbnik extends Uporabnik {

    public Skrbnik() {
        super();
    }

}

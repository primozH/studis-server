package vloge;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({ @NamedQuery(name = "entities.vloge.Oseba.vrniOsebo", query = "SELECT o FROM oseba o WHERE o.elektronska_posta = :elektronskaPosta") })
//@NamedQuery(name = "entities.vloge.Oseba.preveriGeslo", query = "SELECT o FROM oseba o WHERE o.geslo = :geslo")
public abstract class Oseba {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "elektronska_posta")
    private String elektronskaPosta;
    private String geslo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getElektronskaPosta() {
        return elektronskaPosta;
    }

    public void setElektronskaPosta(String elektronskaPosta) {
        this.elektronskaPosta = elektronskaPosta;
    }

    public String getGeslo() {
        return geslo;
    }

    public void setGeslo(String geslo) {
        this.geslo = geslo;
    }
}

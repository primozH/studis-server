package prijava;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Uporablja se samo pri prijavi v sistem.
 * S pomocjo para elektronska_posta in geslo poisce v bazi
 * ustreznega uporabnika in mu dovoli oz. zavrne vstop v sistem.
 */
@Entity(name = "prijava")
public class Prijava {

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

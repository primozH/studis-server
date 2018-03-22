package prijava;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name = "uporabnik")
@NamedQueries({ @NamedQuery(name = "entitete.Uporabnik.vrniUporabnika", query = "SELECT u FROM uporabnik u WHERE u.uporabnisko_ime = :uporabniskoIme") })

//@NamedQuery(name = "entitete.Uporabnik.preveriGeslo", query = "SELECT u FROM uporabnik u WHERE u.geslo = :geslo")
public class Uporabnik {

    @Id
    private int id;
    @Column(name = "uporabnisko_ime")
    private String uporabniskoIme;
    private String geslo;

    public String getUporabniskoIme() {
        return uporabniskoIme;
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme;
    }

    public String getGeslo() {
        return geslo;
    }

    public void setGeslo(String geslo) {
        this.geslo = geslo;
    }
}

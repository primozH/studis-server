package vloge;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "uporabnik")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries( value = {
        @NamedQuery(name = "entities.vloge.Uporabnik.prijava", query = "SELECT u FROM Uporabnik u WHERE u.email = :email")
})
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uporabnik") private Integer id;
    @Column(name = "email", nullable = false) private String email;
    @Column(name = "geslo", nullable = false) private String geslo;
    @Column(name = "emso") private String emso;

    @Column(name = "davcna_stevilka") private String davcnaStevilka;
    @Column(name = "zadnja_prijava") private Date zadnjaPrijava;
    @Column(name = "spremenjeno", columnDefinition = "DATETIME NULL ON UPDATE CURRENT_TIMESTAMP")
    private Date spremenjeno;
    @Column(name = "ustvarjeno", insertable = false, updatable = false,
    columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private Date ustvarjeno;

    public Uporabnik() { }

    public Uporabnik(String email, String geslo) {
        this.email = email;
        this.geslo = geslo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGeslo(String geslo) {
        /* TODO make a hash from password then store it to database */
    }

    public void setEmso(String emso) {
        // TODO verify emso, then store it
    }

    public void setDavcnaStevilka(String davcnaStevilka) {
        // TODO Verify???
    }

    public void setZadnjaPrijava() {
        this.zadnjaPrijava = new Date();
    }

    public void setSpremenjeno() {
        this.spremenjeno = new Date();
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getGeslo() {
        return geslo;
    }

    public String getEmso() {
        return emso;
    }

    public String getDavcnaStevilka() {
        return davcnaStevilka;
    }

    public Date getZadnjaPrijava() {
        return zadnjaPrijava;
    }

    public Date getSpremenjeno() {
        return spremenjeno;
    }

    public Date getUstvarjeno() {
        return ustvarjeno;
    }
}

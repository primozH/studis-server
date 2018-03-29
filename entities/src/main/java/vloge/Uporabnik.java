package vloge;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import helpers.PasswordAuthentication;
import helpers.PasswordAuthenticationImpl;
import helpers.adapters.LocalDateTimeAdapter;

@Entity
@Table(name = "uporabnik")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tip")
@NamedQueries( value = {
        @NamedQuery(name = "entitete.vloge.Uporabnik.prijava", query = "SELECT u FROM Uporabnik u WHERE u.email = :email")
})
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uporabnik") private Integer id;
    @Column(name = "email", nullable = false) private String email;

    @Column(name = "ime") private String ime;
    @Column(name = "priimek") private String priimek;

    @XmlTransient
    @Column(name = "geslo", nullable = false)
    private String geslo;

    @Column(name = "emso") private String emso;
    @Column(name = "tip", updatable = false, insertable = false)
    private String tip;

    @Column(name = "davcna_stevilka")
    private String davcnaStevilka;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Column(name = "zadnja_prijava")
    private LocalDateTime zadnjaPrijava;

    @XmlTransient
    @Column(name = "spremenjeno", columnDefinition = "DATETIME NULL ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime spremenjeno;

    @XmlTransient
    @Column(name = "ustvarjeno", insertable = false, updatable = false,
    columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ustvarjeno;

    public Uporabnik() { }

    public Uporabnik(String email, String geslo) {
        this.email = email;
        setGeslo(geslo);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmso(String emso) {
        // TODO verify emso, then store it
    }

    public void setDavcnaStevilka(String davcnaStevilka) {
        // TODO Verify???
    }

    public void setZadnjaPrijava() {
        this.zadnjaPrijava = LocalDateTime.now();
    }

    public void setSpremenjeno() {
        this.spremenjeno = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getEmso() {
        return emso;
    }

    public String getGeslo() {
        return geslo;
    }

    public String getDavcnaStevilka() {
        return davcnaStevilka;
    }

    public LocalDateTime getZadnjaPrijava() {
        return zadnjaPrijava;
    }

    public LocalDateTime getSpremenjeno() {
        return spremenjeno;
    }

    public LocalDateTime getUstvarjeno() {
        return ustvarjeno;
    }

    public String getTip() { return this.getClass().getSimpleName(); }

    /**
     * Preveri, ce se geslo ujema z uporabnikovim geslom iz baze.
     *
     * @param geslo
     * @return
     */
    public boolean primerjajGeslo(String geslo) {
        PasswordAuthentication passwordAuthentication = new PasswordAuthenticationImpl();
        return passwordAuthentication.authenticate(geslo.toCharArray(), this.geslo);
    }

    /**
     * Naredi hash iz gesla ter ga shrani v bazo.
     *
     * @param geslo
     */
    public void setGeslo(String geslo) {
        PasswordAuthentication passwordAuthentication = new PasswordAuthenticationImpl();
        geslo = passwordAuthentication.hash(geslo.toCharArray());
        this.geslo = geslo;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }
}

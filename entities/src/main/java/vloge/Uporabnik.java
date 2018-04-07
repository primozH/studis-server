package vloge;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import helpers.PasswordAuthentication;
import helpers.PasswordAuthenticationImpl;
import helpers.adapters.LocalDateAdapter;
import helpers.adapters.LocalDateTimeAdapter;

@Entity
@Table(name = "uporabnik")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tip")
@NamedQueries( value = {
        @NamedQuery(name = "entitete.vloge.Uporabnik.prijava", query = "SELECT u FROM Uporabnik u WHERE u.uporabniskoIme = :uporabniskoIme"),
        @NamedQuery(name = "entitete.vloge.Uporabnik.pozabljeno.geslo", query = "SELECT u FROM Uporabnik u WHERE u.email = :email")
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Uporabnik {

    private static final Logger logger = Logger.getLogger(Uporabnik.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uporabnik")
    @XmlElement
    @XmlID
    private Integer id;
    @Column(name = "email", nullable = false) private String email;

    @Column(name = "ime") private String ime;
    @Column(name = "priimek") private String priimek;

    @Column(name = "uporabnisko_ime", nullable = false)
    private String uporabniskoIme;

    @XmlTransient
    @Column(name = "geslo", nullable = false)
    private String geslo;

    @Column(name = "emso")
    private String emso;
    @Column(name = "tip", updatable = false, insertable = false)
    private String tip;

    @Column(name = "davcna_stevilka")
    private String davcnaStevilka;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @Column(name = "datum_rojstva")
    private LocalDate datumRojstva;

    @Column(name = "spol")
    @Enumerated
    private Spol spol;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Column(name = "zadnja_prijava")
    private LocalDateTime zadnjaPrijava;

    @XmlTransient
    @Column(name = "spremenjeno")
    private LocalDateTime spremenjeno;

    @XmlTransient
    @Column(name = "ustvarjeno", updatable = false)
    private LocalDateTime ustvarjeno;

    @PrePersist
    public void timestamp() {
        if (ustvarjeno == null){
            ustvarjeno = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        spremenjeno = LocalDateTime.now();
    }

    public Uporabnik() { }

    public Uporabnik(String email, String geslo, String uporabniskoIme) {
        this.email = email;
        this.uporabniskoIme = uporabniskoIme;
        setGeslo(geslo);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmso(String emso) throws Exception {
        if (datumRojstva == null) {
            throw new Exception("Birth date not set");
        }
        emso = emso.trim();
        if (emso.length() < 13) {
            throw new NumberFormatException("Invalid EMSO");
        }

        Integer day = Integer.parseInt(emso.substring(0, 2));
        Integer month = Integer.parseInt(emso.substring(2, 4));
        Integer year = Integer.parseInt(emso.substring(4, 7));
        Integer register = Integer.parseInt(emso.substring(7, 9));
        Integer seq = Integer.parseInt(emso.substring(9, 12));
        Integer control = Integer.parseInt(emso.substring(12));

        if (day != datumRojstva.getDayOfMonth()) {
            throw new Exception("Invalid day in EMSO");
        }
        if (month != datumRojstva.getMonthValue()) {
            throw new Exception("Invalid month in EMSO");
        }
        if (year != datumRojstva.getYear() % 1000) {
            throw new Exception("Invalid year in EMSO");
        }
        if (register < 10) {
            throw new Exception("Invalid register in EMSO");
        }
        if (spol == Spol.ZENSKI && seq < 500 || spol == Spol.MOSKI && seq > 500) {
            throw new Exception("Invalid sequence number in EMSO");
        }

        Integer verification =
                day / 10 * 7 +
                day % 10 * 6 +
                + month / 10 * 5
                + month % 10 * 4
                + year / 100 * 3
                + (year / 10) % 10 * 2
                + year % 10 * 7
                + register / 10 * 6
                + register % 10 * 5
                + seq / 100 * 4
                + (seq / 10) % 10 * 3
                + seq % 10 * 2;

        verification %= 11;

        Integer controlNum = 11 - verification;

        if (!controlNum.equals(control)) {
            throw new Exception("Invalid control number in EMSO");
        }

        this.emso = emso;
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

    public LocalDate getDatumRojstva() {
        return datumRojstva;
    }

    public void setDatumRojstva(LocalDate datumRojstva) {
        this.datumRojstva = datumRojstva;
    }

    public Spol getSpol() {
        return spol;
    }

    public void setSpol(Spol spol) {
        this.spol = spol;
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
        Pattern p = Pattern.compile(".*[1-9].*");
        if (ime.matches(p.toString()))
            throw new IllegalArgumentException("Neveljavno ime");

        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        Pattern p = Pattern.compile(".*[1-9].*");
        if (ime.matches(p.toString()))
            throw new IllegalArgumentException("Neveljavnen priimek");

        this.priimek = priimek;
    }

    public String getUporabniskoIme() {
        return uporabniskoIme;
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme;
    }
}

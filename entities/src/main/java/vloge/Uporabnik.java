package vloge;

import java.time.LocalDateTime;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import helpers.adapters.LocalDateTimeAdapter;

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
    @Column(name = "DTYPE") private String tip;

    @Column(name = "davcna_stevilka")
    private String davcnaStevilka;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Column(name = "zadnja_prijava")
    private LocalDateTime zadnjaPrijava;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Column(name = "spremenjeno", columnDefinition = "DATETIME NULL ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime spremenjeno;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Column(name = "ustvarjeno", insertable = false, updatable = false,
    columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ustvarjeno;

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

    public String getGeslo() {
        // Generiraj geslo in ga shrani v bazo
        String randomGeslo = generirajRandomGeslo();
        String hashGesla = hashiranjeGesla(randomGeslo);
        // TODO shrani v bazo
        return geslo;
    }

    public String getEmso() {
        return emso;
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

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    /**
     * Preveri, ce se geslo ujema z uporabnikovim geslom iz baze
     *
     * @param geslo
     * @return
     */
    public boolean primerjajGeslo (String geslo) {
        String hashGesla = hashiranjeGesla(geslo);
        return this.geslo.equals(hashGesla);
    }

    /**
     * Se uporablja pri shranjevanju v bazo in pred
     * primerjanjem gesla iz frontenda z geslom iz baze.
     *
     * @return hashirano geslo
     */
    private String hashiranjeGesla(String geslo) {
        return "";
    }

    private static final String ALFANUMERICNI_ZNAKI = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int DOLZINA_RANDOM_GESLA = 10;

    /**
     * Se uporablja v kolikor uporabnik pozabi geslo
     * oz. ob prvi prijavi v sistem.
     *
     * @return
     */
    private String generirajRandomGeslo() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < DOLZINA_RANDOM_GESLA; i++) {
            stringBuilder.append(ALFANUMERICNI_ZNAKI.charAt(random.nextInt(ALFANUMERICNI_ZNAKI.length())));
        }
        return stringBuilder.toString();
    }
}

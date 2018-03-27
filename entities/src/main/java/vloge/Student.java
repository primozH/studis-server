package vloge;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "id_uporabnik", referencedColumnName = "id_uporabnik")
public class Student extends Uporabnik {

    @Column(name = "vpisna_stevilka") private Integer vpisnaStevilka;
    @Column(name = "ime") private String ime;
    @Column(name = "priimek") private String priimek;
    @Column(name = "datum_rojstva") private Date datumRojstva;

    @Column(name = "tel_stevilka") private String telefonskaStevilka;

    public Student() {
        super();
    }

    public Student(String email, String geslo, Integer vpisnaStevilka, String ime, String priimek, Date datumRojstva, String telefonskaStevilka) {
        super(email, geslo);
        this.vpisnaStevilka = vpisnaStevilka;
        this.ime = ime;
        this.priimek = priimek;
        this.datumRojstva = datumRojstva;
        this.telefonskaStevilka = telefonskaStevilka;
    }

    private void setVpisnaStevilka() {
        // TODO generate enrolment number
    }

    private void setIme(String ime) {
        this.ime = ime;
    }

    private void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public Integer getVpisnaStevilka() {
        return vpisnaStevilka;
    }

    public String getIme() {
        return ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public Date getDatumRojstva() {
        return datumRojstva;
    }

    public void setDatumRojstva(Date datumRojstva) {
        this.datumRojstva = datumRojstva;
    }

    public String getTelefonskaStevilka() {
        return telefonskaStevilka;
    }

    public void setTelefonskaStevilka(String telefonskaStevilka) {
        // TODO verify phone number
        this.telefonskaStevilka = telefonskaStevilka;
    }
}

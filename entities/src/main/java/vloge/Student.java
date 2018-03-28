package vloge;

import helpers.adapters.LocalDateAdapter;
import vpis.Vpis;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "id_uporabnik", referencedColumnName = "id_uporabnik")
public class Student extends Uporabnik {

    @Column(name = "vpisna_stevilka") private Integer vpisnaStevilka;
    @Column(name = "ime") private String ime;
    @Column(name = "priimek") private String priimek;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @Column(name = "datum_rojstva")
    private LocalDate datumRojstva;

    @Column(name = "tel_stevilka") private String telefonskaStevilka;

    @OneToMany(targetEntity = Vpis.class)
    private List<Vpis> vpisi;

    public Student() {
        super();
    }

    public Student(String email, String geslo, Integer vpisnaStevilka, String ime, String priimek, LocalDate datumRojstva, String telefonskaStevilka) {
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

    public LocalDate getDatumRojstva() {
        return datumRojstva;
    }

    public void setDatumRojstva(LocalDate datumRojstva) {
        this.datumRojstva = datumRojstva;
    }

    public String getTelefonskaStevilka() {
        return telefonskaStevilka;
    }

    public void setTelefonskaStevilka(String telefonskaStevilka) {
        // TODO verify phone number
        this.telefonskaStevilka = telefonskaStevilka;
    }

    public List<Vpis> getVpisi() {
        return vpisi;
    }

    public void setVpisi(List<Vpis> vpisi) {
        this.vpisi = vpisi;
    }
}

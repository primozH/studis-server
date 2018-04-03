package vloge;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import helpers.adapters.LocalDateAdapter;
import naslov.Drzava;
import naslov.Obcina;
import vpis.Vpis;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "id_uporabnik", referencedColumnName = "id_uporabnik")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.vloge.Student.vrniStudentaPoUporabniskemImenu", query = "SELECT s FROM Student s WHERE s.uporabnisko_ime = :uporabniskoIme"),
        @NamedQuery(name = "entitete.vloge.Student.vrniStudentaPoVpisniStevilki", query = "SELECT s FROM Student s WHERE s.vpisna_stevilka = :vpisnaStevilka")
})
public class Student extends Uporabnik {

    @Column(name = "vpisna_stevilka") private Integer vpisnaStevilka;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @Column(name = "datum_rojstva")
    private LocalDate datumRojstva;

    @Column(name = "tel_stevilka") private String telefonskaStevilka;

    @Column(name = "uporabnisko_ime")
    private String uporabniskoIme;

    @ManyToOne(targetEntity = Drzava.class)
    @JoinColumn(name = "drzava_stalno")
    private Drzava drzavaStalno;
    @ManyToOne(targetEntity = Obcina.class)
    @JoinColumn(name = "obcina_stalno")
    private Obcina obcinaStalno;
    @Column(name = "naslov_stalno")
    private String naslovStalno;

    @ManyToOne(targetEntity = Drzava.class)
    @JoinColumn(name = "drzava_zacasno")
    private Drzava drzavaZacasno;
    @ManyToOne(targetEntity = Obcina.class)
    @JoinColumn(name = "obcina_zacasno")
    private Obcina obcinaZacasno;
    @Column(name = "naslov_zacasno")
    private String naslovZacasno;

    @Column(name = "drzava_rojstva")
    private String drzavaRojstva;
    @Column(name = "obcina_rojstva")
    private String obcinaRojstva;
    @Column(name = "kraj_rojstva")
    private String krajRojstva;

    @Column(name = "spol")
    private Spol spol;

    @OneToMany(targetEntity = Vpis.class)
    private List<Vpis> vpisi;


    public Student() {
        super();
    }

    public Student(String email, String geslo, Integer vpisnaStevilka, String uporabniskoIme, String ime, String priimek, LocalDate datumRojstva, String telefonskaStevilka) {
        super(email, geslo);
        this.vpisnaStevilka = vpisnaStevilka;
        this.uporabniskoIme = uporabniskoIme;
        this.datumRojstva = datumRojstva;
        this.telefonskaStevilka = telefonskaStevilka;

        this.setIme(ime);
        this.setPriimek(priimek);
    }

    public Integer getVpisnaStevilka() {
        return vpisnaStevilka;
    }

    public void setVpisnaStevilka(Integer vpisnaStevilka) {
        this.vpisnaStevilka = vpisnaStevilka;
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
        this.telefonskaStevilka = telefonskaStevilka;
    }

    public Drzava getDrzavaStalno() {
        return drzavaStalno;
    }

    public void setDrzavaStalno(Drzava drzavaStalno) {
        this.drzavaStalno = drzavaStalno;
    }

    public Obcina getObcinaStalno() {
        return obcinaStalno;
    }

    public void setObcinaStalno(Obcina obcinaStalno) {
        this.obcinaStalno = obcinaStalno;
    }

    public String getNaslovStalno() {
        return naslovStalno;
    }

    public void setNaslovStalno(String naslovStalno) {
        this.naslovStalno = naslovStalno;
    }

    public Drzava getDrzavaZacasno() {
        return drzavaZacasno;
    }

    public void setDrzavaZacasno(Drzava drzavaZacasno) {
        this.drzavaZacasno = drzavaZacasno;
    }

    public Obcina getObcinaZacasno() {
        return obcinaZacasno;
    }

    public void setObcinaZacasno(Obcina obcinaZacasno) {
        this.obcinaZacasno = obcinaZacasno;
    }

    public String getNaslovZacasno() {
        return naslovZacasno;
    }

    public void setNaslovZacasno(String naslovZacasno) {
        this.naslovZacasno = naslovZacasno;
    }

    public String getDrzavaRojstva() {
        return drzavaRojstva;
    }

    public void setDrzavaRojstva(String drzavaRojstva) {
        this.drzavaRojstva = drzavaRojstva;
    }

    public String getObcinaRojstva() {
        return obcinaRojstva;
    }

    public void setObcinaRojstva(String obcinaRojstva) {
        this.obcinaRojstva = obcinaRojstva;
    }

    public String getKrajRojstva() {
        return krajRojstva;
    }

    public void setKrajRojstva(String krajRojstva) {
        this.krajRojstva = krajRojstva;
    }

    public Spol getSpol() {
        return spol;
    }

    public void setSpol(Spol spol) {
        this.spol = spol;
    }

    public List<Vpis> getVpisi() {
        return vpisi;
    }

    public void dodajVpis(Vpis vpis) {
        this.vpisi.add(vpis);
    }

    public String getUporabniskoIme() {
        return uporabniskoIme;
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme;
    }
}

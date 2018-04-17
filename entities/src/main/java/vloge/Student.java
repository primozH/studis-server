package vloge;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import helpers.adapters.DrzavaAdapter;
import helpers.adapters.ObcinaAdapter;
import helpers.adapters.PostaAdapter;
import naslov.Drzava;
import naslov.Obcina;
import naslov.Posta;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import vpis.Vpis;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "id_uporabnik", referencedColumnName = "id_uporabnik")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.vloge.Student.vrniVse", query = "SELECT s FROM Student s"),
        @NamedQuery(name = "entitete.vloge.Student.vrniStudentaPoUporabniskemImenu", query = "SELECT s FROM Student s WHERE s.uporabniskoIme = :uporabniskoIme"),
        @NamedQuery(name = "entitete.vloge.Student.vrniStudentaPoVpisniStevilki", query = "SELECT s FROM Student s WHERE s.vpisnaStevilka = :vpisnaStevilka"),
        @NamedQuery(name = "entitete.vloge.Student.vrniNajvisjoZaporednoVpisnoStevilko", query = "SELECT s FROM Student s WHERE CONCAT(s.vpisnaStevilka, '') LIKE :vpisnaStevilka ORDER BY s.vpisnaStevilka DESC"),
        @NamedQuery(name = "entitete.vloge.Student.isciStudentaPoImenuPriimkuVpisni",
                query = "SELECT s FROM Student s WHERE s.ime LIKE :parameter OR " +
                        "s.priimek LIKE :parameter OR CONCAT(s.vpisnaStevilka, '') LIKE :parameter"),
        @NamedQuery(name = "entitete.vloge.Student.ustvariStudentaIzKandidata", query = "UPDATE Uporabnik u SET u.tip = 'Student' WHERE " +
                "u.id = :id"),
})
@XmlAccessorType(XmlAccessType.FIELD)
public class Student extends Uporabnik {

    @Column(name = "vpisna_stevilka")
    private Integer vpisnaStevilka;
    @Column(name = "tel_stevilka")
    private String telefonskaStevilka;

    @Column(name = "drzava_rojstva")
    private String drzavaRojstva;
    @Column(name = "obcina_rojstva")
    private String obcinaRojstva;
    @Column(name = "kraj_rojstva")
    private String krajRojstva;

    @XmlJavaTypeAdapter(DrzavaAdapter.class)
    @ManyToOne
    @JoinColumn(name = "drzava_stalno")
    private Drzava drzavaStalno;
    @XmlJavaTypeAdapter(ObcinaAdapter.class)
    @ManyToOne
    @JoinColumn(name = "obcina_stalno")
    private Obcina obcinaStalno;
    @XmlJavaTypeAdapter(PostaAdapter.class)
    @ManyToOne
    @JoinColumn(name = "posta_stalno")
    private Posta postaStalno;
    @Column(name = "naslov_stalno")
    private String naslovStalno;

    @XmlJavaTypeAdapter(DrzavaAdapter.class)
    @ManyToOne
    @JoinColumn(name = "drzava_zacasno")
    private Drzava drzavaZacasno;
    @XmlJavaTypeAdapter(ObcinaAdapter.class)
    @ManyToOne
    @JoinColumn(name = "obcina_zacasno")
    private Obcina obcinaZacasno;
    @XmlJavaTypeAdapter(PostaAdapter.class)
    @ManyToOne
    @JoinColumn(name = "posta_zacasno")
    private Posta postaZacasno;
    @Column(name = "naslov_zacasno")
    private String naslovZacasno;

    @OneToMany(mappedBy = "student")
    @XmlTransient
    private List<Vpis> vpisi = new ArrayList<>();

    @Column(name = "privzeti_naslov")
    private String naslovZaPosiljanjePoste;


    public Student() {
        super();
    }

    public Student(String email, String geslo, Integer vpisnaStevilka, String uporabniskoIme, String ime, String priimek) {
        super(email, geslo, uporabniskoIme);
        this.vpisnaStevilka = vpisnaStevilka;

        this.setIme(ime);
        this.setPriimek(priimek);
    }

    public Integer getVpisnaStevilka() {
        return vpisnaStevilka;
    }

    public void setVpisnaStevilka(Integer vpisnaStevilka) {
        this.vpisnaStevilka = vpisnaStevilka;
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

    public List<Vpis> getVpisi() {
        return vpisi;
    }

    public void dodajVpis(Vpis vpis) {
        this.vpisi.add(vpis);
    }

    public Posta getPostaStalno() {
        return postaStalno;
    }

    public void setPostaStalno(Posta postaStalno) {
        this.postaStalno = postaStalno;
    }

    public Posta getPostaZacasno() {
        return postaZacasno;
    }

    public void setPostaZacasno(Posta postaZacasno) {
        this.postaZacasno = postaZacasno;
    }

    public String getNaslovZaPosiljanjePoste() {
        return naslovZaPosiljanjePoste;
    }

    public void setNaslovZaPosiljanjePoste(String naslovZaPosiljanjePoste) {
        this.naslovZaPosiljanjePoste = naslovZaPosiljanjePoste;
    }
}

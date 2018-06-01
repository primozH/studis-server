package vloge;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.eclipse.persistence.tools.schemaframework.ForeignKeyConstraint;
import sifranti.StudijskiProgram;
import vloge.Uporabnik;

@Entity
@Table(name = "kandidat")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.vloge.Kandidat.vrniNajvisjoZaporednoVpisnoStevilko", query = "SELECT k FROM Kandidat k WHERE CONCAT(k.vpisnaStevilka, '') LIKE :vpisnaStevilka ORDER BY k.vpisnaStevilka DESC"),
        @NamedQuery(name = "entitete.vloge.Kandidat.vrniKandidate", query = "SELECT k FROM Kandidat k"),
        @NamedQuery(name = "entitete.vloge.Kandidat.prijava", query = "SELECT k FROM Kandidat k WHERE k.email = :email")
})
@XmlAccessorType(XmlAccessType.FIELD)
public class Kandidat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ime") private String ime;
    @Column(name = "priimek") private String priimek;

    @Column(name = "uporabnisko_ime", nullable = false)
    private String uporabniskoIme;

    @Column(name = "email") private String email;

    @Column(name = "vpisna_stevilka")
    private Integer vpisnaStevilka;

    @ManyToOne
    @JoinColumn(name = "studijski_program", referencedColumnName = "sifra_evs")
    private StudijskiProgram studijskiProgram;

    @Column(name = "geslo_plain")
    private String gesloPlain;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUporabniskoIme() {
        return uporabniskoIme;
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getVpisnaStevilka() {
        return vpisnaStevilka;
    }

    public void setVpisnaStevilka(Integer vpisnaStevilka) {
        this.vpisnaStevilka = vpisnaStevilka;
    }

    public StudijskiProgram getStudijskiProgram() {
        return studijskiProgram;
    }

    public void setStudijskiProgram(StudijskiProgram studijskiProgram) {
        this.studijskiProgram = studijskiProgram;
    }

    public String getGesloPlain() {
        return gesloPlain;
    }

    public void setGesloPlain(String gesloPlain) {
        this.gesloPlain = gesloPlain;
    }
}

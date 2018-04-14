package vloge;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.eclipse.persistence.tools.schemaframework.ForeignKeyConstraint;
import sifranti.StudijskiProgram;
import vloge.Uporabnik;

@Entity
@Table(name = "kandidat")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.vloge.Kandidat.vrniNajvisjoZaporednoVpisnoStevilko", query = "SELECT k FROM Kandidat k WHERE CONCAT(k.vpisnaStevilka, '') LIKE :vpisnaStevilka ORDER BY k.vpisnaStevilka DESC"),
        @NamedQuery(name = "entitete.vloge.Kandidat.vrniKandidate", query = "SELECT k FROM Kandidat k WHERE k.izkoriscen = false"),
})
@PrimaryKeyJoinColumn(name = "id_uporabnik", referencedColumnName = "id_uporabnik")
@XmlAccessorType(XmlAccessType.FIELD)
@CascadeOnDelete
public class Kandidat extends Uporabnik {

    @Column(name = "vpisna_stevilka")
    private Integer vpisnaStevilka;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "studijski_program", referencedColumnName = "sifra_evs")
    private StudijskiProgram studijskiProgram;

    @Column(name = "izkoriscen")
    private boolean izkoriscen;

    @Column(name = "geslo_plain")
    private String gesloPlain;

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

    public boolean isIzkoriscen() {
        return izkoriscen;
    }

    public void setIzkoriscen(boolean izkoriscen) {
        this.izkoriscen = izkoriscen;
    }

    public String getGesloPlain() {
        return gesloPlain;
    }

    public void setGesloPlain(String gesloPlain) {
        this.gesloPlain = gesloPlain;
    }
}

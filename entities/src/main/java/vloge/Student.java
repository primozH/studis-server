package vloge;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import naslov.Drzava;
import naslov.Naslov;
import naslov.Obcina;

@Entity(name = "student")
@NamedQueries({ @NamedQuery(name = "entitete.Student.vrniStudentaZVpisno", query = "SELECT s FROM student s WHERE s.vpisna_stevilka = :vpisnaStevilka"),
                @NamedQuery(name = "entitete.Student.vrniStudentaZElektronskoPosto", query = "SELECT s FROM student s WHERE s.elektronska_posta = :elektronskaPosta") })
public class Student extends Oseba {

    @Column(name = "vpisna_stevilka")
    private Integer vpisnaStevilka;
    private String ime;
    private String priimek;
    @Column(name = "datum_rojstva")
    private Date datumRojstva;

    @Column(name = "obcina_rojstva")
    private Obcina obcinaRojstva;
    @Column(name = "drzava_rojstva")
    private Drzava drzavaRojstva;
    @Column(name = "stalno_bivalisce")
    private Naslov stalnoBivalisce;
    @Column(name = "zacasno_bivalisce")
    private Naslov zacasnoBivallisce;

    @Column(name = "telefonska_stevilka")
    private String telefonskaStevilka;

    public Integer getVpisnaStevilka() {
        return vpisnaStevilka;
    }

    public void setVpisnaStevilka(Integer vpisnaStevilka) {
        this.vpisnaStevilka = vpisnaStevilka;
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

    public Date getDatumRojstva() {
        return datumRojstva;
    }

    public void setDatumRojstva(Date datumRojstva) {
        this.datumRojstva = datumRojstva;
    }

    public Obcina getObcinaRojstva() {
        return obcinaRojstva;
    }

    public void setObcinaRojstva(Obcina obcinaRojstva) {
        this.obcinaRojstva = obcinaRojstva;
    }

    public Drzava getDrzavaRojstva() {
        return drzavaRojstva;
    }

    public void setDrzavaRojstva(Drzava drzavaRojstva) {
        this.drzavaRojstva = drzavaRojstva;
    }

    public Naslov getStalnoBivalisce() {
        return stalnoBivalisce;
    }

    public void setStalnoBivalisce(Naslov stalnoBivalisce) {
        this.stalnoBivalisce = stalnoBivalisce;
    }

    public Naslov getZacasnoBivallisce() {
        return zacasnoBivallisce;
    }

    public void setZacasnoBivallisce(Naslov zacasnoBivallisce) {
        this.zacasnoBivallisce = zacasnoBivallisce;
    }

    public String getTelefonskaStevilka() {
        return telefonskaStevilka;
    }

    public void setTelefonskaStevilka(String telefonskaStevilka) {
        this.telefonskaStevilka = telefonskaStevilka;
    }
}

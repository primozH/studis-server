package student;

import sifranti.*;
import vloge.Student;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import java.io.Serializable;

@Entity
@Table(name = "zeton")
@NamedQueries( value = {
            @NamedQuery(name = "entitete.vpis.Zeton.vrniVse", query = "SELECT z FROM Zeton z"),
            @NamedQuery(name = "entitete.vpis.Zeton.vrniZetoneZaStudenta", query = "SELECT z FROM Zeton z WHERE z.student.id = :student"),
            @NamedQuery(name = "entitete.vpis.Zeton.nastaviIzkoriscenostZetona", query = "UPDATE Zeton z " +
                    "SET z.izkoriscen = :izkoriscen WHERE z.student = :student")
        }
)
@IdClass(ZetonId.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Zeton {

    @Id
    @ManyToOne
    @JoinColumn(name = "vrsta_vpisa")
    private VrstaVpisa vrstaVpisa;

    @Id
    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "studijski_program")
    private StudijskiProgram studijskiProgram;

    @ManyToOne
    @JoinColumn(name = "letnik")
    private Letnik letnik;

    @ManyToOne
    @JoinColumn(name = "studijsko_leto")
    private StudijskoLeto studijskoLeto;

    @ManyToOne
    @JoinColumn(name = "nacin_studija")
    private NacinStudija nacinStudija;

    @ManyToOne
    @JoinColumn(name = "oblika_studija")
    private OblikaStudija oblikaStudija;

    @Column(name = "prosta_izbira")
    private boolean prostaIzbira;

    @Column(name = "izkoriscen")
    private boolean izkoriscen = false;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public VrstaVpisa getVrstaVpisa() {
        return vrstaVpisa;
    }

    public void setVrstaVpisa(VrstaVpisa vrstaVpisa) {
        this.vrstaVpisa = vrstaVpisa;
    }

    public StudijskiProgram getStudijskiProgram() {
        return studijskiProgram;
    }

    public void setStudijskiProgram(StudijskiProgram studijskiProgram) {
        this.studijskiProgram = studijskiProgram;
    }

    public Letnik getLetnik() {
        return letnik;
    }

    public void setLetnik(Letnik letnik) {
        this.letnik = letnik;
    }

    public StudijskoLeto getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(StudijskoLeto studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
    }

    public NacinStudija getNacinStudija() {
        return nacinStudija;
    }

    public void setNacinStudija(NacinStudija nacinStudija) {
        this.nacinStudija = nacinStudija;
    }

    public OblikaStudija getOblikaStudija() {
        return oblikaStudija;
    }

    public void setOblikaStudija(OblikaStudija oblikaStudija) {
        this.oblikaStudija = oblikaStudija;
    }

    public boolean isProstaIzbira() {
        return prostaIzbira;
    }

    public void setProstaIzbira(boolean prostaIzbira) {
        this.prostaIzbira = prostaIzbira;
    }

    public boolean isIzkoriscen() {
        return izkoriscen;
    }

    public void setIzkoriscen(boolean izkoriscen) {
        this.izkoriscen = izkoriscen;
    }
}

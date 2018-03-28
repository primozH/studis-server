package vpis;

import sifranti.*;
import vloge.Student;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "vpis")
public class Vpis {

    @EmbeddedId
    private VpisId vpisId;

    @ManyToOne(targetEntity = StudijskiProgram.class)
    @JoinColumn(name = "studijski_program")
    private StudijskiProgram studijskiProgram;
    @ManyToOne(targetEntity = VrstaVpisa.class)
    @JoinColumn(name = "vrsta_vpisa", nullable = false)
    private VrstaVpisa vrstaVpisa;
    @ManyToOne(targetEntity = NacinStudija.class)
    @JoinColumn(name = "nacin_studija")
    private NacinStudija nacinStudija;

    @ManyToOne(targetEntity = OblikaStudija.class)
    @JoinColumn(name = "oblika_studija")
    private OblikaStudija oblikaStudija;
    @ManyToOne(targetEntity = Letnik.class)
    @JoinColumn(name = "letnik")
    private Letnik letnik;

    public VpisId getVpisId() {
        return vpisId;
    }

    public void setVpisId(VpisId vpisId) {
        this.vpisId = vpisId;
    }

    public StudijskiProgram getStudijskiProgram() {
        return studijskiProgram;
    }

    public void setStudijskiProgram(StudijskiProgram studijskiProgram) {
        this.studijskiProgram = studijskiProgram;
    }

    public VrstaVpisa getVrstaVpisa() {
        return vrstaVpisa;
    }

    public void setVrstaVpisa(VrstaVpisa vrstaVpisa) {
        this.vrstaVpisa = vrstaVpisa;
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

    public Letnik getLetnik() {
        return letnik;
    }

    public void setLetnik(Letnik letnik) {
        this.letnik = letnik;
    }
}

@Embeddable
class VpisId implements Serializable {
    @Column(name = "student")
    private Integer studentId;
    @Column(name = "studijsko_leto")
    private Integer studijskoLetoId;


    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudijskoLetoId() {
        return studijskoLetoId;
    }

    public void setStudijskoLetoId(Integer studijskoLetoId) {
        this.studijskoLetoId = studijskoLetoId;
    }
}


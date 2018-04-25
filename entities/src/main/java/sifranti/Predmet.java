package sifranti;

import vpis.Semester;

import javax.persistence.*;

@Entity
@Table(name = "predmet")
public class Predmet {

    @Id
    @Column(name = "sifra")
    private Integer sifra;
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "semester")
    @Enumerated
    private Semester semester;
    @Column(name = "ects")
    private Integer ECTS;

    public Integer getSifra() {
        return sifra;
    }

    public void setSifra(Integer sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Integer getECTS() {
        return ECTS;
    }

    public void setECTS(Integer ECTS) {
        this.ECTS = ECTS;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Predmet) {
            Predmet p = (Predmet) obj;
            if (this.sifra.equals(p.getSifra()))
                return true;
        }

        return false;
    }
}

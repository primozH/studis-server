package helpers;

import java.time.LocalDateTime;

public class PrijavniPodatkiIzpit {
    private Integer student;
    private Integer predmet;
    private Integer studijskoLeto;
    private LocalDateTime datumIzvajanja;

    public PrijavniPodatkiIzpit(Integer student, Integer predmet, Integer studijskoLeto, LocalDateTime datumIzvajanja) {
        this.student = student;
        this.predmet = predmet;
        this.studijskoLeto = studijskoLeto;
        this.datumIzvajanja = datumIzvajanja;
    }

    public Integer getStudent() {
        return student;
    }

    public void setStudent(Integer student) {
        this.student = student;
    }

    public Integer getPredmet() {
        return predmet;
    }

    public void setPredmet(Integer predmet) {
        this.predmet = predmet;
    }

    public Integer getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(Integer studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
    }

    public LocalDateTime getDatumIzvajanja() {
        return datumIzvajanja;
    }

    public void setDatumIzvajanja(LocalDateTime datumIzvajanja) {
        this.datumIzvajanja = datumIzvajanja;
    }
}

package common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class PrijavniPodatki {

    private Integer student;
    private Integer predmet;
    private Integer studijskoLeto;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime datumIzvajanja;

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

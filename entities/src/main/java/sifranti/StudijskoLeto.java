package sifranti;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class StudijskoLeto {

    @Id @GeneratedValue private Integer id;
    private String studijskoLeto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudijskoLeto() {
        return studijskoLeto;
    }

    public void setStudijskoLeto(String studijskoLeto) {
        this.studijskoLeto = studijskoLeto;
    }
}

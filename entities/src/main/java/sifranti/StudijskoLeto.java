package sifranti;

import javax.persistence.*;

@Entity
@Table(name = "studijsko_leto")
public class StudijskoLeto {

    @Id @GeneratedValue private Integer id;
    @Column(name = "studijsko_leto")
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

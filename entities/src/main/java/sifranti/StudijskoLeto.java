package sifranti;

import javax.persistence.*;

@Entity
@Table(name = "studijsko_leto")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.sifranti.StudijskoLeto.vrniStudijskoLeto", query = "SELECT s FROM StudijskoLeto s WHERE " +
                "s.studijskoLeto LIKE :studijskoLeto"),
        @NamedQuery(name = "entitete.sifranti.StudijskoLeto.vrniZadnjeStudijskoLeto", query = "SELECT s FROM StudijskoLeto s ORDER BY s.id DESC")
}
)
public class StudijskoLeto {

    @Id
    @Column(name = "sifra")
    private Integer id;
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

package sifranti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

@Entity
@Table(name = "letnik")
public class Letnik {

    @Id
    @Column(name = "letnik")
    @XmlID
    @XmlElement
    private Integer letnik;

    public Integer getLetnik() {
        return letnik;
    }

    public void setLetnik(Integer letnik) {
        this.letnik = letnik;
    }
}

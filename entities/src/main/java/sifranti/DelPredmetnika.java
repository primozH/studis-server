package sifranti;

import javax.persistence.*;

@Entity
@Table(name = "del_predmetnika")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.sifranti.DelPredmetnika.vrniDelPredmetnika", query = "SELECT d FROM DelPredmetnika d WHERE " +
                "d.tip LIKE :tip"),
        @NamedQuery(name = "entitete.sifranti.DelPredmetnika.vrniVseDelePredmetnika", query = "SELECT d FROM DelPredmetnika d")
})
public class DelPredmetnika {

    @Id
    @Column(name = "sifra")
    private Integer sifra;

    @Column(name = "tip")
    private String tip;

    public Integer getSifra() {
        return sifra;
    }

    public void setSifra(Integer sifra) {
        this.sifra = sifra;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}

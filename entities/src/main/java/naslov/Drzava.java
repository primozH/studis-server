package naslov;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "drzava")
public class Drzava {

    @Id
    private String dvomestnaKoda;

    private String trimestnaKoda;
    private Integer numericnaOznaka;
    private String ISONaziv;
    private String slovenskiNaziv;
    private String opombe;

    @OneToMany(targetEntity = Obcina.class)
    private List<Obcina> obcine;

    public String getDvomestnaKoda() {
        return dvomestnaKoda;
    }

    public void setDvomestnaKoda(String dvomestnaKoda) {
        this.dvomestnaKoda = dvomestnaKoda;
    }

    public String getTrimestnaKoda() {
        return trimestnaKoda;
    }

    public void setTrimestnaKoda(String trimestnaKoda) {
        this.trimestnaKoda = trimestnaKoda;
    }

    public Integer getNumericnaOznaka() {
        return numericnaOznaka;
    }

    public void setNumericnaOznaka(Integer numericnaOznaka) {
        this.numericnaOznaka = numericnaOznaka;
    }

    public String getISONaziv() {
        return ISONaziv;
    }

    public void setISONaziv(String ISONaziv) {
        this.ISONaziv = ISONaziv;
    }

    public String getSlovenskiNaziv() {
        return slovenskiNaziv;
    }

    public void setSlovenskiNaziv(String slovenskiNaziv) {
        this.slovenskiNaziv = slovenskiNaziv;
    }

    public String getOpombe() {
        return opombe;
    }

    public void setOpombe(String opombe) {
        this.opombe = opombe;
    }

    public List<Obcina> getObcine() {
        return obcine;
    }

    public void setObcine(List<Obcina> obcine) {
        this.obcine = obcine;
    }
}

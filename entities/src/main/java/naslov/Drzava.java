package naslov;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "drzava")
public class Drzava {

    @Id
    @Column(name = "iso_koda", nullable = false)
    private String dvomestnaKoda;
    @Column(name = "iso_3_koda", nullable = false)
    private String trimestnaKoda;
    @Column(name = "numericna_oznaka", nullable = false)
    private Integer numericnaOznaka;
    @Column(name = "iso_naziv", nullable = false)
    private String ISONaziv;
    @Column(name = "slovenski_naziv", nullable = false)
    private String slovenskiNaziv;
    @Column(name = "opombe")
    private String opombe;


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
}

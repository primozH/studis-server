package naslov;

import javax.persistence.*;

@Entity
@Table(name = "drzava")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.naslov.Drzava.vseDrzave", query = "SELECT c FROM Drzava c ORDER BY c.slovenskiNaziv")
})
public class Drzava {

    @Id
    @Column(name = "numericna_oznaka")
    private Integer numericnaOznaka;

    @Column(name = "iso_koda", nullable = false, length = 2)
    private String dvomestnaKoda;
    @Column(name = "iso_3_koda", nullable = false, unique = true, length = 3)
    private String trimestnaKoda;
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

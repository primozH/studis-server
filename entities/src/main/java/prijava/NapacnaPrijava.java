package prijava;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "napacna_prijava")
@NamedQueries(value = {
        @NamedQuery(name = "entitete.prijava.NapacnaPrijava.vrniGledeNaIP", query = "SELECT np FROM NapacnaPrijava np WHERE np.ip = :ip"),
})
public class NapacnaPrijava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_napacnega")
    private Integer id;

    private String ip;

    @Column(name = "st_napacnih_poskusov")
    private int steviloNapacnihPoskusov;

    @Column(name = "cas_poteka_izklopa")
    private String casPotekaIzklopaMilliseconds;

    public NapacnaPrijava() {
    }

    public NapacnaPrijava(String ip) {
        this.ip = ip;
        this.steviloNapacnihPoskusov = 0;
        this.casPotekaIzklopaMilliseconds = null;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getSteviloNapacnihPoskusov() {
        return steviloNapacnihPoskusov;
    }

    public void povecajSteviloNapacnihPoskusov() {
        setSteviloNapacnihPoskusov(++steviloNapacnihPoskusov);
    }

    public void setSteviloNapacnihPoskusov(int steviloNapacnihPoskusov) {
        if (steviloNapacnihPoskusov >= 3) {
            // Ban for 5minutes
            casPotekaIzklopaMilliseconds = Long.toString(System.currentTimeMillis() + 1 * 60 * 1000);
        }
        this.steviloNapacnihPoskusov = steviloNapacnihPoskusov;
    }

    public String getCasPotekaIzklopaMilliseconds() {
        return casPotekaIzklopaMilliseconds;
    }

    public void setCasPotekaIzklopaMilliseconds(String casPotekaIzklopaMilliseconds) {
        this.casPotekaIzklopaMilliseconds = casPotekaIzklopaMilliseconds;
    }

    public int vrniPreostalCasBana() {
        return casPotekaIzklopaMilliseconds != null ? (int) ((Long.parseLong(casPotekaIzklopaMilliseconds) - System.currentTimeMillis()) / 1000) : 0;
    }

    public boolean jeCasBanaPotekel() {
        return casPotekaIzklopaMilliseconds == null || System.currentTimeMillis() >= Long.parseLong(casPotekaIzklopaMilliseconds);
    }
}

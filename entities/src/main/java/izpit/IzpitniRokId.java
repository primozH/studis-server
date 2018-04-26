package izpit;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class IzpitniRokId implements Serializable {
    private LocalDateTime datumCasIzvajanja;
    private IzvajanjePredmetaId izvajanjePredmeta;

    public IzvajanjePredmetaId getIzvajanjePredmeta() {
        return izvajanjePredmeta;
    }

    public void setIzvajanjePredmeta(IzvajanjePredmetaId izvajanjePredmeta) {
        this.izvajanjePredmeta = izvajanjePredmeta;
    }

    public LocalDateTime getDatumCasIzvajanja() {
        return datumCasIzvajanja;
    }

    public void setDatumCasIzvajanja(LocalDateTime datumCasIzvajanja) {
        this.datumCasIzvajanja = datumCasIzvajanja;
    }
}

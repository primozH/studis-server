package prijava;

/**
 * Uporablja se samo pri prijavi v sistem.
 * S pomocjo para elektronska_posta in geslo poisce v bazi
 * ustreznega uporabnika in mu dovoli oz. zavrne vstop v sistem.
 */
public class Prijava {

    private String elektronskaPosta;
    private String geslo;

    public String getElektronskaPosta() {
        return elektronskaPosta;
    }

    public void setElektronskaPosta(String elektronskaPosta) {
        this.elektronskaPosta = elektronskaPosta;
    }

    public String getGeslo() {
        return geslo;
    }

    public void setGeslo(String geslo) {
        this.geslo = geslo;
    }

}

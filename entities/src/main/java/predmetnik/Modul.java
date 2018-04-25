package predmetnik;

public enum Modul {
    I("Informacijski sistemi"),
    II("Obvladovanje informatike"),
    III("Razvoj programske opreme"),
    IV("Računalniška omrežja"),
    V("Računalniški sistemi"),
    VI("Algoritmi in sistemski programi"),
    VII("Umetna inteligenca"),
    VIII("Medijske tehnologije");

    private String ime;
    Modul(String ime) {
        this.ime = ime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }
}

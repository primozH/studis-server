package vpis;

public enum Stopnja {
    B ("tretja stopnja: doktorski"),
    C ("(predbolonjski) univerzitetni"),
    F ("(predbolonjski) magistrski"),
    G ("(predbolonjski) doktorski"),
    M ("tretja stopnja: doktorski");

    private final String opis;

    Stopnja(String opis) {
        this.opis = opis;
    }
}

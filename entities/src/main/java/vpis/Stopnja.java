package vpis;

public enum Stopnja {
    A ("blank"),
    J ("prva stopnja: visokošolski strokovni"),
    K ("prva stopnja: univerzitetni"),
    L ("druga stopnja: magistrski"),
    M ("tretja stopnja: doktorski");


    private final String opis;

    Stopnja(String opis) {
        this.opis = opis;
    }


    @Override
    public String toString() {
        return this.name() + " - " + this.opis;
    }

}
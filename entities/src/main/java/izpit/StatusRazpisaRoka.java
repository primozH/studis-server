package izpit;

public enum StatusRazpisaRoka {
    DATUM_ZE_PRETECEN("vecji-datum"),
    DATUM_VIKEND("vikend"),
    DATUM_PRAZNIK("praznik"),
    UPORABNIK_NEOBSTAJA ("uporabnik-neobstaja"),
    IZPIT_ZA_URO_OBSTAJA("izpit-za-uro-obstaja"),
    UCITELJ_NE_UCI_PREDMETA("ucitelj-neveljaven"),
    NAPACEN_TIP_UPORABNIKA("napacen-tip"),
    MANJKAJO_PODATKI("manjkajo-podatki"),
    VELJAVEN_VNOS("veljaven");

    private final String sporocilo;

    StatusRazpisaRoka(String sporocilo) {
        this.sporocilo = sporocilo;
    }

    @Override
    public String toString() {
        return this.sporocilo;
    }
}

package izpit;

public enum StatusRazpisaRoka {
    DATUM_ZE_PRETECEN("vecji-datum"),
    DATUM_VIKEND("vikend"),
    DATUM_PRAZNIK("praznik"),
    UPORABNIK_NEOBSTAJA ("uporabnik-neobstaja"),
    DATUM_RAZPISAN_ROK("izpitni rok na ta dan in za ta predmet že obstaja"),
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

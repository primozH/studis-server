package helpers.adapters;

import naslov.Drzava;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DrzavaAdapter extends XmlAdapter<String, Drzava> {
    @Override
    public Drzava unmarshal(String v) throws Exception {
        Drzava drzava = new Drzava();
        drzava.setDvomestnaKoda(v);
        return drzava;
    }

    @Override
    public String marshal(Drzava v) throws Exception {
        return v.getDvomestnaKoda();
    }
}

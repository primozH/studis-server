package helpers.adapters;

import naslov.Drzava;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DrzavaAdapter extends XmlAdapter<Integer, Drzava> {
    @Override
    public Drzava unmarshal(Integer v) throws Exception {
        Drzava drzava = new Drzava();
        drzava.setNumericnaOznaka(v);
        return drzava;
    }

    @Override
    public Integer marshal(Drzava v) throws Exception {
        return v.getNumericnaOznaka();
    }
}

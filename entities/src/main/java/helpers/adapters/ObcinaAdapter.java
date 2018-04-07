package helpers.adapters;

import naslov.Obcina;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ObcinaAdapter extends XmlAdapter<Integer, Obcina> {
    @Override
    public Obcina unmarshal(Integer v) throws Exception {
        Obcina obcina = new Obcina();
        obcina.setSifra(v);
        return obcina;
    }

    @Override
    public Integer marshal(Obcina v) throws Exception {
        return v.getSifra();
    }
}

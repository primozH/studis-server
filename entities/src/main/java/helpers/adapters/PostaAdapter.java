package helpers.adapters;

import naslov.Posta;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PostaAdapter extends XmlAdapter<Integer, Posta> {
    @Override
    public Posta unmarshal(Integer v) throws Exception {
        Posta posta = new Posta();
        posta.setPostnaStevilka(v);
        return posta;
    }

    @Override
    public Integer marshal(Posta v) throws Exception {
        return v.getPostnaStevilka();
    }
}

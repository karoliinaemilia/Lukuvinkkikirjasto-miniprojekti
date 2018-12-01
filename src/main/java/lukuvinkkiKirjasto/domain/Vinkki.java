package lukuvinkkiKirjasto.domain;

import java.time.LocalDate;

public abstract class Vinkki {
    
    protected String nimi;
    protected int pituus;
    protected String linkki;
    protected String tekija;
    protected int julkaisuVuosi;
    protected LocalDate paivamaara;
    protected boolean luettu;

    public boolean isLuettu() {
        return luettu;
    }

    public void setLuettu(boolean luettu) {
        this.luettu = luettu;
    }

    public String getLuettuString() {
        if (this.luettu) {
            return "Luettu";
        } else {
            return "Lukematon";
        }
    }

    public int getJulkaistu() {
        return julkaisuVuosi;
    }

    public void setJulkaistu(int julkaistu) {
        this.julkaisuVuosi = julkaistu;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getPituus() {
        return pituus;
    }

    public void setPituus(int pituus) {
        this.pituus = pituus;
    }

    public String getLinkki() {
        return linkki;
    }

    public void setLinkki(String linkki) {
        this.linkki = linkki;
    }

    public String getTekija() {
        return tekija;
    }

    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    public LocalDate getPaivamaara() {
        return paivamaara;
    }

    public void setPaivamaara(LocalDate paivamaara) {
        this.paivamaara = paivamaara;
    }
    
}

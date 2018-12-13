
package lukuvinkkiKirjasto.domain;

import java.time.LocalDate;

public abstract class Lukuvinkki {
    
    protected String nimi;
    protected int pituus;
    protected String linkki;
    protected String tekija;
    protected int julkaisuVuosi;
    protected LocalDate paivamaara;
    protected boolean luettu;
    private String luettuAika;

    public Lukuvinkki(String nimi, int pituus, String linkki, String tekija, int julkaisuVuosi, LocalDate paivamaara, boolean luettu, String luettuAika) {
        this.nimi = nimi;
        this.pituus = pituus;
        this.linkki = linkki;
        this.tekija = tekija;
        this.julkaisuVuosi = julkaisuVuosi;
        this.paivamaara = paivamaara;
        this.luettu = luettu;
        this.luettuAika = luettuAika;
    }
    
    public String getLuettuString() {
        if (this.luettu) {
            return "Luettu " + luettuAika;
        } else {
            return "Lukematon";
        }
    }
    
    public boolean isLuettu() {
         return luettu;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setPituus(int pituus) {
        this.pituus = pituus;
    }

    public void setLinkki(String linkki) {
        this.linkki = linkki;
    }

    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    public void setJulkaistu(int julkaisuVuosi) {
        this.julkaisuVuosi = julkaisuVuosi;
    }

    public void setPaivamaara(LocalDate paivamaara) {
        this.paivamaara = paivamaara;
    }

    public void setLuettu(boolean luettu) {
        this.luettu = luettu;
    }

    public void setLuettuAika(String luettuAika) {
        this.luettuAika = luettuAika;
    }
    
    

    public String getNimi() {
        return nimi;
    }

    public int getPituus() {
        return pituus;
    }

    public String getLinkki() {
        return linkki;
    }

    public String getTekija() {
        return tekija;
    }

    public int getJulkaistu() {
        return julkaisuVuosi;
    }

    public LocalDate getPaivamaara() {
        return paivamaara;
    }

    public String getLuettuAika() {
        return luettuAika;
    }
    
    
}

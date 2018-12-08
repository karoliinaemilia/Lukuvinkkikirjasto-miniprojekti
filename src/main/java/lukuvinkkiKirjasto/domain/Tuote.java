package lukuvinkkiKirjasto.domain;

import java.time.LocalDate;

public class Tuote {

    private String nimi;
    private int pituus;
    private String linkki;
    private String tekija;
    private int julkaisuVuosi;
    private LocalDate paivamaara;
    private boolean luettu;
    private String luettuAika;

    public Tuote(String nimi, int pituus, String linkki, String tekija,
            int julkaisuVuosi, LocalDate paivamaara, boolean luettu, String aika) {

        this.nimi = nimi;
        this.pituus = pituus;
        this.linkki = linkki;
        this.tekija = tekija;
        this.julkaisuVuosi = julkaisuVuosi;
        this.paivamaara = paivamaara;
        this.luettu = luettu;
        this.luettuAika = aika;
    }

    public int getJulkaisuVuosi() {
        return julkaisuVuosi;
    }

    public void setJulkaisuVuosi(int julkaisuVuosi) {
        this.julkaisuVuosi = julkaisuVuosi;
    }

    public boolean isLuettu() {

        return luettu;
    }

    public String getLuettuAika() {
        return luettuAika;
    }

    public void setLuettuAika(String aika) {
        this.luettuAika = aika;

    }

    public void setLuettu(boolean luettu) {

        this.luettu = luettu;
    }

    public String getLuettuString() {

        if (this.luettu) {
            return "Luettu " + this.luettuAika;
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

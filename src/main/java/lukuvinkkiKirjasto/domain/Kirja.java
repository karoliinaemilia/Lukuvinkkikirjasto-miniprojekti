package lukuvinkkiKirjasto.domain;

import java.time.LocalDate;
import java.util.Date;

public class Kirja {

    private Integer ISBN;
    private String genre;
    private String nimi;
    private int pituus;
    private String linkki;
    private String tekija;
    private int julkaisuVuosi;
    private LocalDate paivamaara;
    private boolean luettu;

    public Kirja(Integer id, String genre, String nimi, int pituus, String linkki, String tekija, int julkaisuVuosi, LocalDate paivamaara, boolean luettu) {
        this.ISBN = id;
        this.genre = genre;
        this.nimi = nimi;
        this.pituus = pituus;
        this.linkki = linkki;
        this.tekija = tekija;
        this.julkaisuVuosi = julkaisuVuosi;
        this.paivamaara = paivamaara;
        this.luettu = luettu;

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

    public void setLuettu(boolean luettu) {
        this.luettu = luettu;
    }

    public int getJulkaistu() {
        return julkaisuVuosi;
    }

    public void setJulkaistu(int julkaistu) {
        this.julkaisuVuosi = julkaistu;
    }

    public Integer getISBN() {
        return ISBN;
    }

    public void setId(int id) {
        this.ISBN = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public String toString() {
        String merkki = "LUKEMATON";
        if (isLuettu() == true) {
            merkki = "LUETTU";
        }
        String tuloste = getNimi() + ", ISBN: " + getISBN() + ", Kirjailija: " + getTekija() + ", julkaisuvuosi: " + getJulkaistu() + ", pituus: " + getPituus() + ", genre: " + getGenre();
        if (!linkki.isEmpty()) {
            tuloste += ", linkki: " + getLinkki();
        }
        tuloste += ", " + merkki;
        return tuloste;
    }
}

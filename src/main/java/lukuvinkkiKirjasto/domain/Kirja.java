package lukuvinkkiKirjasto.domain;

import java.time.LocalDate;

public class Kirja extends Vinkki {

    private String ISBN;
    private String genre;

    public Kirja(String id, String genre, String nimi, int pituus, String linkki, String tekija, int julkaisuVuosi, LocalDate paivamaara, boolean luettu) {
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

    public String getISBN() {
        return ISBN;
    }

    public void setId(String id) {
        this.ISBN = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

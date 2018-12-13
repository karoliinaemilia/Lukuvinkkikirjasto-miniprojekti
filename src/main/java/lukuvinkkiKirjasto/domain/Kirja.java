package lukuvinkkiKirjasto.domain;

import java.time.LocalDate;

public class Kirja extends Lukuvinkki {

    private String ISBN;
    private String genre;

    public Kirja(
            String id, String genre, String nimi, int pituus, String linkki,
            String tekija, int julkaisuVuosi, LocalDate paivamaara, boolean luettu,
            String luettuAika
    ) {
        super(nimi, pituus, linkki, tekija, julkaisuVuosi, paivamaara, luettu, luettuAika);
        this.ISBN = id;
        this.genre = genre;

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

    @Override
    public String toString() {
        return "Tekijä: " + tekija + ", nimi: " + nimi;
    }
}

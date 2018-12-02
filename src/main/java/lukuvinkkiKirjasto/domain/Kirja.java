package lukuvinkkiKirjasto.domain;

import com.sun.jna.platform.win32.Sspi;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import javax.print.attribute.standard.DateTimeAtCompleted;

public class Kirja {

    private String ISBN;
    private String genre;
    private String nimi;
    private int pituus;
    private String linkki;
    private String tekija;
    private int julkaisuVuosi;
    private LocalDate paivamaara;
    private boolean luettu;
    private String luettuAika;

    public Kirja(
            String id, String genre, String nimi, int pituus, String linkki, 
            String tekija, int julkaisuVuosi, LocalDate paivamaara, boolean luettu,
            String luettuAika
    ) {
        this.ISBN = id;
        this.genre = genre;
        this.nimi = nimi;
        this.pituus = pituus;
        this.linkki = linkki;
        this.tekija = tekija;
        this.julkaisuVuosi = julkaisuVuosi;
        this.paivamaara = paivamaara;
        this.luettu = luettu;
        this.luettuAika = luettuAika;
     

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

    @Override
    public String toString() {
       
        
        return "Tekijä: " + tekija + ", nimi: " + nimi;
    }
}

package lukuvinkkiKirjasto.domain;


public class Kirja {
    
    private String genre;
    private String nimi;
    private int pituus;
    private String linkki;
    private String tekija;
    private String paivamaara;
    
    public Kirja(String genre2, String nimi2, int pituus2, String linkki2, String tekija2, String paivamaara2) {
        
        this.genre = genre2;
        this.nimi = nimi2; 
        this.pituus = pituus2;
        this.linkki = linkki2;
        this.tekija = tekija2;
        this.paivamaara = paivamaara2;
                
    }
    
    
    
}

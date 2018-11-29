package lukuvinkkiKirjasto.domain;

import java.time.LocalDate;
import java.util.Date;

public class Artikkeli {

    private Integer id;
    private String nimi;
    private int pituus;
    private String linkki;
    private String tekija;
    private String julkaisuLehti;
    private int julkaisuVuosi;
    private int numero;
    private String sivut;
    private LocalDate paivamaara;
    private boolean luettu;

    public Artikkeli(Integer id, String nimi, int pituus, String linkki, String tekija, String julkaisuLehti, int julkaisuVuosi, int numero, String sivut, LocalDate paivamaara, boolean luettu) {
        this.id = id;
        this.nimi = nimi;
        this.pituus = pituus;
        this.linkki = linkki;
        this.tekija = tekija;
        this.julkaisuLehti = julkaisuLehti;
        this.julkaisuVuosi = julkaisuVuosi;
        this.numero = numero;
        this.sivut = sivut;
        this.paivamaara = paivamaara;
        this.luettu = luettu;
    }

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

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getJulkaisuLehti() {
        return this.julkaisuLehti;
    }

    public void setJulkaisuLehti(String julkaisuLehti) {
        this.julkaisuLehti = julkaisuLehti;
    }

    public String getSivut() {
        return this.sivut;
    }

    public void setSivut(String sivut) {
        this.sivut = sivut;
    }

    public int getJulkaistu() {
        return julkaisuVuosi;
    }

    public void setJulkaistu(int julkaistu) {
        this.julkaisuVuosi = julkaistu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        String tuloste = nimi + ", Lehti: " + julkaisuLehti + ", nro: " + numero + ", sivuja: " + sivut + ", tekijä: " + tekija + ", julkaistu: " + julkaisuVuosi;
        if (!linkki.isEmpty()) {
            tuloste += ", linkki: " + linkki;
        }
        if (luettu) {
            tuloste += ", luettu";
        } else {
            tuloste += ", lukematon";
        }
        return tuloste;
    }
}

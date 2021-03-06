package lukuvinkkiKirjasto.domain;

import java.time.LocalDate;

public class Artikkeli extends Lukuvinkki {

    private Integer id;
    private String julkaisuLehti;
    private int numero;
    private String sivut;

    public Artikkeli(Integer id, String nimi, int pituus, String linkki, String tekija, String julkaisuLehti, int julkaisuVuosi, int numero, String sivut, LocalDate paivamaara, boolean luettu, String luettuAika) {
        super(nimi, pituus, linkki, tekija, julkaisuVuosi, paivamaara, luettu, luettuAika);
        this.id = id;
        this.julkaisuLehti = julkaisuLehti;
        this.numero = numero;
        this.sivut = sivut;
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

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
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

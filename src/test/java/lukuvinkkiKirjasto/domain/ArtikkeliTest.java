package lukuvinkkiKirjasto.domain;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Date;


public class ArtikkeliTest {

    Artikkeli lehti;

    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        lehti = new Artikkeli(1, "testiartikkeli", 10,"www.testiartikkeli.fi","testaaja","testijulkaisija", 2018, 2, "20-30",LocalDate.now(),true, "2018-1");

    }

    @Test
    public void konstruktoriLuoOikeanIdn() {
        assertEquals(1, lehti.getId(), vertailuTarkkuus);
    }
    
     @Test
    public void konstruktoriLuoOikeanLuetun() {
        assertEquals(true, lehti.isLuettu());
    }
    @Test
    public void konstruktoriLuoOikeanNimen() {
        assertEquals("testiartikkeli", lehti.getNimi());
    }
    
    @Test
    public void konstruktoriLuoOikeanNumeron() {
        assertEquals(2, lehti.getNumero(), vertailuTarkkuus);
    }

    @Test
    public void konstruktoriLuoOikeanJulkaisuLehden() {
        assertEquals("testijulkaisija", lehti.getJulkaisuLehti());
    }

    @Test
    public void konstruktoriLuoOikeatSivut() {
        assertEquals("20-30", lehti.getSivut());
    }
    
    
    @Test
    public void konstruktoriLuoOikeanPituuden() {
        assertEquals(10, lehti.getPituus(), vertailuTarkkuus);
    }
    
    @Test
    public void konstruktoriLuoOikeanLinkin() {
        assertEquals("www.testiartikkeli.fi", lehti.getLinkki());
    }
    
    @Test
    public void konstruktoriLuoOikeanTekijan() {
        assertEquals("testaaja", lehti.getTekija());
    }
    
    @Test
    public void konstruktoriLuoOikeanPaivamaaran() {
        assertEquals(LocalDate.now(), lehti.getPaivamaara());
    }
    
    
    @Test
    public void luetunMuuttaminenOnnistuu() {
        lehti.setLuettu(false);
        assertEquals(false, lehti.isLuettu());
    }
    @Test
    public void idnMuuttaminenOnnistuu() {
        lehti.setId(4);
        assertEquals(4, lehti.getId(), vertailuTarkkuus);
    }
    
    @Test
    public void numeronMuuttaminenOnnistuu() {
        lehti.setNumero(14);
        assertEquals(14, lehti.getNumero());
    }
    
    
    @Test
    public void julkaisulehdenMuuttaminenOnnistuu() {
        lehti.setJulkaisuLehti("lehti1");
        assertEquals("lehti1", lehti.getJulkaisuLehti());
    }
    
    @Test
    public void sivujenMuuttaminenOnnistuu() {
        lehti.setSivut("30-40");
        assertEquals("30-40", lehti.getSivut());
    }
    @Test
    public void nimenMuuttaminenOnnistuu() {
        lehti.setNimi("testi1");
        assertEquals("testi1", lehti.getNimi());
    }
    
    
    @Test
    public void pituudenMuuttaminenOnnistuu() {
        lehti.setPituus(20);
        assertEquals(20, lehti.getPituus(), vertailuTarkkuus);
    }
    
    @Test
    public void linkinMuuttaminenOnnistuu() {
        lehti.setLinkki("www.testi2.fi");
        assertEquals("www.testi2.fi", lehti.getLinkki());
    }
    
    @Test
    public void kirjoittajanMuuttaminenOnnistuu() {
        lehti.setTekija("uusi");
        assertEquals("uusi", lehti.getTekija());
    }
    
    @Test
    public void julkaisuvuodenMuuttaminenOnnistuu() {
        lehti.setJulkaistu(2011);
        assertEquals(2011, lehti.getJulkaistu(), vertailuTarkkuus);
    }
    @Test
    public void tulostusOikeinJosLinkkiJaLuettu() {
        assertEquals(lehti.toString(), "testiartikkeli, Lehti: testijulkaisija, "
                + "nro: 2, sivuja: 20-30, tekijä: testaaja, julkaistu: 2018, linkki: www.testiartikkeli.fi, luettu");
        
    }
    @Test
    public void tulostusOikeinJosEiLinkkijaLukematon() {
        Artikkeli kappale = new Artikkeli(2, "joku", 1, "", "tuntematon", "jossain", 2018, 11, "2-3", LocalDate.now(), false, "2019");
        
        assertEquals(kappale.toString(), "joku, Lehti: jossain, "
                + "nro: 11, sivuja: 2-3, tekijä: tuntematon, julkaistu: 2018, lukematon");
    }
    @Test
    public void lukemisenAjankohtaOnnistuu() {
        assertEquals(lehti.getLuettuAika(), "2018-1");
    }
    @Test
    public void lukemisenAjankohtaaVoiVaihtaa() {
        lehti.setLuettuAika("2019");
        assertEquals(lehti.getLuettuAika(), "2019");
    }
    @Test
    public void lukematonArvona() {
        assertEquals(lehti.getLuettuString(), "Luettu 2018-1");
    }
    @Test
    public void luettuArvona() {
        lehti.setLuettu(false);
        assertEquals(lehti.getLuettuString(), "Lukematon");
    }
    @Test
    public void paivamaaraaVoiVaihtaa() {
        lehti.setPaivamaara(LocalDate.now());
        assertEquals(lehti.getPaivamaara(), LocalDate.now());
    }
}
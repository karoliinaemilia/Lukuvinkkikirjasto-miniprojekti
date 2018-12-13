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


public class KirjaTest {

    Kirja lehti;

    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        lehti = new Kirja("1234", "viihde", "testilehti", 20, "www.testi.fi", "testikirjoittaja", 2009, LocalDate.now(), true, "2018-12");

    }

    @Test
    public void konstruktoriLuoOikeanISBN() {
        assertEquals("1234", lehti.getISBN());
    }
    
    
     @Test
    public void konstruktoriLuoOikeanLuetun() {
        assertEquals(true, lehti.isLuettu());
    }
    
    @Test
    public void konstruktoriLuoOikeanNimen() {
        assertEquals("testilehti", lehti.getNimi());
    }
    
    @Test
    public void konstruktoriLuoOikeanGenren() {
        assertEquals("viihde", lehti.getGenre());
    }

    @Test
    public void konstruktoriLuoOikeanPituuden() {
        assertEquals(20, lehti.getPituus(), vertailuTarkkuus);
    }
    
    @Test
    public void konstruktoriLuoOikeanLinkin() {
        assertEquals("www.testi.fi", lehti.getLinkki());
    }
    
    @Test
    public void konstruktoriLuoOikeanTekijan() {
        assertEquals("testikirjoittaja", lehti.getTekija());
    }
    
    @Test
    public void konstruktoriLuoOikeanPaivamaaran() {
        assertEquals(LocalDate.now(), lehti.getPaivamaara());
    }
    
    @Test
    public void isbnMuuttaminenOnnistuu() {
        lehti.setId("4321");
        assertEquals("4321", lehti.getISBN());
    }
    
    
    @Test
    public void luetunMuuttaminenOnnistuu() {
        lehti.setLuettu(false);
        assertEquals(false, lehti.isLuettu());
    }
    
    @Test
    public void nimenMuuttaminenOnnistuu() {
        lehti.setNimi("testi");
        assertEquals("testi", lehti.getNimi());
    }
    
    @Test
    public void genrenMuuttaminenOnnistuu() {
        lehti.setGenre("kauhu");
        assertEquals("kauhu", lehti.getGenre());
    }
    
    @Test
    public void pituudenMuuttaminenOnnistuu() {
        lehti.setPituus(10);
        assertEquals(10, lehti.getPituus(), vertailuTarkkuus);
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
    public void julkaisuVuodenOikeaPalautus() {
        assertEquals(lehti.getJulkaistu(), 2009, vertailuTarkkuus);
    }
    @Test
    public void julkaisuVuodenMuokkaaminen() {    
        lehti.setJulkaistu(2011);
        assertEquals(2011, lehti.getJulkaistu(), vertailuTarkkuus);
    }
    @Test
    public void setLuettuAikaVaihtuu() {
        lehti.setLuettuAika("2019");
        assertEquals(lehti.getLuettuAika(), "2019");
    }
    @Test
    public void LuettuStringPalutuuOikeinLukemattomalla() {
        lehti.setLuettu(false);
        assertEquals(lehti.getLuettuString(), "Lukematon");
    }
    @Test
    public void palauttaaOikeinLuetulla() {
        assertEquals(lehti.getLuettuString(), "Luettu 2018-12");
    }
    @Test
    public void paivamaaranVoiVaihtaa() {
        lehti.setPaivamaara(LocalDate.now());
        assertEquals(lehti.getPaivamaara(), LocalDate.now());
    }
    @Test
    public void tulostusToimii() {
        assertEquals(lehti.toString(), "Tekijä: testikirjoittaja, nimi: testilehti");
    }
    
}
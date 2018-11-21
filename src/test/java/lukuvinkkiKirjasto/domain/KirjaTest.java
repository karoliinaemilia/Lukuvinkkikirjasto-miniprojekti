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
        lehti = new Kirja(1, "viihde", "testilehti", 20, "www.testi.fi", "testikirjoittaja", 2009, LocalDate.now());

    }

    @Test
    public void konstruktoriLuoOikeanIdn() {
        assertEquals(1, lehti.getId(), vertailuTarkkuus);
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
    public void tulostusOnOikea() {
        assertEquals("testilehti, Kirjailija: testikirjoittaja, julkaistu: 2009, pituus: 20, genre: viihde, linkki: www.testi.fi", lehti.toString());
    }
}
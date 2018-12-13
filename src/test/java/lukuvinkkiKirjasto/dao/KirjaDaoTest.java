/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkkiKirjasto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.domain.Kirja;
import lukuvinkkiKirjasto.domain.KirjaTagi;
import lukuvinkkiKirjasto.domain.Tagi;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author strohm
 */
public class KirjaDaoTest {

    Database db;
    KirjaDao kirjaDao;
    KirjaTagiDao kirjaTagiDao;
    TagiDao tagiDao;

    public KirjaDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Throwable {
        db = new Database("jdbc:sqlite:LukuvinkkiKirjasto.db");
        db.setTest(true);
        kirjaDao = new KirjaDao(db);
        kirjaTagiDao = new KirjaTagiDao(db);
        tagiDao = new TagiDao(db);
        
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kirja;");
        stmt.execute();
        stmt.close();
        conn.close();
    }

    public void lisaaDataa() throws Throwable {
        kirjaDao.saveOrUpdate(new Kirja("1", "genre", "kirja1", 1, "linkki", "tekija", 1, LocalDate.MAX, true, "luettuAika"));
        kirjaDao.saveOrUpdate(new Kirja("2", "genre", "kirja2", 1, "linkki", "tekija", 1, LocalDate.MAX, true, "luettuAika"));
        kirjaDao.saveOrUpdate(new Kirja("3", "genre", "kirja3", 1, "linkki", "tekija", 1, LocalDate.MAX, true, "luettuAika"));

    }

    @Test
    public void findOneLoytaaOikeanKirjan() throws Throwable {
        lisaaDataa();
        assertTrue(kirjaDao.findOne("2").getNimi().equals("kirja2"));
    }

    @Test
    public void findOnePalauttaaNullJosKirjaaEiOle() throws Throwable {
        lisaaDataa();
        assertTrue(kirjaDao.findOne("4") == null);
    }

    @Test
    public void findAllLoytaaOikeanMaaranKirjoja() throws Throwable {
        lisaaDataa();
        assertTrue(kirjaDao.findAll().size() == 3);
    }

    @Test
    public void saveOrUpdateTallentaaKirjan() throws Throwable {
        Kirja kirja = new Kirja("1", "genre", "kirja1", 1, "linkki", "tekija", 1, LocalDate.MAX, true, "luettuAika");
        kirjaDao.saveOrUpdate(kirja);
        assertTrue(kirjaDao.findOne("1").getNimi().equals("kirja1"));
    }

    @Test
    public void saveOrUpdateEiTallennaSamaaKirjaaUseaanKertaan() throws Throwable {
        Kirja kirja = new Kirja("1", "genre", "kirja1", 1, "linkki", "tekija", 1, LocalDate.MAX, true, "luettuAika");
        kirjaDao.saveOrUpdate(kirja);
        kirjaDao.saveOrUpdate(kirja);
        assertTrue(kirjaDao.findAll().size() == 1);
    }

    @Test
    public void deletePoistaaKirjan() throws Throwable {
        lisaaDataa();
        kirjaDao.delete("2");
        assertTrue(kirjaDao.findAll().size() == 2);

    }

    @Test
    public void deletePoistaaOikeanKirjan() throws Throwable {
        lisaaDataa();
        kirjaDao.delete("2");
        assertTrue(kirjaDao.findOne("2") == null);
    }

    @Test
    public void updateInformationPaivittaaTiedot() throws Throwable {
        Kirja kirja = new Kirja("1", "genre", "kirja1", 1, "linkki", "tekija", 1, LocalDate.MAX, true, "luettuAika");
        kirjaDao.saveOrUpdate(kirja);
        kirja.setId("2");
        kirja.setGenre("uusiGenre");
        kirja.setNimi("uusiNimi");
        kirja.setPituus(7);
        kirja.setLinkki("uusiLinkki");
        kirja.setTekija("sina");
        kirja.setJulkaistu(2018);
        kirja.setPaivamaara(LocalDate.MIN);

        kirjaDao.updateInformation("1", kirja);
        Kirja kirjaNoudettu = kirjaDao.findOne("2");
        assertTrue(kirjaNoudettu.getISBN().equals("2"));
        assertTrue(kirjaNoudettu.getGenre().equals("uusiGenre"));
        assertTrue(kirjaNoudettu.getNimi().equals("uusiNimi"));
        assertTrue(kirjaNoudettu.getPituus() == 7);
        assertTrue(kirjaNoudettu.getLinkki().equals("uusiLinkki"));
        assertTrue(kirjaNoudettu.getTekija().equals("sina"));
        assertTrue(kirjaNoudettu.getJulkaistu() == 2018);
    }

    @Test
    public void updateInformationEiLisaaUuttaKirjaa() throws Throwable {
        Kirja kirja = new Kirja("1", "genre", "kirja1", 1, "linkki", "tekija", 1, LocalDate.MAX, true, "luettuAika");

        kirjaDao.saveOrUpdate(kirja);
        kirja.setNimi("uusiNimi");
        assertTrue(kirjaDao.findAll().size() == 1);

    }
    
    @Test
    public void tietokannassaOlevaKirjaTagiLotyy() throws SQLException {
        Kirja kirja = new Kirja("9789511319177", "genre", "kirja1", 1, "linkki", "tekija", 1, LocalDate.MAX, true, "luettuAika");
        kirjaDao.saveOrUpdate(kirja);
        Tagi tagi = new Tagi(2, "Info");
        tagiDao.saveOrUpdate(tagi);
        KirjaTagi kirjaTagi = new KirjaTagi("9789511319177", 2);
        kirjaTagiDao.saveOrUpdate(kirjaTagi);
        
        KirjaTagi uusi = kirjaTagiDao.findOne("9789511319177");
        
        assertEquals(new Integer(2), uusi.getTagiId());
    }

    @After
    public void tearDown() {
    }

}

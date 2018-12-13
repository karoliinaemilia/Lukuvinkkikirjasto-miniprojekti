package lukuvinkkiKirjasto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.domain.Artikkeli;
import lukuvinkkiKirjasto.domain.ArtikkeliTagi;
import lukuvinkkiKirjasto.domain.Kirja;
import lukuvinkkiKirjasto.domain.KirjaTagi;
import lukuvinkkiKirjasto.domain.Tagi;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TagiDaotTest {
    
    Database db;
    ArtikkeliDao artikkeliDao;
    ArtikkeliTagiDao artikkeliTagiDao;
    KirjaDao kirjaDao;
    KirjaTagiDao kirjaTagiDao;
    TagiDao tagiDao;
    
    public TagiDaotTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        db = new Database("jdbc:sqlite:LukuvinkkiKirjasto.db");
        db.setTest(true);
        artikkeliDao = new ArtikkeliDao(db);
        kirjaDao = new KirjaDao(db);
        kirjaTagiDao = new KirjaTagiDao(db);
        artikkeliTagiDao = new ArtikkeliTagiDao(db);
        tagiDao = new TagiDao(db);
        
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Artikkeli;");
        stmt.execute();
        stmt = conn.prepareStatement("DELETE FROM Kirja");
        stmt.execute();
        stmt = conn.prepareStatement("DELETE FROM Tagi");
        stmt.execute();
        stmt.close();
        conn.close();
    }
    
    @Test
    public void artikkeliTagiPalauttaaNullJosTietokannassaEiOleArtikkeliTagiOlioita() throws SQLException {
        assertEquals(artikkeliTagiDao.findOne(45), null);
    }
    
    @Test
    public void kirjaTagiPalauttaaNullJosTietokannassaEiOleKirjaTagiOlioita() throws SQLException {
        assertEquals(kirjaTagiDao.findOne("45"), null);
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
    
    @Test
    public void tietokannassaOlevaArtikkeliTagiLotyy() throws SQLException {
        Artikkeli artikkeli = new Artikkeli(2, "Double trouble", 0, "linkki", "tekija", "julkaisuLehti", 0, 0, "sivut", LocalDate.MAX, true, "aika");
        artikkeliDao.saveOrUpdate(artikkeli);
        Tagi tagi = new Tagi(4, "Info");
        tagiDao.saveOrUpdate(tagi);
        ArtikkeliTagi artikkeliTagi = new ArtikkeliTagi(2, 4);
        artikkeliTagiDao.saveOrUpdate(artikkeliTagi);
        
        ArtikkeliTagi uusi = artikkeliTagiDao.findOne(2);
        
        assertEquals(new Integer(4), uusi.getTagiId());
    }
    
    @After
    public void tearDown() {
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkkiKirjasto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.domain.Artikkeli;
import lukuvinkkiKirjasto.domain.ArtikkeliTagi;
import lukuvinkkiKirjasto.domain.Kirja;
import lukuvinkkiKirjasto.domain.KirjaTagi;
import lukuvinkkiKirjasto.domain.Tagi;
import lukuvinkkiKirjasto.ui.Ui;
import static lukuvinkkiKirjasto.ui.Ui.db;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import spark.Spark;

/**
 *
 * @author strohm
 */
public class ArtikkeliDaoTest {

    Database db;
    ArtikkeliDao artikkeliDao;

    public ArtikkeliDaoTest() {
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
        artikkeliDao = new ArtikkeliDao(db);

        
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Artikkeli;");
        stmt.execute();
        stmt.close();
        conn.close();
    }

    public void lisaaDataa() throws Throwable {
        artikkeliDao.saveOrUpdate(new Artikkeli(1, "nimi", 0, "linkki", "tekija", "julkaisuLehti", 0, 0, "sivut", LocalDate.MAX, true, "aika"));
        artikkeliDao.saveOrUpdate(new Artikkeli(2, "nimi2", 0, "linkki", "tekija", "julkaisuLehti", 0, 0, "sivut", LocalDate.MAX, true, "aika"));
        artikkeliDao.saveOrUpdate(new Artikkeli(3, "nimi3", 0, "linkki", "tekija", "julkaisuLehti", 0, 0, "sivut", LocalDate.MAX, true, "aika"));
    }

    @Test
    public void findOneLoytaaOikeanArtikkelin() throws Throwable {
        lisaaDataa();
        assertTrue(artikkeliDao.findOne(2).getNimi().equals("nimi2"));
    }

    @Test
    public void findOnePalauttaaNullJosArtikkeliaEiOle() throws Throwable {
        lisaaDataa();
        assertTrue(artikkeliDao.findOne(4) == null);
    }

    @Test
    public void findAllLoytaaOikeanMaaranArtikkeleja() throws Throwable {
        lisaaDataa();
        assertTrue(artikkeliDao.findAll().size() == 3);
    }

    @Test
    public void saveOrUpdateTallentaaArtikkelin() throws Throwable {
        Artikkeli artikkeli = new Artikkeli(1, "nimi", 0, "linkki", "tekija", "julkaisuLehti", 0, 0, "sivut", LocalDate.MAX, true, "aika");
        artikkeliDao.saveOrUpdate(artikkeli);
        assertTrue(artikkeliDao.findOne(1).getNimi().equals("nimi"));
    }

    @Test
    public void saveOrUpdateEiTallennaSamaaArtikkeliaUseaanKertaan() throws Throwable {
        Artikkeli artikkeli = new Artikkeli(1, "nimi", 0, "linkki", "tekija", "julkaisuLehti", 0, 0, "sivut", LocalDate.MAX, true, "aika");
        artikkeliDao.saveOrUpdate(artikkeli);
        artikkeliDao.saveOrUpdate(artikkeli);
        assertTrue(artikkeliDao.findAll().size() == 1);
    }

    @Test
    public void deletePoistaaArtikkelin() throws Throwable {
        lisaaDataa();
        artikkeliDao.delete(2);
        assertTrue(artikkeliDao.findAll().size() == 2);

    }

    @Test
    public void deletePoistaaOikeanArtikkelin() throws Throwable {
        lisaaDataa();
        artikkeliDao.delete(2);
        assertTrue(artikkeliDao.findOne(2) == null);
    }

    @Test
    public void updateInformationPaivittaaTiedot() throws Throwable {
        Artikkeli artikkeli = new Artikkeli(1, "nimi", 0, "linkki", "tekija", "julkaisuLehti", 0, 0, "sivut", LocalDate.MAX, true, "aika");
        artikkeliDao.saveOrUpdate(artikkeli);
        artikkeli.setNimi("uusiNimi");
        artikkeli.setPituus(6);
        artikkeli.setLinkki("jtn");
        artikkeli.setTekija("mina");
        artikkeli.setJulkaisuLehti("lehtinen");
        artikkeli.setJulkaistu(3);
        artikkeli.setNumero(4);
        artikkeli.setSivut("19");
        artikkeli.setPaivamaara(LocalDate.MIN);
        artikkeli.setLuettu(false);
        artikkeli.setLuettuAika("jotain");
        artikkeli.setId(1);
        artikkeliDao.updateInformation(artikkeli);
        Artikkeli artikkeliNoudettu = artikkeliDao.findOne(1);
        assertTrue(artikkeliNoudettu.getNimi().equals("uusiNimi"));
        assertTrue(artikkeliNoudettu.getPituus() == 6);
        assertTrue(artikkeliNoudettu.getLinkki().equals("jtn"));
        assertTrue(artikkeliNoudettu.getTekija().equals("mina"));
        assertTrue(artikkeliNoudettu.getJulkaisuLehti().equals("lehtinen"));
        assertTrue(artikkeliNoudettu.getJulkaistu() == 3);
        assertTrue(artikkeliNoudettu.getNumero() == 4);
        assertTrue(artikkeliNoudettu.getSivut().equals("19"));
        assertTrue(artikkeliNoudettu.getLuettuString().equals("Lukematon"));
        assertTrue(artikkeliNoudettu.getLuettuAika().equals("jotain"));
    }

    @Test
    public void updateInformationEiLisaaUuttaArtikkelia() throws Throwable {
        Artikkeli artikkeli = new Artikkeli(1, "nimi", 0, "linkki", "tekija", "julkaisuLehti", 0, 0, "sivut", LocalDate.MAX, true, "aika");
        artikkeliDao.saveOrUpdate(artikkeli);
        artikkeli.setNimi("uusiNimi");
        assertTrue(artikkeliDao.findAll().size() == 1);

    }

    @After
    public void tearDown() throws Throwable {

    }

}

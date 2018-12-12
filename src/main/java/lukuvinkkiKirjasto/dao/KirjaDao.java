package lukuvinkkiKirjasto.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import lukuvinkkiKirjasto.database.Database;

import lukuvinkkiKirjasto.domain.Kirja;

public class KirjaDao implements Dao<Kirja, String> {
    
    private Database database;
    
    public KirjaDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Kirja findOne(String key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kirja where ISBN = ?");
            stmt.setObject(1, key);
            ResultSet rs = stmt.executeQuery();
            
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }
            Kirja kirja = uusiKirja(rs);
            sulkija(stmt, rs, conn);
            return kirja;
        }
        
    }
    
    @Override
    public List<Kirja> findAll() throws SQLException {
        List<Kirja> kirjat = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kirja");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                kirjat.add(uusiKirja(rs));
            }
            sulkija(stmt, rs, conn);
            return kirjat;
        }
        
    }
    
    public Kirja updateInformation(String ISBN, Kirja kirja) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Kirja set ISBN = ?, genre = ?, nimi = ?, pituus = ?, linkki = ?, tekija = ?, julkaisuVuosi = ?,"
                    + " paivamaara = ?, luettu = ?, luettuAika = ? where ISBN = ?");
            
            stmt.setString(1, kirja.getISBN());
            stmt.setString(2, kirja.getGenre());
            stmt.setString(3, kirja.getNimi());
            stmt.setInt(4, kirja.getPituus());
            stmt.setString(5, kirja.getLinkki());
            stmt.setString(6, kirja.getTekija());
            stmt.setInt(7, kirja.getJulkaistu());
            stmt.setDate(8, Date.valueOf(kirja.getPaivamaara()));
            stmt.setBoolean(9, kirja.isLuettu());
            stmt.setString(10, kirja.getLuettuAika());
            stmt.setString(11, ISBN);
            stmt.executeUpdate();
            
            sulkija(stmt, null, conn);
            
        }
        return findOne(ISBN);
    }
    
    @Override
    public Kirja saveOrUpdate(Kirja kirja) throws SQLException {
        if (findOne(kirja.getISBN()) == null) {
            try (Connection conn = database.getConnection()) {
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Kirja "
                        + "(ISBN, genre, nimi, pituus, linkki, tekija, julkaisuVuosi,"
                        + " paivamaara, luettu, luettuAika) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, kirja.getISBN());
                statement.setString(2, kirja.getGenre());
                statement.setString(3, kirja.getNimi());
                statement.setInt(4, kirja.getPituus());
                statement.setString(5, kirja.getLinkki());
                statement.setString(6, kirja.getTekija());
                statement.setInt(7, kirja.getJulkaistu());
                statement.setDate(8, Date.valueOf(kirja.getPaivamaara()));
                statement.setBoolean(9, kirja.isLuettu());
                statement.setString(10, kirja.getLuettuAika());
                statement.executeUpdate();
                
                sulkija(statement, null, conn);
            }
        }
        return findOne(kirja.getISBN());
    }
    
    @Override
    public void delete(String key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement statement = conn.prepareStatement("DELETE FROM Kirja WHERE ISBN = ?");
        
        statement.setString(1, key);
        statement.executeUpdate();
        
        sulkija(statement, null, conn);
    }
    
    public List<Kirja> kirjatTageille(int tagiId) throws SQLException {
        
        String kysely = "SELECT Kirja.ISBN, Kirja.genre, Kirja.nimi, "
                + "Kirja.pituus, Kirja.linkki, Kirja.tekija, "
                + "Kirja.julkaisuVuosi, "
                + "Kirja.paivamaara, Kirja.luettu, Kirja.luettuAika "
                + "FROM Kirja, KirjaTagi\n WHERE kirja.ISBN = "
                + "KirjaTagi.kirja_ISBN AND KirjaTagi.tagi_id = ?\n";
        
        List<Kirja> kirjat = new ArrayList<>();
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(kysely);
            stmt.setInt(1, tagiId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                kirjat.add(uusiKirja(rs));
            }
        }
        return kirjat;
    }
    
    public Kirja uusiKirja(ResultSet rs) throws SQLException {
        return new Kirja(rs.getString("ISBN"), rs.getString("genre"),
                rs.getString("nimi"), rs.getInt("pituus"), rs.getString("linkki"),
                rs.getString("tekija"), rs.getInt("julkaisuVuosi"),
                rs.getDate("paivamaara").toLocalDate(),
                rs.getBoolean("luettu"), rs.getString("luettuAika"));
    }
    
    public void sulkija(Statement stmt, ResultSet rs, Connection conn) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        if (rs != null) {
            rs.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}

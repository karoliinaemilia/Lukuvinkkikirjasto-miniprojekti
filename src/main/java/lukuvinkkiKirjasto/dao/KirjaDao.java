package lukuvinkkiKirjasto.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import lukuvinkkiKirjasto.database.Database;

import lukuvinkkiKirjasto.domain.Kirja;

public class KirjaDao implements Dao<Kirja, Integer> {
    
    private Database database;
    
    public KirjaDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Kirja findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Kirja where id = ?");
            statement.setObject(1, key);
            ResultSet rs = statement.executeQuery();
            
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
                
            }
            Kirja kirja = new Kirja(rs.getInt("id"), rs.getString("genre"),
                    rs.getString("nimi"), rs.getInt("pituus"),
                    rs.getString("linkki"), rs.getString("tekija"), rs.getInt("julkaisuVuosi"),
                    rs.getDate("paivamaara").toLocalDate());
            
            statement.close();
            rs.close();
            conn.close();
            return kirja;
        }
        
    }
    
    @Override
    public List<Kirja> findAll() throws SQLException {
        List<Kirja> kirjat = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Kirja");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                kirjat.add(new Kirja(rs.getInt("id"), rs.getString("genre"),
                        rs.getString("nimi"), rs.getInt("pituus"),
                        rs.getString("linkki"), rs.getString("tekija"), rs.getInt("julkaisuVuosi"),
                        rs.getDate("paivamaara").toLocalDate()));
            }
            statement.close();
            rs.close();
            conn.close();
            return kirjat;
        }
        
    }
    
    @Override
    public Kirja saveOrUpdate(Kirja kirja) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Kirja (genre),(nimi),(pituus),(linkki),(tekija),(paivamaara) VALUES (?),(?),(?),(?),(?),(?)");
            statement.setString(1, kirja.getGenre());
            statement.setString(2, kirja.getNimi());
            statement.setInt(3, kirja.getPituus());
            statement.setString(4, kirja.getLinkki());
            statement.setString(5, kirja.getTekija());
            statement.setDate(6, Date.valueOf(kirja.getPaivamaara()));
            statement.executeUpdate();
        }
        return findOne(kirja.getId());
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Kirja WHERE id = ?");
            
            statement.setInt(1, key);
            statement.executeUpdate();
            statement.close();
            conn.close();
        }
    }
    
}

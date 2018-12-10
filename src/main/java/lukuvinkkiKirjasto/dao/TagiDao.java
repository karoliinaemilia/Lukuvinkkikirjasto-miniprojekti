
package lukuvinkkiKirjasto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.domain.Tagi;


public class TagiDao implements Dao<Tagi, Integer>{
     private Database database;

    public TagiDao(Database database) {
        this.database = database;
    }

    @Override
    public Tagi findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Tagi where id = ?");
            statement.setObject(1, key);
            ResultSet rs = statement.executeQuery();

            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;

            }
            Tagi tagi = new Tagi(rs.getInt("id"),
                    rs.getString("nimi"));

            statement.close();
            rs.close();
            conn.close();
            return tagi;
        }

    }

    @Override
    public List<Tagi> findAll() throws SQLException {
        List<Tagi> tagit = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Tagi");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tagit.add(new Tagi(rs.getInt("id"),
                        rs.getString("nimi")));
            }
            statement.close();
            rs.close();
            conn.close();
            return tagit;
        }

    }

    public Tagi updateInformation(Integer id, Tagi tagi) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE Tagi set nimi = ? where id = ?");

           
            statement.setString(1, tagi.getNimi());
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
            conn.close();
         
        }
        return findOne(id);
    }

    @Override
    public Tagi saveOrUpdate(Tagi tagi) throws SQLException {
        if (findOne(tagi.getId()) == null) {
            Connection conn = database.getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO Tagi"
                        + "(nimi)"
                        + "VALUES (?)");
            
                statement.setString(1, tagi.getNimi());
                
                statement.executeUpdate();
                statement.close();
                conn.close();
            
        }
        return findOne(tagi.getId());
    }
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement statement = conn.prepareStatement("DELETE FROM Tagi WHERE id = ?");

        statement.setInt(1, key);
        statement.executeUpdate();
        statement.close();
        conn.close();

    }
    
    public List<Tagi> tagitArtikkelille(Integer artikkeliId) throws SQLException{
        String kysely = "SELECT Tagi.id, Tagi.nimi FROM Tagi, ArtikkeliTagi\n"
                + "              WHERE tagi.id = ArtikkeliTagi.tagi_id "
                + "                  AND ArtikkeliTagi.artikkeli_id = ?\n";
        
         List<Tagi> tagit = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(kysely);
            stmt.setInt(1, artikkeliId);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                tagit.add(new Tagi(result.getInt("id"), result.getString("nimi")));
            }
        }   
     

            
        return tagit;
        
        
    }
    
     public List<Tagi> tagitKirjoille(String kirjaId) throws SQLException{
        String kysely = "SELECT Tagi.id, Tagi.nimi FROM Tagi, KirjaTagi\n"
                + "              WHERE tagi.id = KirjaTagi.tagi_id "
                + "                  AND KirjaTagi.kirja_ISBN = ?\n";
        
         List<Tagi> tagit = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(kysely);
            stmt.setString(1, kirjaId);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                tagit.add(new Tagi(result.getInt("id"), result.getString("nimi")));
            }
        }   
     

            
        return tagit;
        
        
    }

   
}

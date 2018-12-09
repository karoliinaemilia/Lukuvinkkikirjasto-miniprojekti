/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkkiKirjasto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.domain.ArtikkeliTagi;

/**
 *
 * @author ihqsanna
 */
public class ArtikkeliTagiDao implements Dao<ArtikkeliTagi, Integer> {
    private Database database;
    
    public ArtikkeliTagiDao(Database db) {
        this.database = db;
    }
    
    @Override
    public ArtikkeliTagi findOne(Integer key) throws SQLException {
        Connection con  = database.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM ArtikkeliTagi"
                + " WHERE artikkeli_id = ?");
        
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
        ArtikkeliTagi at = new ArtikkeliTagi(rs.getInt("artikkeli_id"), rs.getInt("tagi_id"));
        stmt.close();
        rs.close();
        con.close();
        return at;
        
    }

    @Override
    public List<ArtikkeliTagi> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ArtikkeliTagi");
        List<ArtikkeliTagi> artikkeliTagit = new ArrayList();
        
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        while (rs.next()) {
        ArtikkeliTagi at = new ArtikkeliTagi(rs.getInt("artikkei_id"), rs.getInt("tagi_id"));
        artikkeliTagit.add(at);
        }
        
        stmt.close();
        rs.close();

        conn.close();

        return artikkeliTagit;
    }

    @Override
    public ArtikkeliTagi saveOrUpdate(ArtikkeliTagi object) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO ArtikkeliTagi" + "(artikkeli_id, tagi_id)" + "VALUES (?, ?)");
        stmt.setInt(1, object.getArtikkeliId());
        stmt.setInt(2, object.getTagiId());
        stmt.executeUpdate();
        stmt.close();
        
        stmt = conn.prepareStatement("SELECT * FROM ArtikkeliTagi WHERE artikkeli_id = ? AND tagi_id = ?");
        stmt.setInt(1, object.getArtikkeliId());
        stmt.setInt(2, object.getTagiId());
       
        
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        ArtikkeliTagi arta = new ArtikkeliTagi(rs.getInt("artikkeli_id"), rs.getInt("tagi_id"));
        
        stmt.close();
        rs.close();
        conn.close();
        return arta;
    }

    @Override
    public void delete(Integer key) throws SQLException {
       
    }
    
}

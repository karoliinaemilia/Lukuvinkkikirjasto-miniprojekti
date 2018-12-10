
package lukuvinkkiKirjasto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lukuvinkkiKirjasto.database.Database;

import lukuvinkkiKirjasto.domain.KirjaTagi;


public class KirjaTagiDao implements Dao<KirjaTagi, String>{
    private Database database;

    public KirjaTagiDao(Database database) {
        this.database = database;
    }

    @Override
    public KirjaTagi findOne(String key) throws SQLException {
        Connection con  = database.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM KirjaTagi"
                + " WHERE artikkeli_ISBN = ?");
        
        stmt.setString(1, key);
        
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
        KirjaTagi at = new KirjaTagi(rs.getString("kirja_ISBN"), rs.getInt("tagi_id"));
        stmt.close();
        rs.close();
        con.close();
        return at;
    }

    @Override
    public List<KirjaTagi> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM KirjaTagi");
        List<KirjaTagi> kirjaTagit = new ArrayList();
        
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        while (rs.next()) {
        KirjaTagi at = new KirjaTagi(rs.getString("kirja_ISBN"), rs.getInt("tagi_id"));
        kirjaTagit.add(at);
        }
        
        stmt.close();
        rs.close();

        conn.close();

        return kirjaTagit;
    }

    @Override
    public KirjaTagi saveOrUpdate(KirjaTagi object) throws SQLException {
         Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO KirjaTagi" + "(kirja_ISBN, tagi_id)" + "VALUES (?, ?)");
        stmt.setString(1, object.getKirjaId());
        stmt.setInt(2, object.getTagiId());
        stmt.executeUpdate();
        stmt.close();
        
        stmt = conn.prepareStatement("SELECT * FROM KirjaTagi WHERE kirja_ISBN = ? AND tagi_id = ?");
        stmt.setString(1, object.getKirjaId());
        stmt.setInt(2, object.getTagiId());
       
        
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        KirjaTagi kirja = new KirjaTagi(rs.getString("kirja_ISBN"), rs.getInt("tagi_id"));
        
        stmt.close();
        rs.close();
        conn.close();
        return kirja;
    }

    @Override
    public void delete(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

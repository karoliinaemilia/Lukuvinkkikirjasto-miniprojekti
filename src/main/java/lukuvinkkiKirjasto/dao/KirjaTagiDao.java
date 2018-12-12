package lukuvinkkiKirjasto.dao;

import java.sql.*;
import java.util.*;
import lukuvinkkiKirjasto.database.Database;

import lukuvinkkiKirjasto.domain.KirjaTagi;

public class KirjaTagiDao implements Dao<KirjaTagi, String> {

    private Database database;

    public KirjaTagiDao(Database database) {
        this.database = database;
    }

    @Override
    public KirjaTagi findOne(String key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM KirjaTagi"
                + " WHERE artikkeli_ISBN = ?");
        stmt.setString(1, key);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        KirjaTagi kirjaTagi = new KirjaTagi(rs.getString("kirja_ISBN"), rs.getInt("tagi_id"));
        sulkija(stmt, rs, conn);
        return kirjaTagi;
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

        sulkija(stmt, rs, conn);

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

        sulkija(stmt, rs, conn);
        return kirja;
    }

    @Override
    public void delete(String key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM KirjaTagi WHERE kirja_ISBN = ?");

        stmt.setString(1, key);
        stmt.executeUpdate();

        sulkija(stmt, null, conn);
    }

    public void deleteTagi(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM KirjaTagi WHERE tagi_id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        sulkija(stmt, null, conn);
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

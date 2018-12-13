package lukuvinkkiKirjasto.dao;

import java.sql.*;
import java.util.*;
import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.domain.Tagi;

public class TagiDao implements Dao<Tagi, Integer> {

    private Database database;

    public TagiDao(Database database) {
        this.database = database;
    }

    @Override
    public Tagi findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tagi where id = ?");
            stmt.setObject(1, key);
            ResultSet rs = stmt.executeQuery();

            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;

            }
            Tagi tagi = new Tagi(rs.getInt("id"),
                    rs.getString("nimi"));

            sulkija(stmt, rs, conn);
            return tagi;
        }

    }

    @Override
    public List<Tagi> findAll() throws SQLException {
        List<Tagi> tagit = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tagi");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tagit.add(new Tagi(rs.getInt("id"),
                        rs.getString("nimi")));
            }
            sulkija(stmt, rs, conn);
            return tagit;
        }

    }

    @Override
    public Tagi saveOrUpdate(Tagi tagi) throws SQLException {
        if (findOne(tagi.getId()) == null) {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tagi"
                    + "(nimi)"
                    + "VALUES (?)");

            stmt.setString(1, tagi.getNimi());

            stmt.executeUpdate();
            sulkija(stmt, null, conn);

        }
        return findOne(tagi.getId());
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Tagi WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();
        sulkija(stmt, null, conn);

    }

    public List<Tagi> tagitArtikkelille(Integer artikkeliId) throws SQLException {
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

    public List<Tagi> tagitKirjoille(String kirjaId) throws SQLException {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkkiKirjasto.dao;

import java.sql.*;
import java.util.*;
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
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ArtikkeliTagi"
                    + " WHERE artikkeli_id = ?");
            stmt.setInt(1, key);

            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }
            ArtikkeliTagi artikkeliTagi = new ArtikkeliTagi(rs.getInt("artikkeli_id"), rs.getInt("tagi_id"));

            sulkija(stmt, rs, conn);

            return artikkeliTagi;
        }

    }

    @Override
    public List<ArtikkeliTagi> findAll() throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ArtikkeliTagi");
            List<ArtikkeliTagi> artikkeliTagit = new ArrayList();

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            while (rs.next()) {
                artikkeliTagit.add(new ArtikkeliTagi(rs.getInt("artikkeli_id"), rs.getInt("tagi_id")));
            }
            sulkija(stmt, rs, conn);

            return artikkeliTagit;
        }
    }

    @Override
    public ArtikkeliTagi saveOrUpdate(ArtikkeliTagi object) throws SQLException {
        try (Connection conn = database.getConnection()) {
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

            ArtikkeliTagi artikkeliTagi = new ArtikkeliTagi(rs.getInt("artikkeli_id"), rs.getInt("tagi_id"));

            sulkija(stmt, rs, conn);
            return artikkeliTagi;
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM ArtikkeliTagi WHERE artikkeli_id = ?");

            stmt.setInt(1, key);
            stmt.executeUpdate();

            sulkija(stmt, null, conn);
        }
    }

    public void deleteTagi(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM ArtikkeliTagi WHERE tagi_id = ?");

            stmt.setInt(1, key);
            stmt.executeUpdate();

            sulkija(stmt, null, conn);
        }
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

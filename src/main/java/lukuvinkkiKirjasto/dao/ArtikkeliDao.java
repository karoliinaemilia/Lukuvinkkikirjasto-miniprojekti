package lukuvinkkiKirjasto.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.domain.Artikkeli;

public class ArtikkeliDao implements Dao<Artikkeli, Integer> {

    private Database database;

    public ArtikkeliDao(Database database) {
        this.database = database;
    }

    @Override
    public Artikkeli findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Artikkeli where id = ?");
            stmt.setObject(1, key);
            ResultSet rs = stmt.executeQuery();

            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }

            Artikkeli artikkeli = uusiArtikkeli(rs);
            sulkija(stmt, rs, conn);
            return artikkeli;
        }
    }

    @Override
    public List<Artikkeli> findAll() throws SQLException {
        List<Artikkeli> artikkelit = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Artikkeli");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                artikkelit.add(uusiArtikkeli(rs));
            }
            sulkija(stmt, rs, conn);
            return artikkelit;
        }

    }

    @Override
    public Artikkeli saveOrUpdate(Artikkeli artikkeli) throws SQLException {

        if (findOne(artikkeli.getId()) == null) {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Artikkeli "
                    + "(nimi, pituus, linkki, tekija, julkaisuLehti, julkaisuVuosi, "
                    + "numero, sivut, paivamaara, luettu, luettuAika) VALUES (?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, artikkeli.getNimi());
            stmt.setInt(2, artikkeli.getPituus());
            stmt.setString(3, artikkeli.getLinkki());
            stmt.setString(4, artikkeli.getTekija());
            stmt.setString(5, artikkeli.getJulkaisuLehti());
            stmt.setInt(6, artikkeli.getJulkaistu());
            stmt.setInt(7, artikkeli.getNumero());
            stmt.setString(8, artikkeli.getSivut());
            stmt.setDate(9, Date.valueOf(artikkeli.getPaivamaara()));
            stmt.setBoolean(10, artikkeli.isLuettu());
            stmt.setString(11, artikkeli.getLuettuAika());
            stmt.executeUpdate();

            sulkija(stmt, null, conn);
        }
        return findOne(artikkeli.getId());
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Artikkeli WHERE id = ?");
            stmt.setInt(1, key);
            stmt.executeUpdate();

            sulkija(stmt, null, conn);
        }
    }

    public void updateInformation(Artikkeli artikkeli) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Artikkeli "
                    + "SET nimi = ?, pituus = ?, linkki = ?, tekija = ?, julkaisuLehti = ?, "
                    + "julkaisuVuosi = ?, numero = ?, sivut = ?, paivamaara = ?, "
                    + "luettu = ?, luettuAika = ? WHERE id = ?");
            stmt.setString(1, artikkeli.getNimi());
            stmt.setInt(2, artikkeli.getPituus());
            stmt.setString(3, artikkeli.getLinkki());
            stmt.setString(4, artikkeli.getTekija());
            stmt.setString(5, artikkeli.getJulkaisuLehti());
            stmt.setInt(6, artikkeli.getJulkaistu());
            stmt.setInt(7, artikkeli.getNumero());
            stmt.setString(8, artikkeli.getSivut());
            stmt.setDate(9, Date.valueOf(artikkeli.getPaivamaara()));
            stmt.setBoolean(10, artikkeli.isLuettu());
            stmt.setString(11, artikkeli.getLuettuAika());
            stmt.setInt(12, artikkeli.getId());
            stmt.executeUpdate();

            sulkija(stmt, null, conn);
        }

    }

    public List<Artikkeli> artikkelitTageille(int tagiId) throws SQLException {

        String kysely = "SELECT Artikkeli.id, Artikkeli.nimi, Artikkeli.pituus, "
                + "Artikkeli.linkki, Artikkeli.tekija, Artikkeli.julkaisuLehti, "
                + "Artikkeli.julkaisuVuosi, Artikkeli.numero, Artikkeli.sivut, "
                + "Artikkeli.paivamaara, Artikkeli.luettu, Artikkeli.luettuAika "
                + "FROM Artikkeli, ArtikkeliTagi\n WHERE artikkeli.id = "
                + "ArtikkeliTagi.artikkeli_id AND ArtikkeliTagi.tagi_id = ?\n";

        List<Artikkeli> artikkelit = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(kysely);
            stmt.setInt(1, tagiId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                artikkelit.add(uusiArtikkeli(rs));
            }
        }
        return artikkelit;
    }

    public static Artikkeli uusiArtikkeli(ResultSet rs) throws SQLException {
        Artikkeli artikkeli = new Artikkeli(rs.getInt("id"), rs.getString("nimi"), rs.getInt("pituus"),
                rs.getString("linkki"), rs.getString("tekija"),
                rs.getString("julkaisuLehti"), rs.getInt("julkaisuVuosi"), rs.getInt("numero"), rs.getString("sivut"),
                rs.getDate("paivamaara").toLocalDate(), rs.getBoolean("luettu"), rs.getString("luettuAika"));

        return artikkeli;
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

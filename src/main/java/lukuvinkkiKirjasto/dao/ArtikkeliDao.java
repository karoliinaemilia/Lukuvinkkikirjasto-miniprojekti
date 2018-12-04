package lukuvinkkiKirjasto.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.domain.Artikkeli;

public class ArtikkeliDao implements Dao<Artikkeli, Integer> {

    private Database database;

    public ArtikkeliDao(Database database) {
        this.database = database;
    }

    @Override
    public Artikkeli findOne(Integer key) throws SQLException {
        Artikkeli artikkeli; 
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Artikkeli where id = ?");
            statement.setObject(1, key);
            ResultSet rs = statement.executeQuery();

            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;

            }
            artikkeli = new Artikkeli(rs.getInt("id"), rs.getString("nimi"), rs.getInt("pituus"),
                    rs.getString("linkki"), rs.getString("tekija"),
                    rs.getString("julkaisuLehti"), rs.getInt("julkaisuVuosi"), rs.getInt("numero"), rs.getString("sivut"),
                    rs.getDate("paivamaara").toLocalDate(), rs.getBoolean("luettu"), rs.getString("luettuAika"));

            statement.close();
            rs.close();
        }
        return artikkeli; 

    }

    @Override
    public List<Artikkeli> findAll() throws SQLException {
        List<Artikkeli> artikkelit = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Artikkeli");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                artikkelit.add(new Artikkeli(rs.getInt("id"), rs.getString("nimi"), rs.getInt("pituus"),
                        rs.getString("linkki"), rs.getString("tekija"),
                        rs.getString("julkaisuLehti"), rs.getInt("julkaisuVuosi"), rs.getInt("numero"), rs.getString("sivut"),
                        rs.getDate("paivamaara").toLocalDate(), rs.getBoolean("luettu"), rs.getString("luettuAika")));
            }
            statement.close();
            rs.close();
        }
        return artikkelit;

    }

    @Override
    public Artikkeli saveOrUpdate(Integer avain, Artikkeli artikkeli) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Artikkeli "
                + "(nimi, pituus, linkki, tekija, julkaisuLehti, julkaisuVuosi, "
                + "numero, sivut, paivamaara, luettu, luettuAika) VALUES (?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, artikkeli.getNimi());
        statement.setInt(2, artikkeli.getPituus());
        statement.setString(3, artikkeli.getLinkki());
        statement.setString(4, artikkeli.getTekija());
        statement.setString(5, artikkeli.getJulkaisuLehti());
        statement.setInt(6, artikkeli.getJulkaistu());
        statement.setInt(7, artikkeli.getNumero());
        statement.setString(8, artikkeli.getSivut());
        statement.setDate(9, Date.valueOf(artikkeli.getPaivamaara()));
        statement.setBoolean(10, artikkeli.isLuettu());
        statement.setString(11, artikkeli.getLuettuAika());
        statement.executeUpdate();

        return findOne(artikkeli.getId());
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Artikkeli WHERE id = ?");
            statement.setInt(1, key);
            statement.executeUpdate();
            statement.close();
        }
    }

    public void updateInformation(Artikkeli artikkeli) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE Artikkeli "
                    + "SET nimi = ?, pituus = ?, linkki = ?, tekija = ?, julkaisuLehti = ?, "
                    + "julkaisuVuosi = ?, numero = ?, sivut = ?, paivamaara = ?, "
                    + "luettu = ?, luettuAika = ? WHERE id = ?");
            statement.setString(1, artikkeli.getNimi());
            statement.setInt(2, artikkeli.getPituus());
            statement.setString(3, artikkeli.getLinkki());
            statement.setString(4, artikkeli.getTekija());
            statement.setString(5, artikkeli.getJulkaisuLehti());
            statement.setInt(6, artikkeli.getJulkaistu());
            statement.setInt(7, artikkeli.getNumero());
            statement.setString(8, artikkeli.getSivut());
            statement.setDate(9, Date.valueOf(artikkeli.getPaivamaara()));
            statement.setBoolean(10, artikkeli.isLuettu());
            statement.setString(11, artikkeli.getLuettuAika());
            statement.setInt(12, artikkeli.getId());
            statement.executeUpdate();
        }

    }

}

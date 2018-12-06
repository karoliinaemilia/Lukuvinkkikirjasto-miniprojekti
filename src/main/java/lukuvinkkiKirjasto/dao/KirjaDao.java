package lukuvinkkiKirjasto.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Kirja where ISBN = ?");
            statement.setObject(1, key);
            ResultSet rs = statement.executeQuery();

            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;

            }
            Kirja kirja = new Kirja(rs.getString("ISBN"), rs.getString("genre"),
                    rs.getString("nimi"), rs.getInt("pituus"),
                    rs.getString("linkki"), rs.getString("tekija"), rs.getInt("julkaisuVuosi"),
                    rs.getDate("paivamaara").toLocalDate(), rs.getBoolean("luettu"), rs.getString("luettuAika"));

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
                kirjat.add(new Kirja(rs.getString("ISBN"), rs.getString("genre"),
                        rs.getString("nimi"), rs.getInt("pituus"),
                        rs.getString("linkki"), rs.getString("tekija"), rs.getInt("julkaisuVuosi"),
                        rs.getDate("paivamaara").toLocalDate(), rs.getBoolean("luettu"), rs.getString("luettuAika")));
            }
            statement.close();
            rs.close();
            conn.close();
            return kirjat;
        }

    }

    public Kirja updateInformation(String ISBN, Kirja kirja) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE Kirja set ISBN = ?, genre = ?, nimi = ?, pituus = ?, linkki = ?, tekija = ?, julkaisuVuosi = ?,"
                    + " paivamaara = ? where ISBN = ?");

            statement.setString(1, kirja.getISBN());
            statement.setString(2, kirja.getGenre());
            statement.setString(3, kirja.getNimi());
            statement.setInt(4, kirja.getPituus());
            statement.setString(5, kirja.getLinkki());
            statement.setString(6, kirja.getTekija());
            statement.setInt(7, kirja.getJulkaistu());
            statement.setDate(8, Date.valueOf(kirja.getPaivamaara()));
            statement.setString(9, ISBN);

            statement.executeUpdate();
            statement.close();
            conn.close();
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
                statement.close();
                conn.close();
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
        statement.close();
        conn.close();

    }

}

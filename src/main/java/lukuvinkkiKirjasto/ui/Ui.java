package lukuvinkkiKirjasto.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lukuvinkkiKirjasto.dao.KirjaDao;

import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.domain.Kirja;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Ui {

    public static Database db = new Database("jdbc:sqlite:LukuvinkkiKirjasto.db");

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        Class.forName("org.sqlite.JDBC");
        Class.forName("org.postgresql.Driver");

        Connection conn = db.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT 1");
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            System.out.println("Yhteys onnistui");
        } else {
            System.out.println("Yhteys epäonnistui");
        }

        KirjaDao kirjaDao = new KirjaDao(db);

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            try {
                map.put("kirjat", kirjaDao.findAll());
            } catch (SQLException ex) {
                Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new ModelAndView(map, "aloitus");
        }, new ThymeleafTemplateEngine());

        Spark.get("/kirjat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kirjat", kirjaDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("/kirjat", (req, res) -> {
            List<Kirja> kirjat = kirjaDao.findAll();

            for (int j = 0; j < kirjat.size(); j++) {
                if (kirjat.get(j).getISBN().equals(Integer.parseInt(req.queryParams("ISBN")))) {
                    res.redirect("/kirjat");
                    return "";
                }
            }

            boolean lue = false;
            try {
                lue = req.queryParams("luettu").equals("true");
            } catch (Exception e) {
                System.out.println(e);
            }

            kirjaDao.saveOrUpdate(
                    new Kirja(
                            Integer.parseInt(req.queryParams("ISBN")),
                            req.queryParams("genre"),
                            req.queryParams("nimi"),
                            Integer.parseInt(req.queryParams("pituus")),
                            req.queryParams("linkki"),
                            req.queryParams("tekija"),
                            Integer.parseInt(req.queryParams("julkaisuVuosi")),
                            LocalDate.now(),
                            lue
                    )
            );
            res.redirect("/kirjat");
            return "";
        });
        Spark.post("/kirjat/:ISBN", (req, res) -> {
            System.out.println("jtn");
            System.out.println(req.queryParams(":ISBN"));
            kirjaDao.delete(Integer.parseInt(req.queryParams(":ISBN")));

            res.redirect("/kirjat");
            return "";
        });

    }

    public static void setDatabase(Database db) {
        Ui.db = db;
    }

}

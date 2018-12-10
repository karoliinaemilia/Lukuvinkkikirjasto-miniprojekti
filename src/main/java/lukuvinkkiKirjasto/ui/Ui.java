package lukuvinkkiKirjasto.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.DispatcherType;
import lukuvinkkiKirjasto.dao.KirjaDao;

import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.domain.Kirja;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import org.apache.commons.validator.routines.*;
import lukuvinkkiKirjasto.dao.ArtikkeliDao;
import lukuvinkkiKirjasto.dao.ArtikkeliTagiDao;
import lukuvinkkiKirjasto.dao.KirjaTagiDao;
import lukuvinkkiKirjasto.dao.TagiDao;
import lukuvinkkiKirjasto.domain.Artikkeli;
import lukuvinkkiKirjasto.domain.ArtikkeliTagi;
import lukuvinkkiKirjasto.domain.KirjaTagi;
import lukuvinkkiKirjasto.domain.Tagi;

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

        ArtikkeliDao artikkeliDao = new ArtikkeliDao(db);
        TagiDao tagiDao = new TagiDao(db);
        ArtikkeliTagiDao artikkeliTagiDao = new ArtikkeliTagiDao(db);
        KirjaTagiDao kirjaTagiDao = new KirjaTagiDao(db);

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
            map.put("tagit", tagiDao.findAll());
            return new ModelAndView(map, "kirjat");
        }, new ThymeleafTemplateEngine());

        Spark.get("/artikkelit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("artikkelit", artikkeliDao.findAll());
            map.put("tagit", tagiDao.findAll());
            return new ModelAndView(map, "artikkelit");
        }, new ThymeleafTemplateEngine());

        Spark.get("/tagit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("tagit", tagiDao.findAll());
            return new ModelAndView(map, "tagit");
        }, new ThymeleafTemplateEngine());

        Spark.get("/kirja/:ISBN", (req, res) -> {
            HashMap map = new HashMap();
            String tunnus = req.params(":ISBN");
            map.put("kirja", kirjaDao.findOne(tunnus));
            map.put("tagit", tagiDao.tagitKirjoille(tunnus));
            return new ModelAndView(map, "kirja");
        }, new ThymeleafTemplateEngine());

        Spark.get("/artikkeli/:id", (req, res) -> {
            HashMap map = new HashMap();
            int i = Integer.parseInt(req.params(":id"));
            try {
                map.put("artikkeli", artikkeliDao.findOne(i));
                map.put("tagit", tagiDao.tagitArtikkelille(i));
            } catch (SQLException ex) {
                Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new ModelAndView(map, "artikkeli");

        }, new ThymeleafTemplateEngine());

        Spark.post("/kirjat", (req, res) -> {

            List<Kirja> kirjat = kirjaDao.findAll();

            for (int j = 0; j < kirjat.size(); j++) {

                if (kirjat.get(j).getISBN().equals(req.queryParams("ISBN"))) {
                    res.redirect("/kirjat");
                    return "";
                }

            }
            String testi = req.queryParams("ISBN");
            ISBNValidator isbn = new ISBNValidator();

            try {
                if (!isbn.isValid(testi)) {
                    res.redirect("/kirjat");
                    return "";
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            String aika = "";
            if (Boolean.parseBoolean(req.queryParams("luettu"))) {
                aika = LocalDate.now().toString() + " "
                        + LocalDateTime.now().getHour() + ":"
                        + LocalDateTime.now().getMinute();

            }

            kirjaDao.saveOrUpdate(
                    new Kirja(
                            req.queryParams("ISBN"),
                            req.queryParams("genre"),
                            req.queryParams("nimi"),
                            Integer.parseInt(req.queryParams("pituus")),
                            req.queryParams("linkki"),
                            req.queryParams("tekija"),
                            Integer.parseInt(req.queryParams("julkaisuVuosi")),
                            LocalDate.now(),
                            Boolean.parseBoolean(req.queryParams("luettu")),
                            aika
                    )
            );

            res.redirect("/kirjat");
            return "";

        });

        Spark.post("/kirjat/:ISBN", (req, res) -> {

            String act = req.queryParams("nappi");

            if (act.equals("poista")) {
                kirjaDao.delete(req.params(":ISBN"));
                kirjaTagiDao.delete(req.params(":ISBN"));
            } else if (act.equals("Merkitse luetuksi")) {
                String uusiAika = LocalDate.now().toString() + " "
                        + LocalDateTime.now().getHour() + ":"
                        + LocalDateTime.now().getMinute();
                Kirja kirja = kirjaDao.findOne(req.params(":ISBN"));
                kirja.setLuettu(true);
                kirja.setLuettuAika(uusiAika);
                kirjaDao.updateInformation(req.params(":ISBN"), kirja);
            }

            res.redirect("/kirjat");
            return "";
        });

        Spark.post("/tagit", (req, res) -> {
            String nimi = req.queryParams("nimi");

            if (nimi.isEmpty()) {
                res.redirect("tagit");
                return "";
            }

            List<Tagi> tagit = tagiDao.findAll();
            for (int i = 0; i < tagit.size(); i++) {
                if (nimi.equals(tagit.get(i).getNimi())) {
                    res.redirect("/tagit");
                    return "";
                }
            }

            tagiDao.saveOrUpdate(new Tagi(null, nimi));

            res.redirect("/tagit");
            return "";
        });

        Spark.post("/artikkelit", (req, res) -> {

            List<Artikkeli> artikkel = artikkeliDao.findAll();

            for (int l = 0; l < artikkel.size(); l++) {

                if (artikkel.get(l).getNimi().equals(req.queryParams("nimi"))) {

                    res.redirect("/artikkelit");
                    return "";
                }

            }
            String aika = "";
            if (Boolean.parseBoolean(req.queryParams("luettu"))) {
                aika = LocalDate.now().toString() + " "
                        + LocalDateTime.now().getHour() + ":"
                        + LocalDateTime.now().getMinute();

            }

            artikkeliDao.saveOrUpdate(
                    new Artikkeli(
                            null,
                            req.queryParams("nimi"),
                            Integer.parseInt(req.queryParams("pituus")),
                            req.queryParams("linkki"),
                            req.queryParams("tekija"),
                            req.queryParams("julkaisuLehti"),
                            Integer.parseInt(req.queryParams("julkaisuVuosi")),
                            Integer.parseInt(req.queryParams("numero")),
                            req.queryParams("sivut"),
                            LocalDate.now(),
                            Boolean.parseBoolean(req.queryParams("luettu")),
                            aika
                    )
            );

            res.redirect("/artikkelit");
            return "";
        });
        
        Spark.post("/deletoi/:id", (req, res) -> {
            int i = Integer.parseInt(req.params(":id"));
            tagiDao.delete(i);
            artikkeliTagiDao.deleteTagi(i);
            kirjaTagiDao.deleteTagi(i);
            res.redirect("/tagit");
            return "";
        });

        Spark.post("/artikkelit/:id", (req, res) -> {
            System.out.println();
            String act = req.queryParams("nappi");

            if (act.equals("poista")) {
                artikkeliDao.delete(Integer.parseInt(req.params(":id")));
                artikkeliTagiDao.delete(Integer.parseInt(req.params(":id")));
            } else if (act.equals("Merkitse luetuksi")) {
                String uusiAika = LocalDate.now().toString() + " "
                        + LocalDateTime.now().getHour() + ":"
                        + LocalDateTime.now().getMinute();
                Artikkeli artikkeli = artikkeliDao.findOne(Integer.parseInt(req.params(":id")));
                artikkeli.setLuettu(true);
                artikkeli.setLuettuAika(uusiAika);
                artikkeliDao.updateInformation(artikkeli);
            }

            res.redirect("/artikkelit");
            return "";
        });

        Spark.post("/artikkelit/:artikkeliId", (req, res) -> {
            Artikkeli artikkeli = artikkeliDao.findOne(Integer.parseInt(req.params(":artikkeliId")));

            artikkeli.setLuettu(true);
            artikkeliDao.updateInformation(artikkeli);
            res.redirect("/artikkelit");
            return "";
        });
        Spark.post("/artikkeli/:id", (req, res) -> {

            Artikkeli artikkeli = artikkeliDao.findOne(Integer.parseInt(req.params(":id")));
            artikkeli.setNimi(req.queryParams("nimi"));
            artikkeli.setPituus(Integer.parseInt(req.queryParams("pituus")));
            artikkeli.setLinkki(req.queryParams("linkki"));
            artikkeli.setTekija(req.queryParams("tekija"));
            artikkeli.setJulkaisuLehti(req.queryParams("julkaisuLehti"));
            artikkeli.setJulkaistu(Integer.parseInt(req.queryParams("julkaisuVuosi")));
            artikkeli.setNumero(Integer.parseInt(req.queryParams("numero")));
            artikkeli.setSivut(req.queryParams("sivut"));

            artikkeliDao.updateInformation(artikkeli);
            res.redirect("/artikkeli/" + artikkeli.getId());
            return "";
        });

        Spark.post("/kirja/:id", (req, res) -> {
            Kirja kirja = kirjaDao.findOne(req.params(":id"));
            ISBNValidator isbn = new ISBNValidator();

            try {
                if (!isbn.isValid(req.queryParams("ISBN"))) {
                    res.redirect("/kirjat");
                    return "";
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            if (kirja != null) {
                kirjaDao.updateInformation(kirja.getISBN(),
                        new Kirja(
                                req.queryParams("ISBN"),
                                req.queryParams("genre"),
                                req.queryParams("nimi"),
                                Integer.parseInt(req.queryParams("pituus")),
                                req.queryParams("linkki"),
                                req.queryParams("tekija"),
                                Integer.parseInt(req.queryParams("julkaisuVuosi")),
                                LocalDate.now(),
                                kirja.isLuettu(),
                                kirja.getLuettuAika()
                        ));
            }

            res.redirect("/kirja/" + req.queryParams("ISBN"));
            return "";
        }
        );
        Spark.get("/kirjat/luetut", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Kirja> luetut = kirjaDao.findAll().stream().filter(k -> k.isLuettu() == true).collect(Collectors.toList());
            map.put("kirjat", luetut);
            map.put("tagit", tagiDao.findAll());
            return new ModelAndView(map, "kirjat");
        }, new ThymeleafTemplateEngine());

        Spark.get("/kirjat/lukemattomat", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Kirja> lukemattomat = kirjaDao.findAll().stream().filter(k -> k.isLuettu() == false).collect(Collectors.toList());
            map.put("kirjat", lukemattomat);
            map.put("tagit", tagiDao.findAll());

            return new ModelAndView(map, "kirjat");
        }, new ThymeleafTemplateEngine());

        Spark.get("/artikkelit/luetut", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Artikkeli> luetut = artikkeliDao.findAll().stream().filter(a -> a.isLuettu() == true).collect(Collectors.toList());
            map.put("artikkelit", luetut);
            map.put("tagit", tagiDao.findAll());
            return new ModelAndView(map, "artikkelit");
        }, new ThymeleafTemplateEngine());

        Spark.get("/artikkelit/lukemattomat", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Artikkeli> lukemattomat = artikkeliDao.findAll().stream().filter(a -> a.isLuettu() == false).collect(Collectors.toList());
            map.put("artikkelit", lukemattomat);
            map.put("tagit", tagiDao.findAll());
            return new ModelAndView(map, "artikkelit");
        }, new ThymeleafTemplateEngine());

        Spark.post("/lisaaminen", (req, res) -> {
           
            String t = req.queryParams("tagi");
            String a = req.queryParams("artikkeli");

            List<Tagi> tagit = tagiDao.findAll();
            List<Artikkeli> artikkelit = artikkeliDao.findAll();
            List<ArtikkeliTagi> at = artikkeliTagiDao.findAll();

            int tagi = -1;

            int k = 0;
            while (k < tagit.size()) {
                if (tagit.get(k).getNimi().equals(t)) {
                    tagi = tagit.get(k).getId();
                    break;
                }

                k++;
            }

            int artikkeli = -1;

            int p = 0;
            while (p < artikkelit.size()) {
                if (artikkelit.get(p).getNimi().equals(a)) {
                    artikkeli = artikkelit.get(p).getId();
                    break;
                }
                p++;
            }

            for (int i = 0; i < at.size(); i++) {
                if (at.get(i).getArtikkeliId().equals(artikkeli)
                        && at.get(i).getTagiId().equals(tagi)) {
                    res.redirect("/artikkelit");
                    return "";
                }
            }

            artikkeliTagiDao.saveOrUpdate(new ArtikkeliTagi(artikkeli, tagi));

            res.redirect("/artikkelit");
            return "";
        });
        
        Spark.post("/lisaaminenK", (req, res) -> {
            
            String t = req.queryParams("tagi");
            String k = req.queryParams("kirja");

            List<Tagi> tagit = tagiDao.findAll();
            List<Kirja> kirjat = kirjaDao.findAll();
            List<KirjaTagi> kt = kirjaTagiDao.findAll();
           
            int tagi = -1;

            int s = 0;
            while (s < tagit.size()) {
                if (tagit.get(s).getNimi().equals(t)) {
                    tagi = tagit.get(s).getId();
                    break;
                }

                s++;
            }

            String kirja = "";

            int p = 0;
            while (p < kirjat.size()) {
                if (kirjat.get(p).getNimi().equals(k)) {
                    kirja = kirjat.get(p).getISBN();
                    break;
                }
                p++;
            }
          
            for (int i = 0; i < kt.size(); i++) {
                if (kt.get(i).getKirjaId().equals(kirja)
                        && kt.get(i).getTagiId().equals(tagi)) {
                    res.redirect("/kirjat");
                    return "";
                }
            }
            
            kirjaTagiDao.saveOrUpdate(new KirjaTagi(kirja, tagi));

            res.redirect("/kirjat");
            return "";
        });
        
        Spark.get("/tagi/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer tagId = Integer.parseInt(req.params(":id"));
            map.put("tagi", tagiDao.findOne(tagId));
            map.put("artikkelit", artikkeliDao.artikkelitTageille(tagId));
            map.put("kirjat", kirjaDao.kirjatTageille(tagId));
            return new ModelAndView(map, "tagi");
        }, new ThymeleafTemplateEngine());

    }

    public static void setDatabase(Database db) {
        Ui.db = db;
    }

}

package lukuvinkkiKirjasto.ui;

import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.logging.*;
import lukuvinkkiKirjasto.dao.*;
import lukuvinkkiKirjasto.domain.*;
import org.apache.commons.validator.routines.*;
import spark.*;

import java.util.stream.Collectors;
import lukuvinkkiKirjasto.database.Database;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Ui {

    public static Database db = new Database("jdbc:sqlite:LukuvinkkiKirjasto.db");

    public static KirjaDao kirjaDao;
    public static ArtikkeliDao artikkeliDao;
    public static TagiDao tagiDao;
    public static ArtikkeliTagiDao artikkeliTagiDao;
    public static KirjaTagiDao kirjaTagiDao;

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
        kirjaDao = new KirjaDao(db);
        artikkeliDao = new ArtikkeliDao(db);
        tagiDao = new TagiDao(db);
        artikkeliTagiDao = new ArtikkeliTagiDao(db);
        kirjaTagiDao = new KirjaTagiDao(db);

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            try {
                map.put("kirjat", kirjaDao.findAll());
            } catch (SQLException ex) {
                Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new ModelAndView(map, "aloitus");
        }, new ThymeleafTemplateEngine());

        //listaa kirjat artikkelit ja tagit
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

        //Hae yksi kirja
        Spark.get("/kirja/:ISBN", (req, res) -> {
            HashMap map = new HashMap();
            String tunnus = req.params(":ISBN");
            map.put("kirja", kirjaDao.findOne(tunnus));
            map.put("tagit", tagiDao.tagitKirjoille(tunnus));
            return new ModelAndView(map, "kirja");
        }, new ThymeleafTemplateEngine());

        //Hae yksi artikkeli
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

        //siisti
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
                    res.status(401);
                    return "Ei oikea ISBN!";
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            String aika = "";
            if (Boolean.parseBoolean(req.queryParams("luettu"))) {
                aika = aikaToString();
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
        // kirjan napit
        Spark.post("/kirjat/:ISBN", (req, res) -> {
            String act = req.queryParams("nappi");
            if (act.equals("poista")) {
                
                kirjaTagiDao.delete(req.params(":ISBN"));
                kirjaDao.delete(req.params(":ISBN"));
            } else if (act.equals("Merkitse luetuksi")) {
                String uusiAika = aikaToString();
                Kirja kirja = kirjaDao.findOne(req.params(":ISBN"));
                kirja.setLuettu(true);
                kirja.setLuettuAika(uusiAika);
                kirjaDao.updateInformation(req.params(":ISBN"), kirja);
            }

            res.redirect("/kirjat");
            return "";
        });
        //hakee tagit
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
// artikkelin lisäys
        Spark.post("/artikkelit", (req, res) -> {

            List<Artikkeli> artikkeli = artikkeliDao.findAll();
            for (int l = 0; l < artikkeli.size(); l++) {
                if (artikkeli.get(l).getNimi().equals(req.queryParams("nimi"))) {
                    res.redirect("/artikkelit");
                    return "";
                }
            }
            String aika = "";
            if (Boolean.parseBoolean(req.queryParams("luettu"))) {
                aika = aikaToString();
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
        //poista tagi
        Spark.post("/deletoi/:id", (req, res) -> {
            int i = Integer.parseInt(req.params(":id"));
            artikkeliTagiDao.deleteTagi(i);
            kirjaTagiDao.deleteTagi(i);
            tagiDao.delete(i);
            res.redirect("/tagit");
            return "";
        });
//napit artikkelille
        Spark.post("/artikkelit/:id", (req, res) -> {
            System.out.println();
            String act = req.queryParams("nappi");

            if (act.equals("poista")) {
                artikkeliTagiDao.delete(Integer.parseInt(req.params(":id")));
                artikkeliDao.delete(Integer.parseInt(req.params(":id")));
                
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
        
        //muokkaa artikkelin tietoja
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
//muokkaa kirjan tietoja
        Spark.post("/kirja/:id", (req, res) -> {
            Kirja kirja = kirjaDao.findOne(req.params(":id"));
            ISBNValidator isbn = new ISBNValidator();

            try {
                if (!isbn.isValid(req.queryParams("ISBN"))) {
                    res.status(500);
                    return "Ei oikea ISBN!";

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

        //näytä luetut tai lukemattomat ***
        Spark.get("/kirjat/luetut", (req, res) -> {
            HashMap map = vainOsaKirjoista(true);
            return new ModelAndView(map, "kirjat");
        }, new ThymeleafTemplateEngine());

        Spark.get("/kirjat/lukemattomat", (req, res) -> {
            HashMap map = vainOsaKirjoista(false);
            return new ModelAndView(map, "kirjat");
        }, new ThymeleafTemplateEngine());

        Spark.get("/artikkelit/luetut", (req, res) -> {
            HashMap map = vainOsaArtikkeleista(true);
            return new ModelAndView(map, "artikkelit");
        }, new ThymeleafTemplateEngine());

        Spark.get("/artikkelit/lukemattomat", (req, res) -> {
            HashMap map = vainOsaArtikkeleista(false);
            return new ModelAndView(map, "artikkelit");
        }, new ThymeleafTemplateEngine());

        //lisaa tagi artikkeliin ***
        Spark.post("/lisaaminen", (req, res) -> {
            String tagi = req.queryParams("tagi");
            String artikkeli = req.queryParams("artikkeli");
            lisaaTagiArtikkelille(tagi, artikkeli);

            res.redirect("/artikkelit");
            return "";
        });
        //lisaa tagi kirjaan ***
        Spark.post("/lisaaminenK", (req, res) -> {
            String tagi = req.queryParams("tagi");
            String kirja = req.queryParams("kirja");
            lisaaTagiKirjalle(tagi, kirja);
            res.redirect("/kirjat");
            return "";
        });
        //hae tagiin tagatyt
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

    //apumetodit
    public static HashMap vainOsaKirjoista(boolean luettu) throws Exception {
        HashMap map = new HashMap<>();
        List<Kirja> kirjat = kirjaDao.findAll().stream().filter(kirja -> kirja.isLuettu() == luettu).collect(Collectors.toList());
        map.put("kirjat", kirjat);
        map.put("tagit", tagiDao.findAll());
        return map;
    }

    public static HashMap vainOsaArtikkeleista(boolean luettu) throws Exception {
        HashMap map = new HashMap<>();
        List<Artikkeli> artikkelit = artikkeliDao.findAll().stream().filter(artikkeli -> artikkeli.isLuettu() == luettu).collect(Collectors.toList());
        map.put("artikkelit", artikkelit);
        map.put("tagit", tagiDao.findAll());
        return map;
    }

    public static boolean lisaaTagiKirjalle(String tagi, String nimi) throws Exception {

        List<Kirja> kirjat = kirjaDao.findAll();
        List<KirjaTagi> kirjaTagit = kirjaTagiDao.findAll();
        int tagiId = loydaTagiId(tagi);

        String kirjaISBN = "";
        for (int p = 0; p < kirjat.size(); p++) {
            if (kirjat.get(p).getNimi().equals(nimi)) {
                kirjaISBN = kirjat.get(p).getISBN();
                break;
            }
        }
        for (int i = 0; i < kirjaTagit.size(); i++) {
            if (kirjaTagit.get(i).getKirjaId().equals(kirjaISBN)
                    && kirjaTagit.get(i).getTagiId().equals(tagiId)) {

                return false;
            }
        }
        kirjaTagiDao.saveOrUpdate(new KirjaTagi(kirjaISBN, tagiId));
        return true;

    }

    public static boolean lisaaTagiArtikkelille(String tagi, String artikkeli) throws Exception {

        List<Artikkeli> artikkelit = artikkeliDao.findAll();
        List<ArtikkeliTagi> artikkeliTagit = artikkeliTagiDao.findAll();
        int tagiId = loydaTagiId(tagi);

        int artikkeliId = -1;
        for (int p = 0; p < artikkelit.size(); p++) {
            if (artikkelit.get(p).getNimi().equals(artikkeli)) {
                artikkeliId = artikkelit.get(p).getId();
                break;
            }
        }
        for (int i = 0; i < artikkeliTagit.size(); i++) {
            if (artikkeliTagit.get(i).getArtikkeliId().equals(artikkeliId)
                    && artikkeliTagit.get(i).getTagiId().equals(tagiId)) {
                return false;
            }
        }
        artikkeliTagiDao.saveOrUpdate(new ArtikkeliTagi(artikkeliId, tagiId));
        return true;
    }

    public static int loydaTagiId(String tagi) throws Exception {
        List<Tagi> tagit = tagiDao.findAll();
        int tagiId = -1;
        for (int s = 0; s < tagit.size(); s++) {
            if (tagit.get(s).getNimi().equals(tagi)) {
                tagiId = tagit.get(s).getId();
                break;
            }
        }
        return tagiId;
    }

    public static String aikaToString() {
        return LocalDate.now().toString() + " "
                + LocalDateTime.now().getHour() + ":"
                + LocalDateTime.now().getMinute();
    }
}

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
import org.apache.commons.validator.routines.*;
import lukuvinkkiKirjasto.dao.ArtikkeliDao;
import lukuvinkkiKirjasto.domain.Artikkeli;

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

            return new ModelAndView(map, "kirjat");
        }, new ThymeleafTemplateEngine());
        


       
        Spark.get("/artikkelit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("artikkelit", artikkeliDao.findAll());
            return new ModelAndView(map, "artikkelit");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/kirjat", (req, res) -> {
            
            List<Kirja> kirjat = kirjaDao.findAll();
            
            
            
            
            for (int j = 0; j < kirjat.size(); j ++) {

                if (kirjat.get(j).getISBN().equals(req.queryParams("ISBN"))) {
                    res.redirect("/kirjat");
                    return "";
                }

            }
            String testi = req.queryParams("ISBN");
            ISBNValidator isbn = new ISBNValidator();
            
            try{
                if (!isbn.isValid(testi)) {
                   res.redirect("/kirjat");
                   return "";
               }
            } catch (Exception e) {System.out.println("aaaaaa");}
             
           
            
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
                            Boolean.parseBoolean(req.queryParams("luettu"))

                    )
            );
            res.redirect("/kirjat");
            return "";
             
        });

        Spark.post("/kirjat/:ISBN", (req, res) -> {
            kirjaDao.delete(req.params(":ISBN"));

            res.redirect("/kirjat");
            return "";
        });


        
        Spark.post("/artikkelit", (req, res) -> {
            List<Artikkeli> artikkelit = artikkeliDao.findAll();
            
            for (int j = 0; j < artikkelit.size(); j ++) {

                if (artikkelit.get(j).getNimi().equals(req.queryParams("nimi"))) {
                    res.redirect("/artikkelit");
                    return "";
                }

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
                            Boolean.parseBoolean(req.queryParams("luettu"))
                    )
            );
            
            res.redirect("/artikkelit");
            return "";
        });
        
        Spark.post("/artikkelit/:id", (req, res) -> {
            System.out.println(Integer.parseInt(req.params(":id")));
            artikkeliDao.delete(Integer.parseInt(req.params(":id")));

            res.redirect("/artikkelit");
            return "";
        });
        

    }

    public static void setDatabase(Database db) {
        Ui.db = db;
    }

    
  
    

}

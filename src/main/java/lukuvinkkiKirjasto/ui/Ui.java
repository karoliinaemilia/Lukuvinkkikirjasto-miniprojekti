
package lukuvinkkiKirjasto.ui;

import java.util.Date;
import java.util.HashMap;
import lukuvinkkiKirjasto.dao.KirjaDao;

import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.domain.Kirja;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Ui {
    
    public static void main(String[] args) throws Exception {
        
        Database db = new Database("jdbd:sqlite:LukuvinkkiKirjasto.db");
        
        KirjaDao kirjaDao = new KirjaDao(db);
        
        Spark.get("/kirjat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kirjat", kirjaDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
       
        
        Spark.post("/kirjat", (req, res) -> {
            kirjaDao.saveOrUpdate(new Kirja(req.queryParams("genre"), req.queryParams("nimi"), Integer.parseInt(req.queryParams("pituus")), req.queryParams("linkki"), req.queryParams("tekija"), new Date()));
            res.redirect("kirjat");
            return "";
        });
    }
    
}

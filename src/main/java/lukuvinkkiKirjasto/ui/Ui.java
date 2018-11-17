
package lukuvinkkiKirjasto.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import spark.Spark;

public class Ui {
    
    public static void main(String[] args) throws Exception {
        
        Spark.get("/hei", (req, res) -> {
            return "Hei maailma!";
        });
    }
    
}

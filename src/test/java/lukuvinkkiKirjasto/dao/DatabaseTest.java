
package lukuvinkkiKirjasto.dao;

import java.sql.SQLException;
import lukuvinkkiKirjasto.database.Database;
import org.junit.Test;

public class DatabaseTest {
    Database database = new Database("jdbc:sqlite:LukuvinkkiKirjasto.db");
    
    @Test
    public void getConnectionWorksWithEnv() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Class.forName("org.postgresql.Driver");
        System.getenv("JDBC_DATABASE_URL");
        database.getConnection();
    }
}

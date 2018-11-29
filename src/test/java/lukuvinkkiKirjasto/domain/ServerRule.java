package lukuvinkkiKirjasto.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lukuvinkkiKirjasto.database.Database;
import lukuvinkkiKirjasto.ui.Ui;
import org.junit.rules.ExternalResource;
import spark.Spark;

public class ServerRule extends ExternalResource {
    
    private final int port;

    public ServerRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
        Spark.port(port);
        Ui.setDatabase(new Database("jdbc:sqlite:LukuvinkkiKirjasto.db"));
        Ui.db.setTest(true);
        Connection conn = DriverManager.getConnection("jdbc:sqlite:LukuvinkkiKirjasto.db");
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kirja;");
        stmt.execute();
        stmt = conn.prepareStatement("DELETE FROM Artikkeli;");
        stmt.execute();
        Ui.main(null);
    }

    @Override
    protected void after() {
        Ui.db.setTest(false);
        Spark.stop();
    }
    
}
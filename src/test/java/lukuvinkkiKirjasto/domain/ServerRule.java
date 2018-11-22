package lukuvinkkiKirjasto.domain;

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
        Ui.setDatabase(new Database("jdbc:sqlite:testitietokanta.db"));
        Ui.main(null);
    }

    @Override
    protected void after() {
        Spark.stop();
    }
    
}
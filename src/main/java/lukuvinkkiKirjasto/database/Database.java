package lukuvinkkiKirjasto.database;

import java.sql.*;

public class Database {

    private String databaseAddress;
    private boolean test = false;

    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        if (test) {
            return DriverManager.getConnection(databaseAddress);
        } else {
            String dbUrl = System.getenv("JDBC_DATABASE_URL");
            if (dbUrl != null && dbUrl.length() > 0) {
                return DriverManager.getConnection(dbUrl);
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }

    public void setTest(boolean test) {
        this.test = test;
    }

}

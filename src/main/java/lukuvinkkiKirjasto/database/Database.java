package lukuvinkkiKirjasto.database;

import java.sql.*;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            Class.forName("org.postgresql.JDBC");
            return DriverManager.getConnection(dbUrl);
        }
        
        return DriverManager.getConnection(databaseAddress);
    }
    
}

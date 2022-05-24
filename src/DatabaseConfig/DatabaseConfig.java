package DatabaseConfig;
import java.sql.*;

public class DatabaseConfig {
    private static final String  url = "jdbc:mysql://localhost:3306/proiectpao";
    private static final String username = "root";
    private static final String password = "";

    static Connection connection = null;

    public DatabaseConfig() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

}

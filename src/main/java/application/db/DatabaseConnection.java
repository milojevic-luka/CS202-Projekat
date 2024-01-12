package application.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String url = dotenv.get("DB_CONNECTION");
    private static final String username = dotenv.get("DB_USERNAME");
    private static final String password = dotenv.get("DB_PASSWORD");

    public static Connection connectToDb() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

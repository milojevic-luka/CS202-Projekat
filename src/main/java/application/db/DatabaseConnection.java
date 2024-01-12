package application.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
    private final Dotenv dotenv = Dotenv.load();
    private final String url = dotenv.get("DB_CONNECTION");
    private final String username = dotenv.get("DB_USERNAME");
    private final String password = dotenv.get("DB_PASSWORD");

    public Connection connectToDb() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

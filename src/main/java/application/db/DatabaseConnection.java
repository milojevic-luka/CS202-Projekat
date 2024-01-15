package application.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Class for establishing a database connection using environment variables.
 * Requires the following environment variables: DB_CONNECTION, DB_USERNAME, DB_PASSWORD.
 */
public class DatabaseConnection {
    private final Dotenv dotenv = Dotenv.load();
    private final String url = dotenv.get("DB_CONNECTION");
    private final String username = dotenv.get("DB_USERNAME");
    private final String password = dotenv.get("DB_PASSWORD");

    /**
     * Establishes a connection to the database using the provided environment variables.
     *
     * @return A Connection object representing the established database connection.
     * @throws SQLException If an error occurs while attempting to connect to the database.
     */
    public Connection connectToDb() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

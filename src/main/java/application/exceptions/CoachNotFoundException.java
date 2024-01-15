package application.exceptions;

import java.sql.SQLException;

/**
 * Exception indicating that a coach was not found.
 * Extends SQLException to handle exceptions related to database operations.
 */
public class CoachNotFoundException extends SQLException {

    /**
     * Constructs a new CoachNotFoundException with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
    public CoachNotFoundException(String message) {
        super(message);
    }
}

package application.exceptions;

import java.sql.SQLException;

/**
 * Exception indicating that a member was not found.
 * Extends SQLException to handle exceptions related to database operations.
 */
public class MemberNotFoundException extends SQLException {

    /**
     * Constructs a new MemberNotFoundException with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
    public MemberNotFoundException(String message) {
        super(message);
    }
}
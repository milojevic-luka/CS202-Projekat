package application.exceptions;

import java.sql.SQLException;

/**
 * Exception indicating that a membership was not found.
 * Extends SQLException to handle exceptions related to database operations.
 */
public class MembershipNotFoundException extends SQLException {

    /**
     * Constructs a new MembershipNotFoundException with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
    public MembershipNotFoundException(String message) {
        super(message);
    }
}

package application.exceptions;

import java.sql.SQLException;

public class CoachNotFoundException extends SQLException {
    public CoachNotFoundException(String message) {
        super(message);
    }
}

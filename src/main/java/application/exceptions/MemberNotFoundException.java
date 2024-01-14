package application.exceptions;

import java.sql.SQLException;

public class MemberNotFoundException extends SQLException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
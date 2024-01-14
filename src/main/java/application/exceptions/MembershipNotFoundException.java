package application.exceptions;
import java.sql.SQLException;
public class MembershipNotFoundException extends SQLException{
    public MembershipNotFoundException(String message){
        super(message);
    }
}

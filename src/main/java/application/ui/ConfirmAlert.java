package application.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmAlert {
    public static Boolean show(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> button = alert.showAndWait();
        if (button.get().equals(ButtonType.OK)) {
            return true;
        } else {
            return false;
        }
    }
}

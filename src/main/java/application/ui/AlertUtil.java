package application.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Utility class for displaying various types of alert dialogs in the application.
 */
public class AlertUtil {

    /**
     * Displays a confirmation dialog with the specified title and message.
     *
     * @param title   The title of the confirmation dialog.
     * @param message The message displayed in the confirmation dialog.
     * @return {@code true} if the user clicks OK, {@code false} otherwise.
     */
    public static Boolean showConfirm(String title, String message) {
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

    /**
     * Displays an information dialog with the specified title and message.
     *
     * @param title   The title of the information dialog.
     * @param message The message displayed in the information dialog.
     */
    public static void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an error dialog with the specified title and message.
     *
     * @param title   The title of the error dialog.
     * @param message The message displayed in the error dialog.
     */
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

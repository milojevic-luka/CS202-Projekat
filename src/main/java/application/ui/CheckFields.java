package application.ui;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling TextField operations in a JavaFX application.
 */
public class CheckFields {

    /**
     * Retrieves a list of TextFields provided as parameters.
     *
     * @param fields The TextFields to be included in the list.
     * @return A list containing the provided TextFields.
     */
    public static List<TextField> getFields(TextField... fields) {
        List<TextField> textFieldList = new ArrayList<>();
        for (TextField field : fields) textFieldList.add(field);
        return textFieldList;
    }

    /**
     * Checks if the provided list of TextFields is filled (not empty).
     *
     * @param fields The list of TextFields to be checked.
     * @return {@code true} if all TextFields are filled, {@code false} otherwise.
     */
    public static Boolean areFieldsFilled(List<TextField> fields) {
        for (TextField field : fields)
            if (field.getText().isEmpty()) {
                System.out.println(field.getText() + "\n" + field);
                return false;
            }
        ;
        return true;
    }

    /**
     * Clears the contents of the provided list of TextFields.
     *
     * @param fields The list of TextFields to be cleared.
     */
    public static void clearFields(List<TextField> fields) {
        if (fields.isEmpty()) return;
        for (TextField field : fields) field.clear();
    }
}

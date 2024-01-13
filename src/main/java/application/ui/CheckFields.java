package application.ui;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class CheckFields {

    public static List<TextField> getFields(TextField... fields){
        List<TextField> textFieldList = new ArrayList<>();
        for(TextField field: fields) textFieldList.add(field);
        return textFieldList;
    }
    public static Boolean areFieldsFilled(List<TextField> fields) {
        for (TextField field : fields) if (field.getText().isEmpty()) {
            System.out.println(field.getText() + "\n" + field);
            return false;
        };
        return true;
    }

    public static void clearFields(List<TextField> fields) {
        if(fields.isEmpty()) return;
        for (TextField field : fields) field.clear();
    }
}

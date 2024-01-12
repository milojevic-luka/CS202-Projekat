package application.ui;

import javafx.scene.control.ComboBox;

import java.util.List;

public class ComboBoxPopulation {
    public void populate(ComboBox<String> comboBox, List<String> comboList, String defaultSelection) {
        for (String item : comboList) comboBox.getItems().add(item);
        comboBox.setValue(defaultSelection);
    }
}

package application.ui;

import javafx.scene.control.ComboBox;

import java.util.List;

/**
 * Utility class for populating a JavaFX ComboBox with a list of items.
 */
public class ComboBoxPopulation {

    /**
     * Populates the specified ComboBox with items from the provided list and sets a default selection.
     *
     * @param comboBox         The ComboBox to be populated.
     * @param comboList        The list of items to populate the ComboBox with.
     * @param defaultSelection The default item to be selected in the ComboBox.
     */
    public void populate(ComboBox<String> comboBox, List<String> comboList, String defaultSelection) {
        for (String item : comboList) comboBox.getItems().add(item);
        comboBox.setValue(defaultSelection);
    }
}

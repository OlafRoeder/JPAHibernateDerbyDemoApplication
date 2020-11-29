package view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.Animal;

import java.text.MessageFormat;

public class AnimalCellFactory implements javafx.util.Callback<javafx.scene.control.ListView<model.Animal>, javafx.scene.control.ListCell<model.Animal>> {

    @Override
    public ListCell<Animal> call(ListView<Animal> param) {

        return new ListCell<>() {

            @Override
            protected void updateItem(Animal item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null){

                    setText(null);
                    return;
                }

                setText(MessageFormat.format("{0} (age: {1}, type: {2})", item.getName(), item.getAge(),
                        item.getType()));
            }
        };
    }
}

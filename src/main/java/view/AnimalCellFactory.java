package view;

import application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.Animal;

import java.text.MessageFormat;

public class AnimalCellFactory implements javafx.util.Callback<javafx.scene.control.ListView<model.Animal>, javafx.scene.control.ListCell<model.Animal>> {

    private final Application application;

    public AnimalCellFactory(Application application) {
        this.application=application;
    }

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

                Button delete = new Button("delete");
                delete.onActionProperty().setValue((ActionEvent event) -> application.deleteAnimal(item));
                setGraphic(delete);

                setText(MessageFormat.format("{0} (age: {1}, type: {2})", item.getName(), item.getAge(),
                        item.getType()));
            }
        };
    }
}

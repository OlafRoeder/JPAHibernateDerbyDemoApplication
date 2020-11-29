package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.Animal;
import view.viewmodel.MainWindowViewModel;

public class AnimalCellFactory implements javafx.util.Callback<javafx.scene.control.ListView<model.Animal>, javafx.scene.control.ListCell<model.Animal>> {

    private final MainWindowViewModel viewModel;

    public AnimalCellFactory(MainWindowViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public ListCell<Animal> call(ListView<Animal> param) {

        return new ListCell<>() {

            @Override
            protected void updateItem(Animal item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {

                    setText(null);
                    setGraphic(null);

                } else {

                    Button delete = new Button("delete");
                    delete.onActionProperty().setValue((ActionEvent event) -> viewModel.deleteAnimal(item));
                    setGraphic(delete);

                    setText(viewModel.getAnimalDisplayText(item));
                }
            }
        };
    }
}

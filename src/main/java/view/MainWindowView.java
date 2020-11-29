package view;

import application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import lombok.NonNull;
import model.Animal;
import model.Type;
import view.AnimalCellFactory;

public class MainWindowView extends VBox {

    private final Application application;

    @FXML
    private TextField name;

    @FXML
    private Spinner<Integer> age;

    @FXML
    private ComboBox<Type> type;

    @FXML
    private ListView<Animal> list;

    private final ObservableList<Animal> animals= FXCollections.observableArrayList();

    public MainWindowView(@NonNull Application application) {
        this.application = application;
    }

    @FXML
    private void initialize() {

        type.getItems().setAll(Type.values());

        list.setCellFactory(new AnimalCellFactory(application));
        list.setItems(animals);
        animals.setAll(application.getAnimals());
    }

    @FXML
    private void onQuit() {
        application.quit();
    }

    @FXML
    private void onCreate(){

        Type type = this.type.getSelectionModel().getSelectedItem();
        Integer age = this.age.getValue();
        String name = this.name.textProperty().get();

        Animal animal = application.createAnimal(type, age, name);
        animals.add(animal);
    }
}
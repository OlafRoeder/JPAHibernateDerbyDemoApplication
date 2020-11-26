package controller;

import application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.NonNull;
import model.Animal;
import model.Type;

public class MainWindowController extends VBox {

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

    public MainWindowController(@NonNull Application application) {
        this.application = application;
    }

    @FXML
    private void initialize() {

        type.getItems().setAll(Type.values());

        list.setCellFactory(new Callback<>() {

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

                        setText(item.toString());
                    }
                };
            }
        });
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
package controller;

import application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.NonNull;
import model.Type;

public class MainWindowController extends VBox {

    private final Application application;

    @FXML
    private TextField name;

    @FXML
    private Spinner<Integer> age;

    @FXML
    private ComboBox<Type> type;

    public MainWindowController(@NonNull Application application) {
        this.application = application;
    }

    @FXML
    private void initialize() {
        type.getItems().setAll(Type.values());
    }

    @FXML
    private void onQuit() {
        application.quit();
    }
}
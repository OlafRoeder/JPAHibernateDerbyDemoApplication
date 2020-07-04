package controller;

import application.Application;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.NonNull;

public class MainWindowController extends VBox {

    private final Application application;

    public MainWindowController(@NonNull Application application) {
        this.application = application;
    }

    @FXML
    private void onQuit() {
        application.quit();
    }
}
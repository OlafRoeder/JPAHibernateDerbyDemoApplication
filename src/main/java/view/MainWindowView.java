package view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.NonNull;
import model.Animal;
import model.Type;
import view.viewmodel.MainWindowViewModel;

public class MainWindowView extends VBox {

    private final MainWindowViewModel viewModel;

    @FXML
    private TextField name;
    @FXML
    private Spinner<Integer> age;
    @FXML
    private ComboBox<Type> type;
    @FXML
    private ListView<Animal> list;

    public MainWindowView(@NonNull MainWindowViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {

        name.textProperty().bindBidirectional(viewModel.nameProperty());
        viewModel.ageProperty().bind(age.valueProperty());
        viewModel.typeProperty().bind(type.getSelectionModel().selectedItemProperty());

        type.getItems().setAll(Type.values());

        list.setCellFactory(viewModel.getListViewCellFactory());
        list.setItems(viewModel.getAnimals());

    }

    @FXML
    private void onCreate() {
        viewModel.create();
    }

    @FXML
    private void onQuit() {
        viewModel.quit();
    }
}
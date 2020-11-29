package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML
    private Button createButton;
    @FXML
    private Button updateButton;

    public MainWindowView(@NonNull MainWindowViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {

        name.textProperty().bindBidirectional(viewModel.nameProperty());
        viewModel.ageProperty().bind(age.valueProperty());
        viewModel.ageFactoryProperty().bind(age.valueFactoryProperty());
        viewModel.selectedTypeProperty().bindBidirectional(type.selectionModelProperty());

        createButton.disableProperty().bind(viewModel.createButtonDisableProperty());
        updateButton.disableProperty().bind(viewModel.updateButtonDisableProperty());

        type.getItems().setAll(viewModel.getTypes());

        list.setCellFactory(viewModel.getListViewCellFactory());
        list.setItems(viewModel.getAnimals());
        viewModel.animalProperty().bind(list.getSelectionModel().selectedItemProperty());
        viewModel.selectionModelProperty().set(list.getSelectionModel());
        viewModel.selectedIndexProperty().bind(list.getSelectionModel().selectedIndexProperty());
    }

    @FXML
    private void onCreate() {
        viewModel.createAnimal();
    }

    @FXML
    private void onUpdate() {
        viewModel.updateAnimal();
    }

    @FXML
    private void onQuit() {
        viewModel.quit();
    }
}
package view.viewmodel;

import application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;
import lombok.NonNull;
import model.Animal;
import model.Type;
import view.AnimalCellFactory;

import java.text.MessageFormat;

public class MainWindowViewModel {

    private final Application application;

    private final ObservableList<Animal> animals = FXCollections.observableArrayList();
    private final ObjectProperty<Animal> animalProperty = new SimpleObjectProperty<>();

    private final StringProperty nameProperty = new SimpleStringProperty();
    private final IntegerProperty ageProperty = new SimpleIntegerProperty();
    private final Property<SingleSelectionModel<Type>> selectedTypeProperty = new SimpleObjectProperty<>();

    private final BooleanProperty createButtonActiveProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty updateButtonActiveProperty = new SimpleBooleanProperty(false);

    private final ObjectProperty<SpinnerValueFactory<Integer>> ageFactoryProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<MultipleSelectionModel<Animal>> selectionModelProperty = new SimpleObjectProperty<>();
    private final IntegerProperty selectedIndexProperty = new SimpleIntegerProperty();

    public MainWindowViewModel(@NonNull Application application) {

        this.application = application;

        this.animals.setAll(application.getAnimals());

        this.createButtonActiveProperty.bind(nameProperty.isNotEmpty().and(ageProperty.greaterThan(0)));

        this.selectedIndexProperty.addListener((observable, oldValue, newValue) ->
                updateButtonActiveProperty.set(newValue.intValue() > -1)
        );

        this.animalProperty.addListener((observable, oldValue, newValue) -> {

            if(newValue==null){

                nameProperty.set(null);
                ageFactoryProperty.get().setValue(0);
                selectedTypeProperty.getValue().select(Type.AMPHIBIAN);

                return;
            }

            nameProperty.set(newValue.getName());
            ageFactoryProperty.get().setValue(newValue.getAge());
            selectedTypeProperty.getValue().select(newValue.getType());
        });
    }

    public void createAnimal() {

        Type type = selectedTypeProperty.getValue().getSelectedItem();
        Integer age = ageProperty.get();
        String name = nameProperty.get();

        Animal animal = application.createAnimal(type, age, name);
        animals.add(animal);
    }

    public ObservableList<Animal> getAnimals() {
        return animals;
    }

    public void updateAnimal() {

        Animal animal = animalProperty.get();

        application.updateAnimal(animal, nameProperty.get(), ageProperty.get(), selectedTypeProperty.getValue().getSelectedItem());

        int index = animals.indexOf(animal);

        // triggers refresh on ListView
        animals.remove(animal);
        animals.add(index, animal);

        selectionModelProperty.get().select(animal);
    }

    public void deleteAnimal(Animal animal) {
        application.deleteAnimal(animal);
        animals.remove(animal);
    }

    public String getAnimalDisplayText(Animal animal) {
        return MessageFormat.format("{0} (age: {1}, type: {2})", animal.getName(), animal.getAge(),
                animal.getType());
    }

    public Property<String> nameProperty() {
        return nameProperty;
    }

    public IntegerProperty ageProperty() {
        return ageProperty;
    }

    public Callback<ListView<Animal>, ListCell<Animal>> getListViewCellFactory() {
        return new AnimalCellFactory(this);
    }

    public BooleanBinding createButtonDisableProperty() {
        return createButtonActiveProperty.not();
    }

    public BooleanBinding updateButtonDisableProperty() {
        return updateButtonActiveProperty.not();
    }

    public Type[] getTypes() {
        return Type.values();
    }

    public ObjectProperty<Animal> animalProperty() {
        return animalProperty;
    }

    public ObjectProperty<SpinnerValueFactory<Integer>> ageFactoryProperty() {
        return ageFactoryProperty;
    }

    public Property<SingleSelectionModel<Type>> selectedTypeProperty() {
        return selectedTypeProperty;
    }

    public ObjectProperty<MultipleSelectionModel<Animal>> selectionModelProperty() {
        return selectionModelProperty;
    }

    public IntegerProperty selectedIndexProperty() {
        return selectedIndexProperty;
    }

    public void quit() {
        application.quit();
    }
}

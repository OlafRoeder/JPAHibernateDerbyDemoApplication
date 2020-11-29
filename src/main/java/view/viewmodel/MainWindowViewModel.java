package view.viewmodel;

import application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import lombok.NonNull;
import model.Animal;
import model.Type;
import view.AnimalCellFactory;

import java.text.MessageFormat;

public class MainWindowViewModel {

    private final Application application;

    private final ObservableList<Animal> animals = FXCollections.observableArrayList();
    private final StringProperty nameProperty = new SimpleStringProperty();
    private final IntegerProperty ageProperty = new SimpleIntegerProperty();
    private final ObjectProperty<Type> typeProperty = new SimpleObjectProperty<>();

    private final BooleanProperty createButtonActiveProperty = new SimpleBooleanProperty(false);

    public MainWindowViewModel(@NonNull Application application) {

        this.application = application;

        this.animals.setAll(application.getAnimals());

        this.createButtonActiveProperty.bind(nameProperty.isNotEmpty().and(ageProperty.greaterThan(0).and(typeProperty.isNotNull())));

    }

    public void create() {

        Type type = typeProperty.get();
        Integer age = ageProperty.get();
        String name = nameProperty.get();

        Animal animal = application.createAnimal(type, age, name);
        animals.add(animal);
    }

    public ObservableList<Animal> getAnimals() {
        return animals;
    }

    public void deleteAnimal(Animal animal) {
        application.deleteAnimal(animal);
        animals.remove(animal);
    }

    public String getDisplayText(Animal animal) {
        return MessageFormat.format("{0} (age: {1}, type: {2})", animal.getName(), animal.getAge(),
                animal.getType());
    }

    public Property<String> nameProperty() {
        return nameProperty;
    }

    public IntegerProperty ageProperty() {
        return ageProperty;
    }

    public ObjectProperty<Type> typeProperty() {
        return typeProperty;
    }

    public Callback<ListView<Animal>, ListCell<Animal>> getListViewCellFactory() {
        return new AnimalCellFactory(this);
    }

    public BooleanBinding createButtonDisableProperty() {
        return createButtonActiveProperty.not();
    }

    public void quit() {
        application.quit();
    }
}

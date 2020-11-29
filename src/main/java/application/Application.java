package application;

import javafx.application.Platform;
import model.Animal;
import model.Type;

import java.util.List;

public class Application {

    private final ApplicationType applicationType;

    Application(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public void quit() {
        applicationType.shutdown();
        Platform.exit();
    }

    FXMLControllerFactory getFxmlControllerFactory() {
        return new FXMLControllerFactory(this);
    }

    public Animal createAnimal(Type type, Integer age, String name) {
        return applicationType.createAnimal(type, age, name);
    }

    public List<Animal> getAnimals() {
        return applicationType.getAnimals();
    }

    public void deleteAnimal(Animal animal) {
        applicationType.deleteAnimal(animal);
    }
}

package application;

import javafx.application.Platform;
import javafx.concurrent.Task;
import model.Animal;
import model.Type;

import java.util.List;

public class Application {

    private final ApplicationType applicationType;

    Application(ApplicationType applicationType) {
        this.applicationType = applicationType;
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

    public void updateAnimal(Animal animal, String name, int age, Type type) {
        applicationType.updateAnimal(animal, name, age, type);
    }

    public void deleteAnimal(Animal animal) {
        applicationType.deleteAnimal(animal);
    }

    public void execute(Task<?> task) {
        applicationType.execute(task);
    }

    public void quit() {
        applicationType.shutdown();
        Platform.exit();
    }
}

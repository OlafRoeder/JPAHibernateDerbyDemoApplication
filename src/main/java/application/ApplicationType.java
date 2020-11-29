package application;

import javafx.concurrent.Task;
import model.Animal;
import model.Type;

import java.util.List;

public interface ApplicationType {

    void printApplicationType();

    void shutdown();

    Animal createAnimal(Type type, Integer age, String name);

    List<Animal> getAnimals();

    void updateAnimal(Animal animal, String name, int age, Type type);

    void deleteAnimal(Animal animal);

    void execute(Task<?> task);
}

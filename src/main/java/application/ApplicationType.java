package application;

import model.Animal;
import model.Type;

import java.util.List;

public interface ApplicationType {

    void printApplicationType();

    void shutdown();

    Animal createAnimal(Type type, Integer age, String name);

    List<Animal> getAnimals();
}

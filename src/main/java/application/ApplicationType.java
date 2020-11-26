package application;

import model.Type;

public interface ApplicationType {

    void printApplicationType();

    void shutdown();

    void createAnimal(Type type, Integer age, String name);
}

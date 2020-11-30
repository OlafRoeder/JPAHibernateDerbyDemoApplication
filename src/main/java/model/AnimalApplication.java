package model;

import application.ApplicationType;
import concurrency.GlobalExecutorService;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.AnimalDao;

import java.util.List;

public class AnimalApplication implements ApplicationType {

    private static final Logger logger = LoggerFactory.getLogger(AnimalApplication.class);

    private final AnimalDao animalDao;

    public AnimalApplication() {
        this.animalDao = new AnimalDao();
    }

    @Override
    public void printApplicationType() {
        logger.info("JPA Hibernate Derby Demo. Test CRUD operations with animals.");
    }

    @Override
    public Animal createAnimal(Type type, Integer age, String name) {
        return animalDao.createAnimal(type, age, name);
    }

    @Override
    public List<Animal> getAnimals() {
        return animalDao.getAnimals();
    }

    @Override
    public void updateAnimal(Animal animal, String name, int age, Type type) {
        animalDao.updateAnimal(animal, name, age, type);
    }

    @Override
    public void deleteAnimal(Animal animal) {
        animalDao.deleteAnimal(animal);
    }

    @Override
    public void execute(Task<?> task) {
        GlobalExecutorService.submit(task);
    }

    @Override
    public void shutdown() {
        animalDao.shutdown();
    }
}

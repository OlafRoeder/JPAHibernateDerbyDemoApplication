package model;

import application.ApplicationType;
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
        logger.info("AnimalApplication");
    }

    @Override
    public void shutdown() {
        animalDao.shutdown();
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
    public void deleteAnimal(Animal animal) {
        animalDao.deleteAnimal(animal);
    }
}

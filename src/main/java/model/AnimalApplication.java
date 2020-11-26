package model;

import application.ApplicationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.AnimalDao;

public class AnimalApplication implements ApplicationType {

    private static final Logger logger = LoggerFactory.getLogger(AnimalApplication.class);

    private final AnimalDao animalDao;

    public AnimalApplication() {
        this.animalDao = new AnimalDao();
    }

    @Override
    public void printApplicationType() {
        logger.info("Starting animal application");
    }

    @Override
    public void shutdown() {
        animalDao.shutdown();
    }

    @Override
    public void createAnimal(Type type, Integer age, String name) {
        animalDao.createAnimal(type, age, name);
    }
}

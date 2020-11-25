package application;

import persistence.AnimalDao;

public class JpaHibernateDemo {

    public static void main(String[] args) {

        AnimalDao animalDao = new AnimalDao();
        animalDao.printDatabase();
        animalDao.createAnimal();
        animalDao.printDatabase();
        animalDao.updateAnimal();
        animalDao.printDatabase();
        animalDao.deleteAnimal();
        animalDao.printDatabase();

        animalDao.shutdown();
    }
}
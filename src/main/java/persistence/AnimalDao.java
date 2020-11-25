package persistence;

import model.Animal;
import model.Type;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class AnimalDao {

    /**
     * Persistence unit as declared in persistence.xml
     */
    private static final String APPLICATION_PERSISTENCE_UNIT = "application-persistence-unit";

    /**
     * Configured via persistence.xml, an EntityManagerFactory is coupled to a persistence unit and responsible
     * to create an EntityManager.
     */
    private final EntityManagerFactory entityManagerFactory;

    /**
     * Provides methods for database interaction (CRUD), e.g. retrieve data, persist data, delete data and more complex
     * operations.
     */
    private final EntityManager entityManager;

    public AnimalDao() {

        /* init EntityManagerFactory and EntityManager*/

        this.entityManagerFactory = Persistence.createEntityManagerFactory(APPLICATION_PERSISTENCE_UNIT);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Demonstration of a simple select (CRUD: READ)
     */
    public void printDatabase() {

        Query findAnimals = entityManager.createQuery("select animal from Animal animal", Animal.class);

        @SuppressWarnings("unchecked")
        List<Animal> animalList = findAnimals.getResultList();

        for (Animal animal : animalList)
            System.out.println(animal);

        System.out.println("Database Size: " + animalList.size());
    }

    /**
     * Demonstration of a simple persist (CRUD: CREATE)
     */
    public void createAnimal() {

        entityManager.getTransaction().begin();

        Animal animal = new Animal();
        animal.setType(Type.MAMMAL);
        animal.setSubtype("Dog");
        animal.setWeight(17);

        entityManager.persist(animal);
        entityManager.getTransaction().commit();
    }

    /**
     * Demonstration of a simple update (CRUD: UPDATE)
     */
    public void updateAnimal() {

        entityManager.getTransaction().begin();

        Animal animal = entityManager.find(Animal.class, 1L);

        animal.setWeight(5);
        animal.setType(Type.BIRD);
        animal.setSubtype("Penguin");

        entityManager.getTransaction().commit();
    }

    /**
     * Demonstration of a simple delete (CRUD: DELETE)
     */
    public void deleteAnimal() {

        entityManager.getTransaction().begin();

        Animal animal = entityManager.find(Animal.class, 1L);

        entityManager.remove(animal);

        entityManager.getTransaction().commit();
    }

    /**
     * Shutdown entity manager after all operations are done.
     */
    public void shutdown() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
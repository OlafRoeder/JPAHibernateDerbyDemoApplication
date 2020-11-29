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
     * Finds and returns all {@link Animal animals} from the database.
     *
     * @return List of {@link Animal animals}
     */
    @SuppressWarnings("unchecked")
    public List<Animal> getAnimals() {

        Query findAnimals = entityManager.createQuery("select animal from Animal animal", Animal.class);

        return findAnimals.getResultList();
    }

    /**
     * Demonstration of a simple persist with parameters.
     *
     * @param type {@link Type} of {@link Animal}
     * @param age  Age of {@link Animal}
     * @param name Name of {@link Animal}
     */
    public Animal createAnimal(Type type, Integer age, String name) {

        beginTransaction();

        Animal animal = new Animal();
        animal.setType(type);
        animal.setAge(age);
        animal.setName(name);

        entityManager.persist(animal);

        commitTransaction();

        return animal;
    }

    /**
     * Demonstration of a simple update (CRUD: UPDATE)
     * @param animal The {@link Animal} to update
     * @param name new name value
     * @param age new age value
     * @param type new {@link Type value}
     */
    public void updateAnimal(Animal animal, String name, int age, Type type) {

        beginTransaction();

        animal.setName(name);
        animal.setAge(age);
        animal.setType(type);

        commitTransaction();
    }

    /**
     * Demonstration of a simple delete (CRUD: DELETE) with parameter
     */
    public void deleteAnimal(Animal animal) {

        beginTransaction();

        entityManager.remove(animal);

        commitTransaction();
    }

    private void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    private void commitTransaction() {
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

package application;

import model.demo.Animal;
import model.demo.Type;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class JpaHibernateDemo {

    public static final String APPLICATION_PERSISTENCE_UNIT = "application-persistence-unit";

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public JpaHibernateDemo() {
        entityManagerFactory = Persistence.createEntityManagerFactory(APPLICATION_PERSISTENCE_UNIT);
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void printDatabase(){

        Query query = entityManager.createQuery("select animal from Animal animal", Animal.class);

        List<Animal> animalList = query.getResultList();

        for (Animal animal : animalList)
            System.out.println(animal);

        System.out.println("Size: " + animalList.size());
    }

    public void createAnimal(){

        entityManager.getTransaction().begin();

        Animal animal = new Animal();
        animal.setType(Type.MAMMAL);
        animal.setSubtype("Dog");
        animal.setWeight(17);

        entityManager.persist(animal);
        entityManager.getTransaction().commit();

    }

    public void shutdown() {
        entityManager.close();
    }
}

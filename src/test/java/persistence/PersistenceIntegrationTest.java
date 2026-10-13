package persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Animal;
import model.Type;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceIntegrationTest {
    @TempDir
    Path directory;

    private EntityManagerFactory factory;
    private EntityManager manager;

    @BeforeEach
    void openDatabase() {
        var properties = Map.of("jakarta.persistence.jdbc.url",
                "jdbc:derby:" + directory.resolve("animals") + ";create=true");
        factory = Persistence.createEntityManagerFactory("application-persistence-unit", properties);
        manager = factory.createEntityManager();
    }

    @AfterEach
    void shutdownDatabase() throws SQLException {
        if (manager != null && manager.isOpen()) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            manager.close();
        }
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
        // Derby keeps database files open until an explicit shutdown, even after JPA closes.
        try {
            DriverManager.getConnection("jdbc:derby:" + directory.resolve("animals") + ";shutdown=true");
        } catch (SQLException exception) {
            // Derby reports a successful database shutdown with SQLState 08006.
            if (!"08006".equals(exception.getSQLState())) {
                throw exception;
            }
        }
    }

    @Test
    void createsAnimal() {
        // Given an animal that has not been persisted.
        var animal = newAnimal();

        // When the animal is persisted and the transaction is committed.
        manager.getTransaction().begin();
        manager.persist(animal);
        manager.getTransaction().commit();
        manager.clear();

        // Then it has a generated ID and its values are stored in the database.
        assertTrue(animal.getId() > 0);
        var stored = manager.find(Animal.class, animal.getId());
        assertNotNull(stored);
        assertEquals("Demo animal", stored.getName());
        assertEquals(2, stored.getAge());
        assertEquals(Type.BIRD, stored.getType());
    }

    @Test
    void readsAnimal() {
        // Given an animal already stored in the database.
        var animal = persistAnimal();

        // When animals are queried from the database.
        var animals = manager.createQuery("select animal from Animal animal", Animal.class)
                .getResultList();

        // Then the query returns the stored animal and all its values.
        assertEquals(1, animals.size());
        var stored = animals.getFirst();
        assertEquals(animal.getId(), stored.getId());
        assertEquals("Demo animal", stored.getName());
        assertEquals(2, stored.getAge());
        assertEquals(Type.BIRD, stored.getType());
    }

    @Test
    void updatesAnimal() {
        // Given an animal already stored in the database.
        var animal = persistAnimal();
        var stored = manager.find(Animal.class, animal.getId());

        // When its values are changed and the transaction is committed.
        manager.getTransaction().begin();
        stored.setName("Updated animal");
        stored.setAge(3);
        stored.setType(Type.FISH);
        manager.getTransaction().commit();
        manager.clear();

        // Then a fresh database read returns the updated values with the same ID.
        var updated = manager.find(Animal.class, animal.getId());
        assertNotNull(updated);
        assertEquals(animal.getId(), updated.getId());
        assertEquals("Updated animal", updated.getName());
        assertEquals(3, updated.getAge());
        assertEquals(Type.FISH, updated.getType());
    }

    @Test
    void deletesAnimal() {
        // Given an animal already stored in the database.
        var animal = persistAnimal();
        var stored = manager.find(Animal.class, animal.getId());

        // When the animal is removed and the transaction is committed.
        manager.getTransaction().begin();
        manager.remove(stored);
        manager.getTransaction().commit();
        manager.clear();

        // Then the animal can no longer be found in the database.
        assertNull(manager.find(Animal.class, animal.getId()));
        assertTrue(manager.createQuery("select animal from Animal animal", Animal.class)
                .getResultList().isEmpty());
    }

    private Animal persistAnimal() {
        var animal = newAnimal();
        manager.getTransaction().begin();
        manager.persist(animal);
        manager.getTransaction().commit();
        manager.clear();
        return animal;
    }

    private Animal newAnimal() {
        var animal = new Animal();
        animal.setName("Demo animal");
        animal.setAge(2);
        animal.setType(Type.BIRD);
        return animal;
    }
}

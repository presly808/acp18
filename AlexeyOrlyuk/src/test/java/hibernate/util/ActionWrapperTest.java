package hibernate.util;

import hibernate.model.TestEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ActionWrapperTest {

    private static EntityManagerFactory managerFactory;
    private static final String PERSISTENCE_UNIT = "hibernate-h2-unit";

    @Before
    public void setUp() throws Exception {
        managerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    @After
    public void tearDown() throws Exception {
        EntityManager manager = managerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.createNativeQuery("DELETE FROM test_entity;").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }

        managerFactory.close();
    }

    @Test
    public void executeFunction() throws Exception {
        TestEntity expected = new TestEntity("test_value");

        saveTestEntity(expected);

        TestEntity actual = ActionWrapper.wrap(managerFactory, expected)
                .execute((manager, entity) -> {
                    return manager.find(entity.getClass(), entity.getId());
                });

        assertEquals(expected, actual);
    }

    @Test
    public void executeGroupFunction() throws Exception {
        List<TestEntity> expected = new ArrayList<>();
        expected.add(new TestEntity("first"));
        expected.add(new TestEntity("second"));
        expected.add(new TestEntity("third"));

        EntityManager entityManager = managerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            expected.forEach(entityManager::persist);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }


        List<TestEntity> actual = ActionWrapper.wrap(managerFactory, new TestEntity(), 10)
                .execute((manager, entity, limit) ->
                        manager.createQuery("SELECT t FROM TestEntity t", TestEntity.class)
                                .setMaxResults(limit).getResultList());

        assertEquals(expected, actual);
    }

    @Test
    public void executeProcedure() throws Exception {
        TestEntity expected = new TestEntity("test_value");

        saveTestEntity(expected);

        final List<TestEntity> actual = new ArrayList<>();

        ActionWrapper.wrap(managerFactory, expected).execute((manager, entity) -> {
            actual.add(
                    manager.createQuery("SELECT t FROM TestEntity t WHERE t.value=:par", TestEntity.class)
                            .setParameter("par", "test_value").getResultList().get(0)
            );
        });

        assertEquals(expected, actual.get(0));
        assertEquals(1, actual.size());
    }

    @Test
    public void executeFunctionWithTransaction() throws Exception {
        TestEntity testEntity = new TestEntity("test_value");
        saveTestEntity(testEntity);

        TestEntity expected = new TestEntity(testEntity.getId(), "new_test_value");

        TestEntity actual = ActionWrapper.wrap(managerFactory, expected).executeWithTransaction((manager, entity) -> {
            manager.merge(entity);
            return manager.find(entity.getClass(), entity.getId());
        });

        assertEquals(expected, actual);
    }

    @Test
    public void executeGroupFunctionWithTransaction() throws Exception {
        TestEntity testEntity = new TestEntity("test_value");
        saveTestEntity(testEntity);

        TestEntity expected = new TestEntity(testEntity.getId(), "new_test_value");

        ActionWrapper
                .wrap(managerFactory, expected)
                .executeWithTransaction((ActionWrapper.Procedure<TestEntity>) EntityManager::merge);

        List<TestEntity> actual = checkPersistenceStatus();

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
    }

    @Test
    public void executeProcedureWithTransaction() throws Exception {
        final List<TestEntity> testEntityList = new ArrayList<>();
        testEntityList.add(new TestEntity("first"));
        testEntityList.add(new TestEntity("second"));

        EntityManager entityManager = managerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            testEntityList.forEach(entityManager::persist);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        TestEntity expected = new TestEntity(testEntityList.get(0).getId(), "not_first");

        List<TestEntity> actual = ActionWrapper.wrap(managerFactory, expected, 10)
                .executeWithTransaction((manager, entity, limit) -> {
                    manager.merge(entity);
                    return manager.createQuery("SELECT t FROM TestEntity t", TestEntity.class)
                            .setMaxResults(limit).getResultList();
                });

        assertEquals(expected, actual.get(0));
        assertEquals(testEntityList.get(1), actual.get(1));
        assertEquals(testEntityList.size(), actual.size());
    }

    private void saveTestEntity(TestEntity testEntity) {
        EntityManager entityManager = managerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(testEntity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Returns all TestEntity records from Persistance.
     * @return result List of TestEntity objects.
     */
    private List<TestEntity> checkPersistenceStatus() {
        if (!managerFactory.isOpen()) {
            return null;
        }

        EntityManager manager = managerFactory.createEntityManager();

        try {
            return manager.createQuery("SELECT t FROM TestEntity t", TestEntity.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            manager.close();
        }
    }

}



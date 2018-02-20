package hibernate.entetyManagerSingle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntetyManagerSingleton {

    private static final EntetyManagerSingleton entetyManagerSingleton = new EntetyManagerSingleton();
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private EntetyManagerSingleton() {}

    public static EntetyManagerSingleton getEntetyManagerSingleton() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hiberdb");
        entityManager = entityManagerFactory.createEntityManager();
        return entetyManagerSingleton;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }
}

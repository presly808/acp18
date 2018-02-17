package hibernate.main.exclude;

import hibernate.exception.exclude.AppException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class ServiceUtils {

    public static <T> T addObjToDb(T obj, EntityManagerFactory factory) throws AppException {
        if (factory == null || !factory.isOpen()) {
            throw new AppException("factory is null or closed");
        }

        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.persist(obj);
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            /*throw new AppException(obj.getClass().getSimpleName() + " can not be added");*/

        } finally {
            manager.close();
        }

        return obj;
    }
}

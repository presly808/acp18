package hibernate.dao;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoImpl<T, ID> implements Dao<T, ID> {

    private EntityManagerFactory factory;

    private T obj;

    private Class<T> clazz = (Class<T>) obj.getClass();

    private final Logger logger = Logger.getLogger(clazz);

    public DaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<T> findAll() {

        EntityManager manager = factory.createEntityManager();
        TypedQuery<T> query = manager.createQuery("SELECT e FROM " +
                clazz.getSimpleName() + " e", clazz);
        try {
            List<T> list = query.getResultList();
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public List<T> findAll(int offset, int length) {

        EntityManager manager = factory.createEntityManager();
        TypedQuery<T> query = manager.createQuery("SELECT e FROM " +
                clazz.getSimpleName() + " e", clazz);
        try {
            query.setFirstResult(offset);
            query.setMaxResults(offset + length);
            List<T> list = query.getResultList();
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public T find(ID id) {

        EntityManager manager = factory.createEntityManager();
        try {
            obj = manager.find(clazz, id);
            return obj;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public T remove(ID id) {
        EntityManager manager = factory.createEntityManager();
        try {
            obj = manager.find(clazz, id);
            if (obj != null) {
                manager.remove(obj);
            }
            return obj;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            manager.close();
        }

    }

    @Override
    public T update(T entity) {

        return null;
    }
}

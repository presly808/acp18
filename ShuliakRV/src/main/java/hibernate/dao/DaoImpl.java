package hibernate.dao;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoImpl<T, ID> implements Dao<T, ID> {

    protected EntityManagerFactory factory;

    private Class<T> entityClass;

    protected final Logger logger;

    public DaoImpl(EntityManagerFactory factory, Class<T> entityClass) {

        this.factory = factory;
        this.entityClass = entityClass;
        logger = Logger.getLogger(entityClass);
    }

    @Override
    public List<T> findAll() {

        EntityManager manager = factory.createEntityManager();
        TypedQuery<T> query = manager.createQuery("SELECT e FROM " +
                entityClass.getSimpleName() + " e", entityClass);

        try {
            List<T> list = query.getResultList();
            logger.info("Finding All " + entityClass.getSimpleName());
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
                entityClass.getSimpleName() + " e", entityClass);

        try {
            query.setFirstResult(offset);
            query.setMaxResults(offset + length);
            List<T> list = query.getResultList();
            logger.info("Finding All " + entityClass.getSimpleName() +
                    " from " + offset + " to " + (offset + length));
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

        T obj;

        EntityManager manager = factory.createEntityManager();

        try {
            obj = manager.find(entityClass, id);
            logger.info("Finding " + entityClass.getSimpleName() +
                    " with ID = " + id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            manager.close();
        }
        return obj;

    }

    @Override
    public T remove(ID id) {

        T obj;

        EntityManager manager = factory.createEntityManager();
        EntityTransaction trans = manager.getTransaction();

        try {
            obj = manager.find(entityClass, id);
            if (obj != null) {
                trans.begin();
                manager.remove(obj);
                logger.info("Deleting " + entityClass.getSimpleName() +
                        " with ID = " + id);
                trans.commit();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            trans.rollback();
            return null;
        } finally {
            manager.close();
        }
        return obj;

    }

    @Override
    public T update(T entity) {

        EntityManager manager = factory.createEntityManager();
        EntityTransaction trans = manager.getTransaction();

        String entityName = entityClass.getSimpleName();

        try {
            ID id = (ID) entityClass.getMethod("getId").invoke(entity);
            trans.begin();
            if (id == null || id.equals(0)) {
                manager.persist(entity);
                logger.info("Inserting new " + entityName);
            } else {
                entity = manager.merge(entity);
                logger.info("Updating existing " + entityName);
            }
            trans.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            trans.rollback();
            return null;
        } finally {
            manager.close();
        }
        return entity;

    }

}

package hibernate.dao;

import hibernate.model.Base;
git
import org.apache.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class DaoUtilH2Db {

    private static final Logger LOG = Logger.getLogger(DaoUtilH2Db.class);

    public static Base create(Base entity, EntityManagerFactory factory) {

        String clsName = entity.getClass().getSimpleName();

        LOG.info("create new " + clsName);
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(entity);
            transaction.commit();
            LOG.info(clsName + " was saved");
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(clsName + " was not saved", e);
        } finally {
            manager.close();
        }

        return entity;
    }

    public static Base remove(Class cls, Integer id, EntityManagerFactory factory) {

        String clsName = cls.getSimpleName();

        LOG.info("delete " + clsName);
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        Object entity = manager.find(cls, id);

        try {
            transaction.begin();
            manager.remove(entity);
            transaction.commit();
            LOG.info(clsName + " was deleted");
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(clsName + " was not removed", e);
        } finally {
            manager.close();
        }

        return (Base) entity;
    }

    public static Base find(Class cls, Integer id, EntityManagerFactory factory) {

        String clsName = cls.getSimpleName();

        LOG.info("find by id " + clsName);

        EntityManager manager = factory.createEntityManager();
        //EntityTransaction transaction = manager.getTransaction();

        try {
            return (Base) manager.find(cls, id);
        } finally {
            manager.close();
        }
    }

    public static List<?> findAll(Class cls, EntityManagerFactory factory){

        String clsName = cls.getSimpleName();

        LOG.info("get all " + clsName);

        EntityManager manager = factory.createEntityManager();

        return manager.createQuery("SELECT e FROM " + clsName + " e").getResultList();

    }

    public static Base findByName(Class cls, String name, EntityManagerFactory factory){

        String clsName = cls.getSimpleName();

        LOG.info("get all " + clsName);

        EntityManager manager = factory.createEntityManager();

        List result = manager.createQuery("SELECT e FROM " + clsName + " e").getResultList();

        return (Base) result.get(0);

    }


    public static List<?> findAll(Class cls, EntityManagerFactory factory, int offset, int length){

        String clsName = cls.getSimpleName();

        LOG.info("get all " + clsName);

        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT e FROM " + clsName + " e");

        query.setMaxResults(length);
        query.setFirstResult(offset);

        return query.getResultList();

    }

    public static Base update(Class cls, Base entity, EntityManagerFactory factory) {

        String clsName = cls.getSimpleName();

        LOG.info("Update " + clsName);

        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            Base persistedEntity = (Base) manager.find(cls, entity.getId());
            transaction.begin();
            persistedEntity.update(entity);
            manager.merge(persistedEntity);
            transaction.commit();
            return persistedEntity;
        } finally {
            LOG.info(clsName + " was updated by");
            manager.close();
        }
    }

    public static boolean removeAll(Class cls, EntityManagerFactory factory) {

        String clsName = cls.getSimpleName();

        LOG.info("delete all " + clsName);

        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.createQuery("DELETE FROM " + clsName).executeUpdate();
            transaction.commit();

            return true;
        } finally {
            manager.close();
        }
    }
}

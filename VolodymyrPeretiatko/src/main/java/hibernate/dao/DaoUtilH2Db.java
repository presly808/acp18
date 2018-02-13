package hibernate.dao;

import hibernate.model.Base;


import hibernate.model.City;
import org.apache.log4j.Logger;
import org.hibernate.query.criteria.internal.CriteriaUpdateImpl;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

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
            LOG.info(clsName + " was not found by id" + id);
            manager.close();
        }
    }

    public static List<?> findAll(Class cls, EntityManagerFactory factory){

        String clsName = cls.getSimpleName();

        LOG.info("get all " + clsName);

        EntityManager manager = factory.createEntityManager();

        return manager.createQuery("SELECT e FROM " + clsName + " e").getResultList();

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

}

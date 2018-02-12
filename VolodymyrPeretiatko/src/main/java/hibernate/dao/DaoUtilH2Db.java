package hibernate.dao;

import hibernate.model.Base;


import hibernate.model.City;
import org.apache.log4j.Logger;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DaoUtilH2Db {

    public static Base create(Base entity, EntityManagerFactory factory, Logger LOG) {

        String clsName = entity.getClass().getSimpleName();

        LOG.info("create new " + clsName);
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist((City) entity);
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

    public static Base remove(Class cls, Integer id, EntityManagerFactory factory, Logger LOG) {

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
            LOG.error(clsName + " was not saved", e);
        } finally {
            manager.close();
        }

        return (Base) entity;
    }

}

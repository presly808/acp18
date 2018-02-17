package hibernate.dao.exclude;

import hibernate.dao.Dao;
import hibernate.model.City;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class CityDaoImpl implements Dao<City, Integer> {
    private static final Logger LOGGER = Logger.getLogger(Dao.class);
    private EntityManagerFactory factory;
    private EntityManager manager;

    public CityDaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public City create(City entity) {
        manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("can not create entity");

        } finally {
            manager.close();
        }

        return entity;
    }

    @Override
    public List<City> findAll() {
        manager = factory.createEntityManager();
        ;
        TypedQuery<City> query;

        try {
            query = manager.createQuery("SELECT u FROM City u", City.class);
            return query.getResultList();

        } finally {
            manager.close();
        }
    }

    @Override
    public List<City> findAll(int offset, int length) {
        manager = factory.createEntityManager();
        ;
        TypedQuery<City> query;

        try {
            query = manager.createQuery("SELECT c FROM City c", City.class);
            query.setFirstResult(offset);
            query.setMaxResults(length);
            return query.getResultList();

        } finally {
            manager.close();
        }
    }

    @Override
    public City find(Integer id) {
        manager = factory.createEntityManager();
        try {
            City res = manager.find(City.class, id);
            if (res == null) LOGGER.error("This city is not in the database, id: " + id);
            return res;

        } finally {
            manager.close();
        }
    }

    @Override
    public City remove(Integer id) {
        manager = factory.createEntityManager();
        try {
            City removedUser = manager.find(City.class, id);
            manager.remove(id);
            return removedUser;

        } finally {
            manager.close();
        }
    }

    @Override
    public City update(City entity) {
        manager = factory.createEntityManager();
        City res;
        try {
            manager.getTransaction().begin();
            res = manager.merge(entity);
            manager.getTransaction().commit();

        } finally {
            manager.close();
        }

        return res;
    }
}

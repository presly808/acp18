package hibernate.dao;

import hibernate.model.City;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class CityDao implements Dao<City,Integer> {

    private EntityManagerFactory factory;

    public CityDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public List findAll() {

        EntityManager manager = factory.createEntityManager();

        try {

            TypedQuery<City> query = manager.createQuery("SELECT * FROM cities", City.class);

            return query.getResultList();
        } finally {
            manager.close();
        }
    }

    @Override
    public List findAll(int offset, int length) {
        EntityManager manager = factory.createEntityManager();

        try {

            TypedQuery<City> query = manager.createQuery("SELECT * FROM cities", City.class);
            query.setMaxResults(length);
            query.setFirstResult(offset);

            return query.getResultList();
        } finally {
            manager.close();
        }

    }

    @Override
    public City find(Integer integer) {

        EntityManager manager = factory.createEntityManager();

        try {

            TypedQuery<City> allUsers = manager.createQuery("SELECT * FROM cities", City.class);

            return manager.find(City.class, integer);
        } finally {
            manager.close();
        }
    }

    @Override
    public City remove(Integer integer) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            City city = manager.find(City.class, integer);
            manager.remove(city);
            transaction.commit();
            return city;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public City update(City entity) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            City city = manager.find(City.class, entity.getId());
            City oldDepartment = (City) city.clone();
            manager.merge(city);
            transaction.commit();
            return oldDepartment;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public City create(City entity) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        } finally {
            manager.close();
        }

    }
}

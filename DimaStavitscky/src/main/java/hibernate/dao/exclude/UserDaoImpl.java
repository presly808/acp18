package hibernate.dao.exclude;


import hibernate.dao.Dao;
import hibernate.model.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoImpl implements Dao<User, Integer> {

    private static final Logger LOGGER = Logger.getLogger(Dao.class);
    private EntityManagerFactory factory;
    private EntityManager manager;

    public UserDaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public User create(User entity) {
        manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(entity);
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            LOGGER.error("can not create entity");

        } finally {
            manager.close();
        }

        return entity;
    }

    @Override
    public List<User> findAll() {
        manager = factory.createEntityManager();

        TypedQuery<User> query;

        try {
            query = manager.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();

        } finally {
            manager.close();
        }
    }

    @Override
    public List<User> findAll(int offset, int length) {
        manager = factory.createEntityManager();
        ;
        TypedQuery<User> query;

        try {
            query = manager.createQuery("SELECT u FROM User u", User.class);
            query.setFirstResult(offset);
            query.setMaxResults(length);
            return query.getResultList();

        } finally {
            manager.close();
        }
    }

    @Override
    public User find(Integer id) {
        manager = factory.createEntityManager();
        try {
            User res = manager.find(User.class, id);
            if (res == null) LOGGER.error("This user is not in the database, id: " + id);
            return res;

        } finally {
            manager.close();
        }
    }

    @Override
    public User remove(Integer id) {
        manager = factory.createEntityManager();
        try {
            User removedUser = manager.find(User.class, id);
            manager.remove(id);
            return removedUser;

        } finally {
            manager.close();
        }
    }

    @Override
    public User update(User entity) {
        manager = factory.createEntityManager();
        User res;
        try {
            manager.getTransaction().begin();
            res = manager.merge(entity);
            manager.getTransaction().commit();

        } catch (Exception e) {
            LOGGER.error("user not in database");
            return null;

        } finally {
            manager.close();
        }

        return res;
    }
}

package hibernate.dao;

import hibernate.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDao implements Dao<User, Integer> {

    private EntityManagerFactory factory;

    public UserDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public List findAll() {

        EntityManager manager = factory.createEntityManager();

        try {

            TypedQuery<User> allUsers = manager.createQuery("SELECT * FROM users", User.class);

            return allUsers.getResultList();
        } finally {
            manager.close();
        }
    }

    @Override
    public List findAll(int offset, int length) {
        EntityManager manager = factory.createEntityManager();

        try {

            TypedQuery<User> allUsers = manager.createQuery("SELECT * FROM users", User.class);
            allUsers.setMaxResults(length);
            allUsers.setFirstResult(offset);

            return allUsers.getResultList();
        } finally {
            manager.close();
        }

    }

    @Override
    public User find(Integer integer) {

        EntityManager manager = factory.createEntityManager();

        try {

            TypedQuery<User> allUsers = manager.createQuery("SELECT * FROM users", User.class);

            return manager.find(User.class, integer);
        } finally {
            manager.close();
        }
    }

    @Override
    public User remove(Integer integer) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            User user = manager.find(User.class, integer);
            manager.remove(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public User update(User entity) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            User user = manager.find(User.class, entity.getId());
            User oldUser = (User) user.clone();
            manager.merge(user);
            transaction.commit();
            return oldUser;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public User create(User entity) {
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

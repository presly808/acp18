package servlets.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import servlets.dao.Dao;
import servlets.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class UserDao implements Dao<User, Integer> {

    @Autowired
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
    public User findByName(String name) {

        EntityManager manager = factory.createEntityManager();

        try {

            TypedQuery<User> founded = manager.createQuery("SELECT * FROM users WHERE users.name = :name", User.class);
            founded.setParameter("name", name);
            founded.setMaxResults(1);
            return founded.getSingleResult();
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

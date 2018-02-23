package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Component
public class UserDao implements IUserDao {

    @Autowired
    private EntityManagerFactory factory;

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void addUser(User user) {

        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.persist(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
        }
    }

    @Override
    public User findById(int id) {
        EntityManager manager = factory.createEntityManager();

        try {
            return manager.find(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        manager.close();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            User found = manager.find(User.class,id);
            if (found!=null) {
                manager.remove(found);
                transaction.commit();
            }else {
                System.out.println("no user with this id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
        }

    }
}


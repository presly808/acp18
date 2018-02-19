package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.model.MyUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by alex323glo on 18.02.18.
 */
@Component
public class MyUserDao implements IMyUserDao {

    @Autowired
    private EntityManagerFactory factory;

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public MyUser create(MyUser user) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(user);
            transaction.commit();

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
        }

        return null;
    }

    @Override
    public MyUser remove(int id) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            MyUser removedUser = manager.find(MyUser.class, id);
            manager.remove(removedUser);
            transaction.commit();

            return removedUser;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
        }

        return null;
    }

    @Override
    public MyUser find(int id) {
        EntityManager manager = factory.createEntityManager();

        try {
            return manager.find(MyUser.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            manager.close();
        }

        return null;
    }
}

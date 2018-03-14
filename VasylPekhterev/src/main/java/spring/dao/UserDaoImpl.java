package spring.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class UserDaoImpl implements IUserDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public User save(User user) {
        manager.persist(user);
        return user;
        /*EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }

        return null;*/
    }

    @Override
    @Transactional
    public User delete(int id) {
        User forRemove = manager.find(User.class, id);
        manager.remove(forRemove);
        return forRemove;
        /*EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            User forRemove = manager.find(User.class, id);
            manager.remove(forRemove);
            transaction.commit();
            return forRemove;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }

        return null;*/
    }

    @Override
    @Transactional
    public User findById(int id) {
        //EntityManager manager = entityManagerFactory.createEntityManager();
        return manager.find(User.class, id);
    }
}

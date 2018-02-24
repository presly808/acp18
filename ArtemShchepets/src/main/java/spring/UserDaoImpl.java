package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

@Component
public class UserDaoImpl implements UserDao{

    @Autowired
    private EntityManagerFactory managerFactory;


    public UserDaoImpl() {
    }

    public EntityManagerFactory getManagerFactory() {
        return managerFactory;
    }

    public void setManagerFactory(EntityManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Override
    public User save(User user) {
        EntityManager manager = managerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(user);
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
    public User delete(int id) {

        EntityManager manager = managerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            User user = manager.find(User.class, id);
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
    public User find(int id) {
        EntityManager manager = managerFactory.createEntityManager();

        try {

            TypedQuery<User> allUsers = manager.createQuery("SELECT * FROM users", User.class);

            return manager.find(User.class, id);
        } finally {
            manager.close();
        }
    }


}

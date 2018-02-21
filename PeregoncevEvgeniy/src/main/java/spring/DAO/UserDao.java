package spring.DAO;

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

    public UserDao() {
    }


    @Override
    public void register(User user) {

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
}

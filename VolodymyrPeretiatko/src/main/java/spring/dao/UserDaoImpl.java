package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import spring.dao.UserDao;
import spring.model.User;

import javax.persistence.*;

@Repository
public class UserDaoImpl implements UserDao {

    private EntityManagerFactory factory;

    public UserDaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public User save(User user) {

        EntityManager entityManager = factory.createEntityManager();

        try {
            entityManager.persist(user);
        } catch (Exception e) {

        }
        return user;
    }

    @Override
    public User delete(int id) {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }
}

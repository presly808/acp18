package spring.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component(value = "userDaoBean")
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager manager;


    @Override
    @Transactional
    public User create(User user) {

        manager.persist(user);
        return user;
    }

    @Override
    @Transactional
    public User delete(int id) {
        User deleted = manager.find(User.class, id);
        manager.remove(deleted);
        return deleted;
    }

    @Override
    @Transactional
    public User find(int id) {
        User find = manager.find(User.class, id);
        return find;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        List<User> users = manager.createQuery("select u from User u", User.class).getResultList();
        return users;
    }

}

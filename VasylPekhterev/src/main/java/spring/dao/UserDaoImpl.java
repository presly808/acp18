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
    public User save(User user) {
        manager.persist(user);
        return user;
    }

    @Override
    public User delete(int id) {
        User forRemove = manager.find(User.class, id);
        manager.remove(forRemove);
        return forRemove;
    }

    @Override
    public User findById(int id) {
        return manager.find(User.class, id);
    }
}

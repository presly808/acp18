package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.model.User;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManagerFactory factory;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public User remove(int id) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

}

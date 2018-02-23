package spring.dao;

import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.model.User;

import javax.persistence.*;

@Component
//@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public User save(User userForSave) {

        entityManager.persist(userForSave);

        return userForSave;
    }

    @Override
    @Transactional
    public User delete(int id) {

        User user = entityManager.find(User.class, id);

        if (user != null) entityManager.remove(user);

        return user;
    }

    @Override
    @Transactional
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

}

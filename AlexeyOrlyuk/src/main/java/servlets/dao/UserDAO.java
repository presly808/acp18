package servlets.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import servlets.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by alex323glo on 24.02.18.
 */
@Component
public class UserDAO extends AbstractUserDAO {

    private static Logger LOGGER = Logger.getLogger(UserDAO.class);

    @PersistenceContext
    private EntityManager manager;

    @Override
    public User create(User user) {
        LOGGER.info("trying to persist new User to DB");

        manager.persist(user);
        return user;
    }

    @Override
    public User findById(int id) {
        LOGGER.info("trying to persist new User to DB");

        return manager.find(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        LOGGER.info("trying to persist new User to DB");

        try {
            return manager.createQuery("SELECT u FROM User u WHERE u.email=:requestedEmail", User.class)
                    .setParameter("requestedEmail", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOGGER.error("can't find registered User (email: \"" + email + "\")");
            return null;
        }
    }

    @Override
    public User update(User user) {
        LOGGER.info("trying to update User in DB");

        User oldUser = manager.find(User.class, user.getId()).clone();
        manager.merge(user);
        return oldUser;
    }

    @Override
    public User delete(int id) {
        LOGGER.info("trying to delete existent User from DB");

        User removedUser = manager.find(User.class, id);
        manager.remove(removedUser);
        return removedUser;
    }

    @Override
    public List<User> listAll() {
        LOGGER.info("trying to list all Users from DB");

        return manager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public List<User> listAllWithLimit(int offset, int count) {
        LOGGER.info("trying to list (with limit) all Users from DB");

        return manager.createQuery("SELECT u FROM User u", User.class)
                .setFirstResult(offset).setMaxResults(count).getResultList();
    }

    @Transactional
    @Override
    public List<User> removeAllUsers() {
        LOGGER.info("trying to remove all Users from DB");

        List<User> userList = listAll();
        userList.forEach(manager::remove);
        return userList;
    }
}

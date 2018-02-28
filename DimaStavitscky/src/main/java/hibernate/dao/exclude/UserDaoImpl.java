package hibernate.dao.exclude;

import hibernate.dao.Dao;
import hibernate.dao.UserDao;
import hibernate.exception.exclude.AppException;
import hibernate.model.User;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(Dao.class);

    @PersistenceContext
    private EntityManager manager;

    @Override
    public User create(User entity) {
        int id = entity.getId();
        if(id == 0 || manager.find(entity.getClass(), id) == null) {
            manager.persist(entity);

        } else {
            LOGGER.error("This user already exists in the database, id:" + id);
        }
        return entity;
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = manager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public List<User> findAll(int offset, int length) {
        TypedQuery<User> query = manager.createQuery("SELECT u FROM User u", User.class);
        query.setFirstResult(offset);
        query.setMaxResults(length);

        return query.getResultList();
    }

    @Override
    public User find(Integer id) {
        User res = manager.find(User.class, id);
        if (res == null) LOGGER.error("This user is not in the database, id: " + id);

        return res;
    }

    @Override
    public User remove(Integer id) {
        User removedUser = manager.find(User.class, id);
        manager.remove(id);

        return removedUser;
    }

    @Override
    public User update(User entity) {
        return manager.merge(entity);
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManager() throws AppException {
        Map<User, List<User>> resMap = new HashMap<>();
        List<User> users = findAll();

        for (User user : users) {
            TypedQuery<User> query =
                    manager.createQuery("SELECT u FROM User u " +
                            "WHERE u.manage = :manage", User.class);
            query.setParameter("manage", user);

            resMap.put(user, query.getResultList());
        }
        return resMap;
    }

    @Override
    public User getUserByLoginAndPass(String login, String pass) throws IllegalArgumentException {
            TypedQuery<User> query = manager.createQuery("SELECT u FROM User u " +
                    "WHERE u.login = :login AND u.pass = :pass", User.class);

            return query.getSingleResult();


    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagerAndOrderByCity(String city) throws AppException {
        Map<User, List<User>> resMap = new HashMap<>();
        List<User> users = findAll();

        for (User user : users) {
            TypedQuery<User> query =
                    manager.createQuery("SELECT u FROM User u " +
                            "WHERE u.manage = :manage AND u.city.name = :city", User.class);
            query.setParameter("manage", user);
            query.setParameter("city", city);

            List<User> res = query.getResultList();
            if(res != null) resMap.put(user, res);
        }
        return resMap;
    }
}

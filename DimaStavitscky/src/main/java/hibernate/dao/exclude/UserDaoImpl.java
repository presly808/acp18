package hibernate.dao.exclude;

import hibernate.dao.Dao;
import hibernate.dao.UserDao;
import hibernate.exception.exclude.AppException;
import hibernate.model.User;
import hibernate.utils.exclude.CrudOperationsUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            CrudOperationsUtils.create(entity, manager);

        } else {
            LOGGER.error("It is impossible to create a user, " +
                    "the user with this id already exists in the database, id: " + id);
        }
        return entity;
    }

    @Override
    public List<User> findAll() {
        /*return crudOperationsDao.findAll();*/
        return CrudOperationsUtils.findAll(User.class, manager);
    }

    @Override
    public List<User> findAll(int offset, int length) {
        return CrudOperationsUtils.findAll(offset, length, User.class, manager);
    }

    @Override
    public User find(Integer integer) {
        return CrudOperationsUtils.find(integer, User.class, manager);
    }

    @Override
    public User remove(Integer integer) {
        return CrudOperationsUtils.remove(integer, User.class, manager);
    }

    @Override
    public User update(User entity) {
        return CrudOperationsUtils.update(entity, entity.getId(), manager);
    }

    @Override
    public Integer deleteTable() {
        return manager.createQuery("DELETE FROM User").executeUpdate();
    }

    @Override
    public User getUserByLoginAndPass(String login, String pass) throws IllegalArgumentException {
        try {
            TypedQuery<User> query = manager.createQuery("SELECT u FROM User u " +
                    "WHERE u.login = :login AND u.password = :pass", User.class);
            query.setParameter("login", login);
            query.setParameter("pass", pass);

            return query.getSingleResult();

        } catch (NoResultException e) {
            LOGGER.error("Incorrect login or password");
            throw e;
        }
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

    @Override
    public List<User> findByName(String name) {
        try {
            TypedQuery<User> query = manager.createQuery
                    ("SELECT u FROM User u WHERE u.name = :name", User.class);
            query.setParameter("name", name);
            return query.getResultList();

        } catch (NoResultException e) {
            LOGGER.error("No users found with this name: " + name);
            throw e;
        }
    }

    @Override
    public List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppException {
        try {
            TypedQuery<User> query = manager.createQuery
                    ("SELECT u FROM User u WHERE u.localDateTime BETWEEN :start AND :end", User.class);

            query.setParameter("start", start);
            query.setParameter("end", end);

            return query.getResultList();

        } catch (NoResultException e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LOGGER.error(String.format("No users found that registered between %s and %s",
                    start.format(formatter),
                    end.format(formatter)));
            throw e;
        }
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
}
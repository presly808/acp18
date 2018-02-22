package hibernate.dao;

import hibernate.model.User;
import hibernate.util.ActionWrapper;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Implementation of general DAO interface. Used to work
 * with stored User entity instances.
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see Dao
 */
public class UserDao implements Dao<User, Integer> {

    private EntityManagerFactory factory;

    private static final Logger LOGGER = Logger.getLogger(UserDao.class);
    private static final String SELECT_ALL_USERS_QUERY = "SELECT u FROM User u";

    public UserDao(EntityManagerFactory factory) {
        this.factory = factory;
        LOGGER.trace("create new instance of UserDao");
    }

    /**
     * Searches for all stored entity instances.
     *
     * @return List of stored instances, if operation was successful, or null,
     * if it wasn't.
     */
    @Override
    public List<User> findAll() {
        LOGGER.info("search for all Users (without limits)");
        return ActionWrapper.wrap(factory, new User(), ActionWrapper.NO_LIMIT)
                .execute((manager, entity, limit) -> manager
                        .createQuery(SELECT_ALL_USERS_QUERY, User.class)
                        .setMaxResults(limit)
                        .getResultList());
    }

    /**
     * Searches for all stored entity instances.
     * Shows limited result of search.
     *
     * @param offset start position for limited result List (all elements
     *               "before" offset will be not included into result List).
     * @param length number of elements after offset position (including),
     *               which will be included into result List.
     * @return limited List of stored instances, if operation was successful, or null,
     * if it wasn't.
     */
    @Override
    public List<User> findAll(int offset, int length) {
        if (offset < 0 || length < 0) {
            LOGGER.error("offset or/and length is less then 0");
            return null;
        }

        LOGGER.info("search for all Users (with limits)");
        return ActionWrapper.wrap(factory, new User(), length)
                .execute((manager, entity, limit) -> manager
                        .createQuery(SELECT_ALL_USERS_QUERY, User.class)
                        .setMaxResults(limit)
                        .setFirstResult(offset)
                        .getResultList());
    }

    /**
     * Searches for stored entity instance by its ID.
     *
     * @param integer target instance's ID (key).
     * @return needed instance, if operation was successful, or null,
     * if it wasn't or if such instance was not stored to DB.
     */
    @Override
    public User find(Integer integer) {
        if (integer == null || integer < 0) {
            LOGGER.error("integer is null or is less then 0");
            return null;
        }

        LOGGER.info("search for single User");
        return ActionWrapper.wrap(factory, new User())
                .execute((manager, entity) -> {
                    return manager.find(entity.getClass(), integer);
                });
    }

    /**
     * Removes from DB stored entity instance by its ID.
     *
     * @param integer target instance's ID (key).
     * @return removed instance, if operation was successful, or null,
     * if it wasn't or if such instance was not stored to DB.
     */
    @Override
    public User remove(Integer integer) {
        if (integer == null || integer < 0) {
            LOGGER.error("integer is null or is less then 0");
            return null;
        }

        LOGGER.info("remove single User");
        return ActionWrapper.wrap(factory, new User())
                .executeWithTransaction((manager, entity) -> {
                    User removed = manager.find(entity.getClass(), integer);
                    manager.remove(removed);
                    return removed;
                });
    }

    /**
     * Updates stored entity instance.
     * Note: it will not create new instance (will return null instead),
     * it can only update existent one!
     *
     * @param entity new variant of stored instance.
     * @return old variant of instance, if operation was successful, or null,
     * if it wasn't or if such instance was not stored to DB.
     */
    @Override
    public User update(User entity) {
        if (entity == null) {
            LOGGER.error("entity is null");
            return null;
        }

        LOGGER.info("update single User");
        return ActionWrapper.wrap(factory, entity)
                .executeWithTransaction((manager, wrapEntity) -> {
                    User old = manager.find(wrapEntity.getClass(), wrapEntity.getId()).clone();
                    manager.merge(entity);
                    return old;
                });
    }

    /**
     * Creates new entity instance (stores it to DB).
     *
     * @param entity new instance, which will be stored.
     * @return stored variant of instance (with assigned ID field), if operation
     * was successful, or null, if it wasn't.
     */
    @Override
    public User create(User entity) {
        if (entity == null) {
            LOGGER.error("entity is null");
            return null;
        }

        LOGGER.info("create and save new User");
        ActionWrapper.wrap(factory, entity).executeWithTransaction(EntityManager::persist);

        LOGGER.info("new User was successfully created");
        return ActionWrapper.wrap(factory, entity)
                .execute((manager, wrapEntity) -> {
                    return manager.find(wrapEntity.getClass(), wrapEntity.getId());
                });
    }
}

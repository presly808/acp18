package hibernate.dao;

import hibernate.model.Department;
import hibernate.util.ActionWrapper;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Implementation of general DAO interface. Used to work
 * with stored Department entity instances.
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see Dao
 */
public class DepartmentDao implements Dao<Department, Integer> {

    private EntityManagerFactory factory;

    private static final Logger LOGGER = Logger.getLogger(DepartmentDao.class);
    private static final String SELECT_ALL_DEPARTMENTS_QUERY = "SELECT d FROM Department d";

    public DepartmentDao(EntityManagerFactory factory) {
        this.factory = factory;
        LOGGER.trace("create new instance of DepartmentDao");
    }

    /**
     * Searches for all stored entity instances.
     *
     * @return List of stored instances, if operation was successful, or null,
     * if it wasn't.
     */
    @Override
    public List<Department> findAll() {
        LOGGER.info("search for all Departments (without limits)");
        return ActionWrapper.wrap(factory, new Department(), ActionWrapper.NO_LIMIT)
                .execute((manager, entity, limit) -> manager
                        .createQuery(SELECT_ALL_DEPARTMENTS_QUERY, Department.class)
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
    public List<Department> findAll(int offset, int length) {
        if (offset < 0 || length < 0) {
            LOGGER.error("offset or/and length is less then 0");
            return null;
        }

        LOGGER.info("search for all Departments (with limits)");
        return ActionWrapper.wrap(factory, new Department(), length)
                .execute((manager, entity, limit) -> manager
                        .createQuery(SELECT_ALL_DEPARTMENTS_QUERY, Department.class)
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
    public Department find(Integer integer) {
        if (integer == null || integer < 0) {
            LOGGER.error("integer is null or is less then 0");
            return null;
        }

        LOGGER.info("search for single Department");
        return ActionWrapper.wrap(factory, new Department())
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
    public Department remove(Integer integer) {
        if (integer == null || integer < 0) {
            LOGGER.error("integer is null or is less then 0");
            return null;
        }

        LOGGER.info("remove single Department");
        return ActionWrapper.wrap(factory, new Department())
                .executeWithTransaction((manager, entity) -> {
                    Department removed = manager.find(entity.getClass(), integer);
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
    public Department update(Department entity) {
        if (entity == null) {
            LOGGER.error("entity is null");
            return null;
        }

        LOGGER.info("update single Department");
        return ActionWrapper.wrap(factory, entity)
                .executeWithTransaction((manager, wrapEntity) -> {
                    Department old = manager.find(wrapEntity.getClass(), wrapEntity.getId()).clone();
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
    public Department create(Department entity) {
        if (entity == null) {
            LOGGER.error("entity is null");
            return null;
        }

        LOGGER.info("create and save new Department");
        ActionWrapper.wrap(factory, entity)
                .executeWithTransaction(EntityManager::persist);

        LOGGER.info("new Department was successfully created");
        return ActionWrapper.wrap(factory, entity)
                .execute((manager, wrapEntity) -> {
                    return manager.find(wrapEntity.getClass(), wrapEntity.getId());
                });
    }
}

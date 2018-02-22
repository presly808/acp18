package hibernate.dao;

import java.util.List;

/**
 * General DAO interface.
 *
 * @param <V> type of stored entity (VALUE).
 * @param <K> type of id (KEY) field of stored entity.
 *
 * @author alex323glo
 * @version 1.0
 */
public interface Dao<V,K> {

    /**
     * Searches for all stored entity instances.
     *
     * @return List of stored instances, if operation was successful, or null,
     * if it wasn't.
     */
    List<V> findAll();

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
    List<V> findAll(int offset, int length);

    /**
     * Searches for stored entity instance by its ID.
     *
     * @param id target instance's ID (key).
     * @return needed instance, if operation was successful, or null,
     * if it wasn't or if such instance was not stored to DB.
     */
    V find(K id);

    /**
     * Removes from DB stored entity instance by its ID.
     *
     * @param id target instance's ID (key).
     * @return removed instance, if operation was successful, or null,
     * if it wasn't or if such instance was not stored to DB.
     */
    V remove(K id);

    /**
     * Updates stored entity instance.
     * Note: it will not create new instance (will return null instead),
     * it can only update existent one!
     *
     * @param entity new variant of stored instance.
     * @return old variant of instance, if operation was successful, or null,
     * if it wasn't or if such instance was not stored to DB.
     */
    V update(V entity);

    /**
     * Creates new entity instance (stores it to DB).
     *
     * @param entity new instance, which will be stored.
     * @return stored variant of instance (with assigned ID field), if operation
     * was successful, or null, if it wasn't.
     */
    V create(V entity);

}

package hibernate.dao;

import hibernate.util.ActionWrapper;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by alex323glo on 13.02.18.
 */
public class DaoTestUtils {

    public static final String MYSQL_PERSISTENCE_UNIT = "hibernate-h2-unit";
    public static final String H2_PERSISTENCE_UNIT = "hibernate-h2-unit";

    public static final String SELECT_ALL_USERS_QUERY = "SELECT u from User u";
    public static final String SELECT_ALL_DEPARTMENTS_QUERY = "SELECT d from Department d";
    public static final String SELECT_ALL_CITIES_QUERY = "SELECT c from City c";

    /**
     * Removes all records of specified entity.
     *
     * @param entityType entity class.
     * @param entity entity instance.
     * @param specialQuery String query, which SELECTs all entity records.
     * @return true, if all records were successfully removed.
     */
    public static<T> boolean removeAndCheck(EntityManagerFactory factory, Class<T> entityType, T entity,
                                            String specialQuery) {
        ActionWrapper.wrap(factory, entity)
                .executeWithTransaction(((manager, wrapEntity) -> {
                    List<T> entityList =
                            manager.createQuery(specialQuery, entityType).getResultList();
                    entityList.forEach(manager::remove);
                }));


        List<T> emptyEntityList = ActionWrapper
                .wrap(factory, entity, ActionWrapper.NO_LIMIT)
                .execute((manager, wrapEntity, limit) -> manager
                        .createQuery(specialQuery, entityType)
                        .setMaxResults(limit)
                        .getResultList());

        return emptyEntityList.isEmpty();
    }

}

package hibernate.utils;

import hibernate.model.Base;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DaoUtils {
    private static final Logger LOGGER = Logger.getLogger(DaoUtils.class);

    public static<T extends Base> T createEntityExtendsBase(T entity, EntityManagerFactory factory){

        EntityManager manager;
        manager = factory.createEntityManager();
        int id = entity.getId();

        if(id == 0 || manager.find(entity.getClass(), id) == null) {
            try {
                manager.getTransaction().begin();
                manager.persist(entity);
                manager.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("can not create entity");

            } finally {
                manager.close();
            }

        } else {
            LOGGER.error("This entity already exists in the database, id:" + id);
        }
        return entity;
    }
}

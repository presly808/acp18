package hibernate.dao;

import hibernate.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class UserDao extends DaoImpl<User, Integer> {

    public UserDao(EntityManagerFactory factory) {
        super(factory);
    }

    public List<User> findByName(String name) {

        logger.info("Finding Users by name!");

        String queryUser = "SELECT u FROM User u " +
                "WHERE u.name = :name";

        EntityManager manager = factory.createEntityManager();
        Query resQuery = manager.createQuery(queryUser);
        resQuery.setParameter("name", name);
        try {
            return resQuery.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }

    public List<User> findInRange(double minSal, double maxSal) {

        logger.info("Finding Users salary range!");

        String queryUser = "SELECT u FROM User u " +
                "WHERE u.salary between :minSal AND :maxSal";

        EntityManager manager = factory.createEntityManager();
        Query resQuery = manager.createQuery(queryUser);
        resQuery.setParameter("minSal", minSal);
        resQuery.setParameter("maxSal", maxSal);
        try {
            return resQuery.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }


}

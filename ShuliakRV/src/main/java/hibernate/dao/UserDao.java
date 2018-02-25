package hibernate.dao;

import hibernate.model.Department;
import hibernate.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    public List<User> findByDate(LocalDateTime start, LocalDateTime end) {

        logger.info("Finding Users by date!");

        String queryUser = "SELECT u FROM User u " +
                "WHERE u.localDateTime between :start AND :end";

        EntityManager manager = factory.createEntityManager();
        Query resQuery = manager.createQuery(queryUser);
        resQuery.setParameter("start", start);
        resQuery.setParameter("end", end);
        try {
            return resQuery.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }

    public List getUsersGroupByDepartment() {

        logger.info("Grouping Users by dapartment!");

        String queryUser = "SELECT u.department, u FROM User u " +
                "GROUP by u.department";

        EntityManager manager = factory.createEntityManager();
        Query resQuery = manager.createQuery(queryUser);

        try {
            return resQuery.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            manager.close();
        }

    }

    public List getAvgSalaryGroupByDepartment() {

        logger.info("Getting Users avarage salary by dapartment!");

        String queryUser = "SELECT u.department, AVG(u.salary) " +
                "FROM User u " +
                "GROUP BY u.department";

        EntityManager manager = factory.createEntityManager();
        Query resQuery = manager.createQuery(queryUser);

        try {
            return resQuery.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }

    public List getUsersGroupByManagersAndOrderedThatLiveInCity(String city) {

        logger.info("Getting Users grouped by dapartment and ordered by city!");

        String queryUser = "SELECT u2.department, u1 " +
                "FROM User u1, User u2  WHERE u1.manage=u2.id" +
                "GROUP BY u2.department" +
                "HAVING u1.city.name = :city";

        EntityManager manager = factory.createEntityManager();
        Query resQuery = manager.createQuery(queryUser);
        resQuery.setParameter("city", city);

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

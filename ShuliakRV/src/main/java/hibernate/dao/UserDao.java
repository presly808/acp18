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

        logger.info("Finding User by name!");

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
}

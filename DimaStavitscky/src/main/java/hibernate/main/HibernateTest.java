package hibernate.main;

import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class HibernateTest {

    private static final Logger logger = Logger.getLogger(HibernateTest.class);

    public static void main(String[] args) {
        User user = new User(21, 3000, new Department(), new City());
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("hibernate-unit");

        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            logger.info("transaction beggin");
            transaction.begin();
            manager.persist(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

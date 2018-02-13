package hibernate.main;

import hibernate.dao.Dao;
import hibernate.dao.DaoImpl;
import hibernate.model.Base;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import org.apache.log4j.Logger;

import javax.persistence.*;

public class HibernateTest {

    private static final Logger logger = Logger.getLogger(HibernateTest.class);

    public static void main(String[] args) {
        Department dep1 = new Department("IT");
        Department dep2 = new Department("Director");

        City city1 = new City("Kiev");
        City city2 = new City("Pereyaslav");

        User user1 = new User("Kolia", 21, 3000, dep1, city1, null);
        User user2 = new User("Kolia", 21, 3000, dep2, city2, null);

        User user3 = new User("Kolia", 21, 3000, dep1, city1, null);
        User user4 = new User("Kolia", 21, 3000, dep2, city2, null);

        Base base = new User();


        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("hibernate-unit");

        DaoImpl dao = new DaoImpl(factory);

        User u = (User) dao.find(user1);

        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            logger.info("transaction beggin");
            transaction.begin();
            manager.persist(user1);
            manager.persist(user2);
            manager.persist(user3);
            manager.persist(user4);
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();

        }

        /*EntityManager manager1 = factory.createEntityManager();

        transaction.begin();
        User findUser = manager1.find(User.class, 4);
        System.out.println(findUser.toString());*/

        EntityManager manager2 = factory.createEntityManager();

        TypedQuery<User> query = manager2.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class);
        query.setParameter("name", "Kolia");
        System.out.println("start");
        query.getResultList().forEach(System.out::println);
        System.out.println("finish");

        manager.close();
        factory.close();
    }
}

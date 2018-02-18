package spring.service;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.dao.IMyUserDao;
import spring.dao.MyUserDao;
import spring.exception.AppException;
import spring.model.MyUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceTestWithXMLConfig {

    private static ApplicationContext context;
    private IMyUserService myUserService;
    private IMyUserDao myUserDao;

    private MyUser testMyUser;

    @BeforeClass
    public static void setUpClass() throws Exception {
        context = new ClassPathXmlApplicationContext("/spring-task/spring-context.xml");
    }

    @Before
    public void setUp() throws Exception {
        myUserDao = (MyUserDao) context.getBean("myUserDao");
        myUserService = (MyUserService) context.getBean("myUserService");

        testMyUser = myUserDao.create(new MyUser("Test_user"));
    }

    @After
    public void tearDown() throws Exception {
        EntityManagerFactory factory = context.getBean(EntityManagerFactory.class);
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            List<MyUser> existentUsers = manager.createQuery("SELECT mu FROM MyUser mu", MyUser.class).getResultList();
            System.out.println("tearDown(): Removing Users:");
            existentUsers.forEach(System.out::println);
            existentUsers.forEach(manager::remove);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
        }
    }

    @Test
    public void save() throws Exception {
        MyUser newUser = new MyUser("Alex");

        assertEquals(newUser, myUserService.save(newUser));
    }

    @Test(expected = AppException.class)
    public void saveWithException() throws Exception {
        MyUser duplicateUser = myUserService.save(new MyUser("dup"));

        myUserService.save(duplicateUser);
    }

    @Test
    public void delete() throws Exception {
        MyUser removedUser = testMyUser;

        assertEquals(removedUser, myUserService.delete(removedUser.getId()));
    }

    @Test(expected = AppException.class)
    public void deleteWithException() throws Exception {
        myUserService.delete(testMyUser.getId() + 10);
    }

    @Test
    public void findById() throws Exception {
        MyUser foundUser = testMyUser;

        assertEquals(foundUser, myUserService.findById(foundUser.getId()));
    }

    @Test(expected = AppException.class)
    public void findByIdWithException() throws Exception {
        myUserService.findById(testMyUser.getId() + 10);
    }
}
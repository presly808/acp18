package servlets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import servlets.exception.ServletAppException;
import servlets.model.ServUser;
import servlets.service.UserService;
import servlets.service.UserServiceImpl;

import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.Assert.*;

public class ServiceTest {

    private UserService service;
    private ApplicationContext context;

    @Before
    public void upClass() throws ServletAppException {
        context = new AnnotationConfigApplicationContext("servlets");
        service = context.getBean(UserServiceImpl.class);
        ServUser user1 = new ServUser();
        user1.setEmail("ivan@g.com");
        user1.setPass("1234");
        user1.setName("ivan");
        ServUser user2 = new ServUser();
        user2.setEmail("kolia@g.com");
        user2.setPass("12345");
        user2.setName("kolia");
        service.addUser(user1);
        service.addUser(user2);

    }

    @After
    public void dropTables() {
        EntityManagerFactory factory = (EntityManagerFactory) context.getBean("entityManagerFactoryBean");
        factory.close();
    }

    @Test
    public void loginTest() throws ServletAppException {
        ServUser user = service.login("ivan@g.com", "1234");
        assertNotNull(user);
        assertEquals(user.getName(), "ivan");
        assertEquals(user.getEmail(), "ivan@g.com");
    }

    @Test
    public void findAllTest() throws ServletAppException {
        ServUser user3 = new ServUser();
        user3.setEmail("max@g.com");
        user3.setPass("12345");
        user3.setName("max");
        service.addUser(user3);
        List<ServUser> users = service.allUsers();
        assertEquals(users.size() , 3);
        assertTrue(users.contains(user3));
    }

    @Test(expected = ServletAppException.class)
    public void negativeLoginTest() throws ServletAppException {
        service.addUser(new ServUser());
    }

}

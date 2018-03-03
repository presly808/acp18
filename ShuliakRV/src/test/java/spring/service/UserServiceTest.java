package spring.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.
        AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.exception.AppException;
import spring.model.User;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {

    private static ApplicationContext contextXml;
    private static ApplicationContext contextAnnotation;
    private static IUserService userServiceXml;
    private static IUserService userServiceAnnotation;

    @BeforeClass
    public static void createContext() throws Exception {
        contextXml = new ClassPathXmlApplicationContext("/spring-context.xml");
        contextAnnotation = new AnnotationConfigApplicationContext("spring");
    }

    @Before
    public void setUp() throws Exception {
        userServiceXml = contextXml.getBean(IUserService.class);
        userServiceAnnotation = contextAnnotation.getBean(IUserService.class);
    }

    @Test
    public void save() throws Exception {
        User userXml = userServiceXml.save(new User("Petya"));
        assertEquals(userXml.getName(), "Petya");
        User userAnnotation = userServiceAnnotation.save(new User("Petya"));
        assertEquals(userAnnotation.getName(), "Petya");
    }

    @Test
    public void delete() throws Exception {
        User userXml = userServiceXml.save(new User("Petya"));
        userXml = userServiceXml.delete(userXml.getId());
        assertEquals(userXml.getName(), "Petya");
        User userAnnotation = userServiceAnnotation.save(new User("Petya"));
        userAnnotation = userServiceAnnotation.delete(userAnnotation.getId());
        assertEquals(userAnnotation.getName(), "Petya");
    }

    @Test
    public void findById() throws Exception {
        User userXml = userServiceXml.save(new User("Petya"));
        userXml = userServiceXml.findById(userXml.getId());
        assertEquals(userXml.getName(), "Petya");
        User userAnnotation = userServiceAnnotation.save(new User("Petya"));
        userAnnotation = userServiceAnnotation.findById(userAnnotation.getId());
        assertEquals(userAnnotation.getName(), "Petya");
    }

    @Test
    public void findAll() throws Exception {
        User userXml = userServiceXml.save(new User("Petya"));
        List<User> list = userServiceXml.findAll();
        assertEquals(list.get(0).getName(), "Petya");
        userServiceXml.delete(userXml.getId());
        User userAnnotation = userServiceAnnotation.save(new User("Petya"));
        list = userServiceAnnotation.findAll();
        assertEquals(list.size(), 1);
    }

    @Test(expected = AppException.class)
    public void findAllWithException() throws Exception {
        userServiceXml.findAll();
    }

}
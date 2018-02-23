package spring.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.model.User;

import static org.junit.Assert.*;

public class UserServiceTest_XML {

    private static ClassPathXmlApplicationContext context;
    private IUserService service;
    private User testUser;

    @Before
    public void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext("/spring/spring-Hibernate.xml");
        service = (IUserService) context.getBean("service");
        testUser = new User("Kira");
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void save() {
        service.save(testUser);
        User expected = service.findById(2);
        assertNotNull(expected);
    }

    @Test
    public void deleteById() {
        service.deleteById(1);
        assertNull(service.findById(1));
    }

}
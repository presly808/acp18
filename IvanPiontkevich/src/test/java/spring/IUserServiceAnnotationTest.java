package spring;

import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.model.User;
import spring.sevice.IUserService;
import spring.sevice.IUserServiceImpl;

import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.Assert.*;

public class IUserServiceAnnotationTest {

    private ApplicationContext context;
    private IUserService service;

    @Before
    public void setUpClass(){
        context = new AnnotationConfigApplicationContext("spring");
        service = context.getBean(IUserServiceImpl.class);
        User user1 = new User();
        user1.setName("Ivan");
        User user2 = new User();
        user2.setName("Yulia");
        User user3 = new User();
        user3.setName("Anton");
        service.save(user1);
        service.save(user2);
        service.save(user3);
    }

    @After
    public void tearDown(){
        EntityManagerFactory factory = (EntityManagerFactory) context.getBean("entityManagerFactoryBean");
        factory.close();
    }

    @Test
    public void saveTest(){
        User user = new User();
        user.setName("Kolia");
        User savedUser = service.save(user);
        assertNotNull(savedUser.getId());
        assertEquals(savedUser.getName(), "Kolia");
    }

    @Test
    public void findByIdTest(){
        User find = service.findById(1);
        assertEquals(find.getName(), "Ivan");
    }

    @Test
    public void deleteTest(){
        User deleting = service.delete(1);
        List<User> users = service.findAll();
        assertEquals(users.size(), 2);
        assertEquals(deleting.getName(), "Ivan");
    }

    @Test
    public void findAllTest(){
        List<User> users = service.findAll();
        assertEquals(users.size() , 3);
    }

}

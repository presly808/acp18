package spring.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.exeption.AppUserException;
import spring.model.User;

import static org.junit.Assert.*;

public class UserServiceImplTest_XmlImpl {

    private static IUserService userService;
    private static User userPetr;

    @BeforeClass
    public static void init() throws Exception{
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("/META-INF/spring-context.xml");

        userService = ctx.getBean(UserServiceImpl.class);

        userPetr = new User("Petr");

        userService.save(new User("Test_1"));
        userService.save(new User("Test_2"));
        userService.save(new User("Test_3"));
    }

    @Test
    public void save() throws Exception {

        userPetr = userService.save(userPetr);
        assertTrue(userPetr.getId() != 0);

    }

    @Test
    public void delete() throws Exception {
        User u = userService.delete(3);
        assertTrue("Test_3".equals(u.getName()));
    }

    @Test
    public void findById() throws Exception {
        User u = userService.findById(2);
        assertTrue("Test_2".equals(u.getName()));
    }

    @Test(expected = AppUserException.class)
    public void findByIdExept() throws Exception {
        assertTrue("Test_2".equals(userService.findById(777).getName()));
    }


}
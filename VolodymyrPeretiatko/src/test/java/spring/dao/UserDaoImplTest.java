package spring.dao;


import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.model.User;

import static org.junit.Assert.*;

public class UserDaoImplTest {

    private static UserDao userDao;
    private static User userPetr;

    @BeforeClass
    public static void init(){
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("/META-INF/spring-context.xml");

        userDao = ctx.getBean(UserDao.class);

        userPetr = new User("Petr");
    }

    @Test
    public void save() throws Exception {

        userPetr = userDao.save(userPetr);

        assertTrue(userPetr.getId() != 0);
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void findById() throws Exception {

    }

}
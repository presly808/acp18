package spring.dao;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.model.User;

import static org.junit.Assert.*;

public class UserDaoImplTest_XmlImpl {

    private static UserDao userDao;
    private static User userPetr;

    @BeforeClass
    public static void init(){
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("/META-INF/spring-context.xml");

        userDao = ctx.getBean(UserDao.class);

        userPetr = new User("Petr");

        userDao.save(new User("Test_1"));
        userDao.save(new User("Test_2"));
        userDao.save(new User("Test_3"));
    }

    @Test
    public void save() throws Exception {

        userPetr = userDao.save(userPetr);
        assertTrue(userPetr.getId() != 0);

    }

    @Test
    public void delete() throws Exception {
        User u = userDao.delete(1);
        assertTrue("Test_1".equals(u.getName()));
    }

    @Test
    public void findById() throws Exception {
        User u = userDao.findById(2);
        assertTrue("Test_2".equals(u.getName()));
    }


}
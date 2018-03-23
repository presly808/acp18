package spring;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import spring.dao.UserDao;
import spring.dao.UserDaoImpl;
import spring.exception.NoUserFoundException;
import spring.model.User;
import spring.service.UserService;
import spring.service.UserServiceImpl;
import spring.test.Context;

import java.io.File;

// test

public class AspectXMLTest {

    static Context context = null;
    static User user = null;
    static UserDao dao = null;
    static UserService service = null;
    static File[] files = null;

    @BeforeClass
    public static void beforeClass() throws NoUserFoundException {
        context = new Context(new FileSystemXmlApplicationContext("src/main/java/spring/spring-context.xml"));
        user = context.getApplicationContext().getBean(User.class);;
        dao = context.getApplicationContext().getBean(UserDaoImpl.class);
        service = context.getApplicationContext().getBean(UserServiceImpl.class);
        files = new File(".").listFiles();

        for (File file : files) {
            System.out.println(file.getName());
        }

        service.login(user);
    }



    @Test
    public void testLogging() {
        boolean result = false;
        for (File file : files) {
            if (file.getName().equals("logging.log") && file.length() != 0) {
                result = true;
            }
        }

        Assert.assertTrue(result);
    }
}

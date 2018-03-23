package spring;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import spring.dao.UserDao;
import spring.dao.UserDaoImpl;
import spring.exception.NoUserFoundException;
import spring.model.User;
import spring.service.UserService;
import spring.service.UserServiceImpl;

// test

public class ServiceTest {

    static User user = null;
    static UserDao dao = null;
    static UserService service = null;

    @BeforeClass
    public static void beforeClass() {
        user = new User(1, "Kirill");
        dao = new UserDaoImpl(user);
        service = new UserServiceImpl(dao);
    }

    @Test
    public void testLogin() throws NoUserFoundException {
        Assert.assertEquals(dao.findById(user.getId()), service.login(user));
    }

    @Test
    public void testDelete() throws NoUserFoundException {
        Assert.assertEquals(dao.findById(user.getId()), service.delete(user));
    }
}

package spring;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import spring.dao.UserDao;
import spring.dao.UserDaoImpl;
import spring.exception.NoUserFoundException;
import spring.model.User;

// test

public class DaoTest {

    static User user = null;
    static UserDao dao = null;

    @BeforeClass
    public static void beforeClass() {
        user = new User(1, "Kirill");
        dao = new UserDaoImpl(user);
    }


    @Test
    public void testFindById() throws NoUserFoundException {
        String userById = dao.findById(user.getId()).toString();
        Assert.assertTrue(user.toString().equals(userById));
    }

    @Test
    public void testFindByName() throws NoUserFoundException {
        String userByName = dao.findByName(user.getName()).toString();
        Assert.assertTrue(user.toString().equals(userByName));
    }

    @Test
    public void testGetUserInfo() throws NoUserFoundException {
        Assert.assertTrue(user.toString().equals(dao.getUserInfo(user)));
    }
}

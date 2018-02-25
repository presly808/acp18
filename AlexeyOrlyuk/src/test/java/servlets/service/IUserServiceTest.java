package servlets.service;

import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import servlets.dao.AbstractUserDAO;
import servlets.exception.AuthorizationException;
import servlets.exception.RegistrationException;
import servlets.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IUserServiceTest {

    private static GenericApplicationContext applicationContext;
    private static IUserService userService;
    private static AbstractUserDAO userDAO;

    private List<User> testUserList;

    @BeforeClass
    public static void setUpContextBeforeClass() throws Exception {
        applicationContext = new AnnotationConfigApplicationContext("servlets");
        userService = applicationContext.getBean(IUserService.class);
        userDAO = applicationContext.getBean(AbstractUserDAO.class);
    }

    @AfterClass
    public static void tearDownContextAfterClass() throws Exception {
        applicationContext.close();
    }

    @Before
    public void setUp() throws Exception {
        testUserList = new ArrayList<>();
        testUserList.add(new User("alex@gmail.com", "11111111"));
        testUserList.add(new User("max@gmail.com", "22222222"));
        testUserList.add(new User("jack@gmail.com", "33333333"));
        for (User user: testUserList) {
            User registeredUser = userService.register(user.getEmail(), user.getPassword());
            user.setId(registeredUser.getId());
        }
    }

    @After
    public void tearDown() throws Exception {
        userDAO.removeAllUsers();
    }



    @Test
    public void register() throws Exception {
        User expectedUser = new User("serhii@gmail.com", "44444444");

        User actualRegisteredUser = userService.register(expectedUser.getEmail(), expectedUser.getPassword());

        expectedUser.setId(actualRegisteredUser.getId());
        assertEquals(expectedUser, actualRegisteredUser);
    }

    @Test(expected = RegistrationException.class)
    public void registerInvalidEmailWithException() throws Exception {
        userService.register("serhii_gmail.com", "44444444");
    }

    @Test(expected = RegistrationException.class)
    public void registerInvalidPasswordWithException() throws Exception {
        userService.register("serhii@gmail.com", "4");
    }

    @Test(expected = RegistrationException.class)
    public void registerDuplicateUserWithException() throws Exception {
        userService.register(testUserList.get(0).getEmail(), "44444444");
    }



    @Test
    public void login() throws Exception {
        User expectedUser = testUserList.get(0);

        User actualAuthorizedUser =
                userService.login(expectedUser.getEmail(), expectedUser.getPassword());

        assertEquals(expectedUser, actualAuthorizedUser);
    }

    @Test(expected = AuthorizationException.class)
    public void loginInvalidEmailWithException() throws Exception {
        userService.login("alex_gmail.com", "11111111");
    }

    @Test(expected = AuthorizationException.class)
    public void loginInvalidPasswordWithException() throws Exception {
        userService.login("alex@gmail.com", "1");
    }

    @Test(expected = AuthorizationException.class)
    public void loginUnregisteredEmailWithException() throws Exception {
        userService.login(testUserList.get(0).getEmail() + "bla-bla",
                testUserList.get(0).getPassword());
    }

    @Test(expected = AuthorizationException.class)
    public void loginUnregisteredPasswordWithException() throws Exception {
        userService.login(testUserList.get(0).getEmail(),
                testUserList.get(0).getPassword() + "bla-bla");
    }



    @Test
    public void listAllRegisteredUsers() throws Exception {
        List<User> expectedUserList = testUserList;

        List<User> actualRegisteredUserList = userService.listAllRegisteredUsers();

        assertEquals(expectedUserList, actualRegisteredUserList);
    }

}
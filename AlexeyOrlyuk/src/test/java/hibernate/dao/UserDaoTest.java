package hibernate.dao;

import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import hibernate.util.ActionWrapper;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserDaoTest extends DaoTest {

    private static EntityManagerFactory factory;
    private static Dao<User, Integer> userDao;

    private List<User> testUserList;

    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = Persistence.createEntityManagerFactory(H2_PERSISTENCE_UNIT);
        userDao = new UserDao(factory);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        factory.close();
    }

    @Before
    public void setUp() throws Exception {

        // Init records of "users", "departments" and "cities" tables (5 users, 1 department, 1 city, 1 manager (user)):
        Department department = new Department("test_department");
        City city = new City("test_city");
        User userManager = new User("TestUserManager", 40, 10000,
                department, city, null, LocalDateTime.now());

        testUserList = new ArrayList<>();
        testUserList.add(userManager);
        testUserList.add(new User("TestUser1", 21, 1000,
                department, city, userManager, LocalDateTime.now()));
        testUserList.add(new User("TestUser2", 22, 2000,
                department, city, userManager, LocalDateTime.now()));
        testUserList.add(new User("TestUser3", 23, 3000,
                department, city, userManager, LocalDateTime.now()));
        testUserList.add(new User("TestUser4", 24, 4000,
                department, city, userManager, LocalDateTime.now()));
        testUserList.add(new User("TestUser5", 25, 5000,
                department, city, userManager, LocalDateTime.now()));

        // Write records to DB (Note: it will update some entity instances' fields in List, such as id!!!):
        ActionWrapper.wrap(factory, city).executeWithTransaction(EntityManager::persist);
        ActionWrapper.wrap(factory, department).executeWithTransaction(EntityManager::persist);
        ActionWrapper.wrap(factory, new User()).executeWithTransaction((manager, entity) -> {
            testUserList.forEach(manager::persist);
        });
    }

    @After
    public void tearDown() throws Exception {
        boolean usersRemoved = removeAndCheck(factory, User.class, new User(), SELECT_ALL_USERS_QUERY);
        boolean citiesRemoved = removeAndCheck(factory, City.class, new City(), SELECT_ALL_CITIES_QUERY);
        boolean departmentsRemoved =
                removeAndCheck(factory, Department.class, new Department(), SELECT_ALL_DEPARTMENTS_QUERY);

        if (!usersRemoved || !departmentsRemoved || !citiesRemoved) {
            throw new Exception("Some test tables weren't cleaned up during tearDown() execution!");
        }
    }

    @Test
    public void findAll() throws Exception {
        List<User> expected = testUserList;
        List<User> actual = userDao.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void findAllWithOffsetAndLimit() throws Exception {
        int testStartPoint = 1;
        int testLimit = 3;

        List<User> expected = testUserList.subList(testStartPoint, testStartPoint + testLimit);
        List<User> actual = userDao.findAll(testStartPoint, testLimit);

        assertEquals(expected, actual);
    }

    @Test
    public void find() throws Exception {
        int testId = testUserList.get(0).getId();

        User expected = testUserList.get(0);
        User actual = userDao.find(testId);

        assertEquals(expected, actual);
    }

    @Test
    public void remove() throws Exception {
        int lastElementIndex = testUserList.size() - 1;
        int testId = testUserList.get(lastElementIndex).getId();

        User expected = testUserList.get(lastElementIndex);
        User actual = userDao.remove(testId);

        List<User> expectedList = testUserList.subList(0, lastElementIndex);
        List<User> actualList = ActionWrapper.wrap(factory, new User(), ActionWrapper.NO_LIMIT)
                .execute((manager, entity, limit) -> manager
                        .createQuery(SELECT_ALL_USERS_QUERY, User.class).setMaxResults(limit).getResultList());

        assertEquals(expected, actual);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void update() throws Exception {
        int testId = testUserList.get(0).getId();
        double testNewSalary = 1;

        User expectedOld = testUserList.get(0);
        User expectedNew = testUserList.get(0).clone();
        expectedNew.setSalary(testNewSalary);

        User actualOld = userDao.update(expectedNew);
        User actualNew = ActionWrapper.wrap(factory, actualOld).execute((manager, entity) -> {
            return manager.find(entity.getClass(), entity.getId());
        });

        assertEquals(expectedOld, actualOld);
        assertEquals(expectedNew, actualNew);
    }

    @Test
    public void create() throws Exception {
        User newUser = userDao.create(new User("NewUser", 30, 55, LocalDateTime.now()));

        List<User> expectedList = testUserList;
        expectedList.add(newUser);

        List<User> actualList = ActionWrapper.wrap(factory, new User(), ActionWrapper.NO_LIMIT)
                .execute((manager, entity, limit) -> manager
                        .createQuery(SELECT_ALL_USERS_QUERY, User.class).setMaxResults(limit).getResultList());

        assertEquals(expectedList, actualList);
    }
}
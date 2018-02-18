package hibernate.service;

import hibernate.dao.Dao;
import hibernate.dao.DaoImpl;
import hibernate.exception.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MainServiceTest {

    private static EntityManagerFactory managerFactory;
    private static EntityManager manager;
    private static Dao<City, Integer> cityDao;
    private static Dao<Department, Integer> departmentDao;
    private static Dao<User, Integer> userDao;
    private static MainService ms;

    @BeforeClass
    public static void init() {
        managerFactory = Persistence.createEntityManagerFactory("hibernate-unit");
        manager = managerFactory.createEntityManager();
        cityDao = new DaoImpl(manager, City.class);
        departmentDao = new DaoImpl(manager, Department.class);
        userDao = new DaoImpl(manager, User.class);
        ms = new MainServiceImpl(cityDao, departmentDao, userDao);
    }

    @AfterClass
    public static void close() {
        manager.close();
        managerFactory.close();
    }

    @Before
    public void setUp() {
        City kiev = new City("Kiev");
        City odessa = new City("Odessa");

        cityDao.create(kiev);
        cityDao.create(odessa);

        Department department1 = new Department("IT");
        Department department2 = new Department("QA");

        departmentDao.create(department1);
        departmentDao.create(department2);

        User user3 = new User( "Yura", 35, 1500, department2, kiev, null,
                LocalDateTime.of(1994, Month.APRIL, 15, 11, 30));
        User user1 = new User("Ivan", 30, 2500, department2, kiev, user3,
                LocalDateTime.of(1994, Month.APRIL, 15, 11, 30));
        User user2 = new User( "Oleg", 33, 3500, department2, odessa, user3,
                LocalDateTime.of(1995, Month.MAY, 15, 11, 30));
        User user4 = new User("Serhii", 22, 2500, department1, kiev, user3,
                LocalDateTime.of(1996, Month.MAY, 15, 11, 30));
        User user5 = new User("Olex", 24, 4500, department1, odessa, user3,
                LocalDateTime.of(1997, Month.MAY, 15, 11, 30));

        userDao.create(user3);
        userDao.create(user1);
        userDao.create(user2);
        userDao.create(user4);
        userDao.create(user5);
    }

    @After
    public void tearDown() {
        userDao.removeAll();
        departmentDao.removeAll();
        cityDao.removeAll();
    }

    @Test
    public void register() throws AppException {
        User testUser = new User("TestUser", 35, 1500, null, null, null);
        User actual = ms.register(testUser);
        assertThat(actual.getName(), equalTo("TestUser"));
        assertThat(actual.getAge(), equalTo(35));
    }

    @Test
    public void addDepartment() throws AppException {
        Department testDepartment = new Department("TestDepartment");
        Department actual = ms.addDepartment(testDepartment);
        assertThat(actual.getName(), equalTo("TestDepartment"));
    }

    @Test
    public void update() throws AppException {
        User oleg = ms.findByName("Oleg").get(0);
        User testUser = new User(oleg.getId(), "TestUser", 50, 1234, null, null, null);
        User actual = ms.update(testUser);
        assertThat(actual.getName(), equalTo("TestUser"));
        assertThat(actual.getAge(), equalTo(50));
        assertThat(actual.getSalary(), equalTo(1234.0));
    }


    @Test
    public void remove() throws AppException {
        User oleg = ms.findByName("Oleg").get(0);
        User testUser = new User(oleg.getId());
        User actual = ms.remove(testUser);
        assertThat(actual.getName(), equalTo("Oleg"));
        assertThat(actual.getSalary(), equalTo(3500.0));
        assertThat(actual.getAge(), equalTo(33));
    }


    @Test
    public void getUsersGroupByDepartment() throws AppException {
        Map<Department, List<User>> usersGroupByDepartment = ms.getUsersGroupByDepartment();
        assertThat(usersGroupByDepartment.keySet().size(), equalTo(2));
        List<User> valueList = usersGroupByDepartment.values().stream().
                flatMap(Collection::stream).collect(Collectors.toList());
        assertThat(valueList.size(), equalTo(5));
    }


    @Test
    public void getAvgSalaryGroupByDepartment() throws AppException {
        Map<Department, Double> usersGroupByDepartment = ms.getAvgSalaryGroupByDepartment();
        assertThat(usersGroupByDepartment.keySet().size(), equalTo(2));
        assertTrue(usersGroupByDepartment.containsValue(3500.0));
        assertTrue(usersGroupByDepartment.containsValue(2500.0));
    }


    @Test
    public void getUsersGroupByManagersAndOrderedThatLiveInKiev() throws AppException {
        Map<User, List<User>> usersGroupByManager = ms.getUsersGroupByManagersAndOrderedThatLiveInKiev();
        User yura = ms.findByName("Yura").get(0);
        assertThat(usersGroupByManager.get(yura).size(), equalTo(4));
    }

    @Test
    public void findByName() throws AppException {
        List<User> actual = ms.findByName("Oleg");
        assertThat(actual.size(), equalTo(1));
        User user = actual.get(0);
        assertThat(user.getName(), equalTo("Oleg"));
        assertThat(user.getSalary(), equalTo(3500.0));
        assertThat(user.getAge(), equalTo(33));
    }

    @Test
    public void findInRange() throws AppException {
        List<User> actual = ms.findInRange(2000,4000);
        assertThat(actual.size(), equalTo(3));
        List<String> names = actual.stream().map(User::getName).collect(Collectors.toList());
        assertTrue(names.contains("Ivan"));
        assertTrue(names.contains("Oleg"));
        assertTrue(names.contains("Serhii"));
    }

    @Test
    public void findByDate() throws AppException {
        List<User> actual = ms.findByDate(
                LocalDateTime.of(1995, Month.JANUARY, 1, 11, 30),
                LocalDateTime.of(1997, Month.JANUARY, 1, 11, 30));
        assertThat(actual.size(), equalTo(2));
    }
}
package hibernate.service;

import hibernate.dao.Dao;
import hibernate.dao.DaoDepartment;
import hibernate.dao.DaoUser;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import org.junit.*;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MainServiceImplTest {

    private static MainServiceImpl service;
    private static EntityManagerFactory factory;

    //Test entity's
    private static City cityKiev = new City("Kiev");

    private static Department departIT = new Department("IT");
    private static Department departSB = new Department("SB");

    private static User userVova = new User("Vova", 25, 1000.00, departIT, cityKiev, null, LocalDateTime.of(2000, 10, 10, 0, 0));
    private static User userPetr = new User("Petr", 27, 2000.00, departSB, cityKiev, null, LocalDateTime.of(2010, 10, 10, 0, 0));

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("hibernate-unit");
        service = new MainServiceImpl(new DaoUser(factory), new DaoDepartment(factory));
    }

    @After
    public void tearDown() throws Exception {
        factory.close();
        factory = null;
        service = null;
    }

    @Test
    public void register() throws Exception {
        User actual = service.register(userVova);
        assertEquals(userVova, actual);
    }

    @Test
    public void addDepartment() throws Exception {
        Department actual = service.addDepartment(departIT);
        assertEquals(departIT, actual);
    }

    @Test
    public void update() throws Exception {
        User actual = service.register(userVova);
        actual.setName("Kaban");
        service.update(actual);

        System.out.println(actual);
        User actual2 = service.findUserById(1);
        assertNotEquals(actual, actual2);

    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void getUsersGroupByDepartment() throws Exception {
    }

    @Test
    public void getAvgSalaryGroupByDepartment() throws Exception {
    }

    @Test
    public void getUsersGroupByManagersAndOrderedThatLiveInKiev() throws Exception {
    }

    @Test
    public void findByName() throws Exception {
    }

    @Test
    public void findInRange() throws Exception {
    }

    @Test
    public void findByDate() throws Exception {
    }

}
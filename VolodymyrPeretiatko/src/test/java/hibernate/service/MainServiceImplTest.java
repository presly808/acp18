package hibernate.service;

import hibernate.dao.DaoCityImpl;
import hibernate.dao.DaoDepartmentImpl;
import hibernate.dao.DaoUserImpl;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import org.junit.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MainServiceImplTest {

    private static MainServiceImpl service;
    private static EntityManagerFactory factory;

    //Test entity's
    private static City cityKiev, cityLviv;

    private static Department departIT;
    private static Department departSB;

    private static User userVova;
    private static User userPetr;

    @BeforeClass
    public static void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("hibernate-unit");

        DaoUserImpl daoUser = new DaoUserImpl(factory);
        DaoDepartmentImpl daoDepartment = new DaoDepartmentImpl(factory);
        DaoCityImpl daoCity = new DaoCityImpl(factory);

        service = new MainServiceImpl(daoUser, daoDepartment);

        cityKiev = daoCity.create(new City("Kiev"));
        cityLviv = daoCity.create(new City("Lviv"));

        departIT = daoDepartment.create(new Department("IT"));
        departSB = daoDepartment.create(new Department("SB"));

        userVova = new User("Vova", 25, 1000.00, departIT, cityKiev, null, LocalDateTime.of(2000, 10, 10, 0, 0));
        userPetr = new User("Petr", 27, 2000.00, departSB, cityKiev, null, LocalDateTime.of(2010, 10, 10, 0, 0));

        daoUser.create(userVova);
        daoUser.create(userPetr);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        factory.close();
        factory = null;
        service = null;
    }

    @Test
    public void register() throws Exception {

        User userTest = new User("Test", 40, 1700.00, departIT, cityLviv, null, LocalDateTime.of(2000, 10, 10, 0, 0));

        userTest = service.register(userTest);
        assertTrue(userTest.getId() != 0);

        System.out.println();

    }

    @Test
    public void addDepartment() throws Exception {
        Department department = new Department("HR");
        department = service.addDepartment(departIT);
        assertTrue(department.getId() != 0);
    }

    @Test
    public void update() throws Exception {

        userVova.setName("Kaban");
        service.update(userVova);

        assertTrue("Kaban".equals(service.findUserById(userVova.getId()).getName()));

    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void getUsersGroupByDepartment() throws Exception {
    }

    @Test
    public void getAvgSalaryGroupByDepartment() throws Exception {
        service.getAvgSalaryGroupByDepartment();
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
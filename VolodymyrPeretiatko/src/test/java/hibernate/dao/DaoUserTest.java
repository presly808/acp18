package hibernate.dao;

import hibernate.model.Base;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import org.junit.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class DaoUserTest {

    private static EntityManagerFactory factory;
    private static DaoUserImpl dao;


    private static User userVova;
    private static User userPetr;
    private static User userTest;

    @BeforeClass
    public static void init() throws Exception {
        factory = Persistence.createEntityManagerFactory("hibernate-unit");
        dao = new DaoUserImpl(factory);

        DaoDepartmentImpl daoDepartment = new DaoDepartmentImpl(factory);
        DaoCityImpl daoCity = new DaoCityImpl(factory);

        Department departmentIT = daoDepartment.create(new Department("IT"));
        Department departmentSB = daoDepartment.create(new Department("SB"));

        City cityKiev = daoCity.create(new City("Kiev"));

        userVova = new User("Vova", 25, 1000.00, departmentIT, cityKiev, null, LocalDateTime.of(2000, 10, 10, 0, 0));
        userPetr = new User("Petr", 27, 2000.00, departmentSB, cityKiev, null, LocalDateTime.of(2010, 10, 10, 0, 0));
        userTest = new User("Test", 27, 2000.00, departmentSB, cityKiev, null, LocalDateTime.of(2010, 10, 10, 0, 0));

        userVova = dao.create(userVova);
        userPetr = dao.create(userPetr);

    }

    @AfterClass
    public static void down() throws Exception {
        factory.close();
        dao = null;
    }

    @Test
    public void create() throws Exception {

        Base newEntity = (Base) dao.create(userTest);
        Assert.assertTrue(newEntity.getId() != 0);
    }

    @Test
    public void findAll() throws Exception {
        Assert.assertTrue(dao.findAll().size() == 2);
    }

    @Test
    public void findAllparams() throws Exception {
        Assert.assertTrue(dao.findAll(0,1).size() == 1);
    }

    @Test
    public void find() throws Exception {
        Assert.assertTrue(((Base)dao.find(4)).getId() == 4);
    }

    @Test
    public void remove() throws Exception {
        dao.remove(5);
        Assert.assertTrue(dao.findAll().size() == 2);
    }

    @Test
    public void update() throws Exception {

        userVova.setName("Updated");
        dao.update(userVova);
        Base foundEntity = dao.find(userVova.getId());

        Assert.assertTrue("Updated".equals(userVova.getName()));
        Assert.assertTrue(dao.findAll().contains(foundEntity));

    }
}
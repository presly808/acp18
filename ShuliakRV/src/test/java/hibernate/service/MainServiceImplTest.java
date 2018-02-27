package hibernate.service;

import hibernate.model.*;
import org.apache.log4j.Logger;
import org.junit.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class MainServiceImplTest {

    private static MainService service;
    private static EntityManagerFactory factory;
    private final static Logger logger = Logger.
            getLogger(MainServiceImplTest.class);

    @Before
    public void createtables() {
        factory = Persistence.createEntityManagerFactory("hibernate-unit");
        service = new MainServiceImpl(factory);
    }

    @After
    public void dropTables() {
        factory.close();
    }

    @Test
    public void register() throws Exception {

        City kiev = new City();
        kiev.setName("Kiev");

        City odessa = new City();
        odessa.setName("Oddessa");

        Department department1 = new Department();
        department1.setName("IT");


        Department department2 = new Department();
        department2.setName("QA");

        User user3 = new User("Yura", 35, 1500, department2, kiev, null,
                LocalDateTime.now());
        User user1 = new User("Ivan", 30, 2500, department2, kiev, user3,
                LocalDateTime.now());
        User user2 = new User("Oleg", 33, 3500, department2, odessa, user3,
                LocalDateTime.now());
        User user4 = new User("Serhii", 22, 2500, department1, kiev, user3,
                LocalDateTime.now());
        User user5 = new User("Olex", 24, 4500, department1, odessa, user3,
                LocalDateTime.now());

        service.addCity(kiev);
        service.addCity(odessa);
        service.addDepartment(department1);
        service.addDepartment(department2);
        service.register(user3);
        service.register(user1);
        service.register(user2);
        service.register(user4);
        service.register(user5);

        assertEquals(5, service.findAll().size());
    }

    @Test
    public void addDepartment() throws Exception {

        Department department1 = new Department();
        department1.setName("TESTDEPART");


        Department department = service.addDepartment(department1);
        department.setId(8);

        department = service.addDepartment(department);

        assertEquals("TESTDEPART", department.getName());

    }

    @Test
    public void update() throws Exception {

        City kiev = new City();
        kiev.setName("Kiev");

        Department department1 = new Department();
        department1.setName("IT");

        User user1 = new User("Yura", 35, 1500, department1, kiev, null,
                LocalDateTime.now());

        service.addCity(kiev);
        service.addDepartment(department1);

        service.register(user1);
        user1.setName("Vasya");
        User user = service.update(user1);
        assertEquals("Vasya", user.getName());
        assertEquals(1, service.findAll().size());

    }

    @Test
    public void remove() throws Exception {

        City kiev = new City();
        kiev.setName("Kiev");

        Department department1 = new Department();
        department1.setName("IT");

        User user1 = new User("Yura", 35, 1500, department1, kiev, null,
                LocalDateTime.now());

        service.addCity(kiev);
        service.addDepartment(department1);

        service.register(user1);
        assertEquals(1, service.findAll().size());
        User user = service.remove(user1);
        assertEquals(0, service.findAll().size());

    }

    @Test
    public void getUsersGroupByDepartment() throws Exception {

        City kiev = new City();
        kiev.setName("Kiev");

        City odessa = new City();
        odessa.setName("Oddessa");

        Department department1 = new Department();
        department1.setName("IT");

        Department department2 = new Department();
        department2.setName("QA");

        User user3 = new User("Yura", 35, 1500, department2, kiev, null,
                LocalDateTime.now());
        User user1 = new User("Ivan", 30, 2500, department2, kiev, user3,
                LocalDateTime.now());
        User user2 = new User("Oleg", 33, 3500, department2, odessa, user3,
                LocalDateTime.now());
        User user4 = new User("Serhii", 22, 2500, department1, kiev, user3,
                LocalDateTime.now());
        User user5 = new User("Olex", 24, 4500, department1, odessa, user3,
                LocalDateTime.now());

        service.addCity(kiev);
        service.addCity(odessa);
        service.addDepartment(department1);
        service.addDepartment(department2);
        service.register(user3);
        service.register(user1);
        service.register(user2);
        service.register(user4);
        service.register(user5);

        assertEquals(2, service.getUsersGroupByDepartment().
                get(department1).size());
        assertEquals(3, service.getUsersGroupByDepartment().
                get(department2).size());

    }

    @Test
    public void getAvgSalaryGroupByDepartment() throws Exception {

        City kiev = new City();
        kiev.setName("Kiev");

        City odessa = new City();
        odessa.setName("Oddessa");

        Department department1 = new Department();
        department1.setName("IT");

        Department department2 = new Department();
        department2.setName("QA");

        User user3 = new User("Yura", 35, 1500, department2, kiev, null,
                LocalDateTime.now());
        User user1 = new User("Ivan", 30, 2500, department2, kiev, user3,
                LocalDateTime.now());
        User user2 = new User("Oleg", 33, 3500, department2, odessa, user3,
                LocalDateTime.now());
        User user4 = new User("Serhii", 22, 2500, department1, kiev, user3,
                LocalDateTime.now());
        User user5 = new User("Olex", 24, 4500, department1, odessa, user3,
                LocalDateTime.now());

        service.addCity(kiev);
        service.addCity(odessa);
        service.addDepartment(department1);
        service.addDepartment(department2);
        service.register(user3);
        service.register(user1);
        service.register(user2);
        service.register(user4);
        service.register(user5);

        assertEquals(3500, service.getAvgSalaryGroupByDepartment().
                get(department1), 0.001);
        assertEquals(2500, service.getAvgSalaryGroupByDepartment().
                get(department2), 0.001);

    }

    @Test
    public void getUsersGroupByManagersAndOrderedThatLiveInKiev()
            throws Exception {

        City kiev = new City();
        kiev.setName("Kiev");

        City odessa = new City();
        odessa.setName("Odessa");

        Department department1 = new Department();
        department1.setName("IT");

        Department department2 = new Department();
        department2.setName("QA");

        User user3 = new User("Yura", 35, 1500, department2, kiev, null,
                LocalDateTime.now());
        User user1 = new User("Ivan", 30, 2500, department2, kiev, user3,
                LocalDateTime.now());
        User user2 = new User("Oleg", 33, 3500, department2, odessa, user3,
                LocalDateTime.now());
        User user4 = new User("Serhii", 22, 2500, department1, kiev, user3,
                LocalDateTime.now());
        User user5 = new User("Olex", 24, 4500, department1, odessa, user3,
                LocalDateTime.now());

        service.addCity(kiev);
        service.addCity(odessa);
        service.addDepartment(department1);
        service.addDepartment(department2);
        service.register(user3);
        service.register(user1);
        service.register(user2);
        service.register(user4);
        service.register(user5);

        assertEquals(4, service.
                getUsersGroupByManagersAndOrderedThatLiveInKiev().
                get(user3).size());

    }

    @Test
    public void findByName() throws Exception {

        City kiev = new City();
        kiev.setName("Kiev");

        Department department1 = new Department();
        department1.setName("IT");

        service.addCity(kiev);
        service.addDepartment(department1);

        User user1 = new User("Yura", 35, 1500, department1, kiev, null,
                LocalDateTime.now());
        assertEquals(0, service.findByName("Yura").size());
        service.register(user1);
        assertEquals(1, service.findByName("Yura").size());

    }

    @Test
    public void findInRange() throws Exception {

        City kiev = new City();
        kiev.setName("Kiev");

        City odessa = new City();
        odessa.setName("Oddessa");

        Department department1 = new Department();
        department1.setName("IT");

        Department department2 = new Department();
        department2.setName("QA");

        User user3 = new User("Yura", 35, 1500, department2, kiev, null,
                LocalDateTime.now());
        User user1 = new User("Ivan", 30, 2500, department2, kiev, user3,
                LocalDateTime.now());
        User user2 = new User("Oleg", 33, 3500, department2, odessa, user3,
                LocalDateTime.now());
        User user4 = new User("Serhii", 22, 2500, department1, kiev, user3,
                LocalDateTime.now());
        User user5 = new User("Olex", 24, 4500, department1, odessa, user3,
                LocalDateTime.now());

        service.addCity(kiev);
        service.addCity(odessa);
        service.addDepartment(department1);
        service.addDepartment(department2);
        service.register(user3);
        service.register(user1);
        service.register(user2);
        service.register(user4);
        service.register(user5);

        assertEquals(3, service.findInRange(1000, 2500).size());

    }

    @Test
    public void findByDate() throws Exception {

        City kiev = new City();
        kiev.setName("Kiev");

        City odessa = new City();
        odessa.setName("Oddessa");

        Department department1 = new Department();
        department1.setName("IT");

        Department department2 = new Department();
        department2.setName("QA");

        User user3 = new User("Yura", 35, 1500, department2, kiev, null,
                LocalDateTime.of(2018, 2, 27, 16, 30));
        User user1 = new User("Ivan", 30, 2500, department2, kiev, user3,
                LocalDateTime.of(2018, 2, 27, 16, 35));
        User user2 = new User("Oleg", 33, 3500, department2, odessa, user3,
                LocalDateTime.of(2018, 2, 27, 16, 40));
        User user4 = new User("Serhii", 22, 2500, department1, kiev, user3,
                LocalDateTime.of(2018, 2, 27, 16, 45));
        User user5 = new User("Olex", 24, 4500, department1, odessa, user3,
                LocalDateTime.of(2018, 2, 27, 16, 50));

        service.addCity(kiev);
        service.addCity(odessa);
        service.addDepartment(department1);
        service.addDepartment(department2);
        service.register(user3);
        service.register(user1);
        service.register(user2);
        service.register(user4);
        service.register(user5);

        LocalDateTime start = LocalDateTime.of(2018, 2, 27, 16, 35);
        LocalDateTime end = LocalDateTime.of(2018, 2, 27, 16, 45);

        assertEquals(3, service.findByDate(start, end).size());

    }

}
package hibernate.service;

import hibernate.model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.*;


public class MainServiceImplTest {

    private static MainService service;
    private static EntityManagerFactory factory;

    @BeforeClass
    public static void createtables() {

        factory = Persistence.createEntityManagerFactory("hibernate-unit");
        service = new MainServiceImpl(factory);
    }


    @AfterClass
    public static void dropTables() {
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

        User user3 = new User("Yura", 35, 1500, department2, kiev, null);
        User user1 = new User("Ivan", 30, 2500, department2, kiev, user3);
        User user2 = new User("Oleg", 33, 3500, department2, odessa, user3);
        User user4 = new User("Serhii", 22, 2500, department1, kiev, user3);
        User user5 = new User("Olex", 24, 4500, department1, odessa, user3);

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
        department1.setId(8);
        department1.setName("TESTDEPART");

        Department department = service.addDepartment(department1);
        assertEquals(8, department.getId());

    }

    @Test
    public void update() throws Exception {
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
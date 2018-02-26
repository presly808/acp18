package hibernate.service;

import hibernate.model.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainServiceImplTest {
    @Test
    public void register() throws Exception {

        City kiev = new City();
        kiev.setId(1);
        kiev.setName("Kiev");

        City odessa = new City();
        odessa.setId(2);
        odessa.setName("Oddessa");

        Department department1 = new Department();
        department1.setId(1);
        department1.setName("IT");


        Department department2 = new Department();
        department2.setId(2);
        department2.setName("QA");

        User user3 = new User(0, "Yura", 35, 1500, department2, kiev, null);
        User user1 = new User(1, "Ivan", 30, 2500, department2, kiev, user3);
        User user2 = new User(2, "Oleg", 33, 3500, department2, odessa, user3);
        User user4 = new User(4, "Serhii", 22, 2500, department1, kiev, user3);
        User user5 = new User(5, "Olex", 24, 4500, department1, odessa, user3);

        MainServiceImpl service = new MainServiceImpl();
        User user = service.register(user3);
        user3.setAge(30);
        User user11 = service.register(user3);


        assertEquals(user.getName(), "Yura");
        assertEquals(user11.getAge(), 30);
        System.out.println(user.getId());
        System.out.println(user11.getId());

    }

    @Test
    public void addDepartment() throws Exception {
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
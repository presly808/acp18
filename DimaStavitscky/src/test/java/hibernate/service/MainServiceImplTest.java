package hibernate.service;

import hibernate.exception.exclude.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainServiceImplTest {

    private static City kiev;
    private static City odessa;

    private static Department department1;
    private static Department department2;

    private static LocalDateTime date2000;
    private static LocalDateTime date2005;
    private static LocalDateTime date2010;
    private static LocalDateTime date2015;

    private static User user1;
    private static User user2;
    private static User user3;
    private static User user4;
    private static User user5;;

    private static ApplicationContext context = new AnnotationConfigApplicationContext("hibernate");


    private static MainService service = context.getBean(MainServiceImpl.class);

    @BeforeClass
    public static void beforeClass() throws AppException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        kiev = new City("Kiev");
        odessa = new City("Oddessa");

        department1 = new Department("IT");
        department2 = new Department("QA");

        date2000 = LocalDateTime.of(2000, 1, 1, 1, 1);
        date2005 = LocalDateTime.of(2005, 1, 1, 1, 1);
        date2010 = LocalDateTime.of(2010, 1, 1, 1, 1);
        date2015 = LocalDateTime.of(2015, 1, 1, 1, 1);

        user1 = new User("Ivan", 30, 2500, department1, kiev, null, date2000);
        user2 = new User("Oleg", 33, 3500, department2, odessa, user1, date2005);
        user3 = new User("Yura", 35, 1500, department1, kiev, user2, date2010);
        user4 = new User("Serhii", 22, 2500, department2, odessa, user1, date2015);
        user5 = new User("Olex", 24, 4500, department1, kiev, user2, date2005);

        service.addCity(kiev);
        service.addCity(odessa);

        service.addDepartment(department1);
        service.addDepartment(department2);

        service.register(user1);
        service.register(user2);
        service.register(user3);
        service.register(user4);
        service.register(user5);
    }

    @Test
    public void getByDepFromDB() throws Exception {
        Thread.sleep(1500);
        Map<Department, List<User>> res = service.getUsersGroupByDepartment();
        for (Map.Entry<Department, List<User>> departmentListEntry : res.entrySet()) {
            System.out.println(departmentListEntry + ": ");
            departmentListEntry.getValue().forEach(System.out::println);
        }
    }
    @Test
    public void update() throws Exception {
        Thread.sleep(2000);
        user3.setAge(100);
        service.update(user3);
        User res = service.findById(user3.getId());
        Assert.assertEquals(res.getAge(), user3.getAge());
    }

    @Test
    public void getUsersGroupByDepartment() throws Exception {
        Thread.sleep(2000);
        Map<Department, List<User>> map = service.getUsersGroupByDepartment();
        List<User> userInDep1 = new ArrayList<>();
        userInDep1.add(user1);
        userInDep1.add(user3);
        userInDep1.add(user5);

        Assert.assertTrue(map.get(department1).containsAll(userInDep1));
    }

    @Test
    public void getAvgSalaryGroupByDepartment() throws Exception {
        Thread.sleep(2000);
        Map<Department, Integer> map = service.getAvgSalaryGroupByDepartment();
        int expected = (int) (user2.getSalary() + user4.getSalary()) / 2;

        Assert.assertEquals(expected, (int) map.get(department2));
    }

    @Test
    public void getUsersGroupByManagersAndOrderedThatLiveInKiev() throws Exception {
        Thread.sleep(2000);
        Map<User, List<User>> map = service.getUsersGroupByManagersAndOrderedThatLiveInKiev();

        Assert.assertTrue(map.get(user1).isEmpty()
                && map.get(user2).get(0).getCity().equals(kiev));
    }

    @Test
    public void findByName() throws Exception {
        Thread.sleep(2000);
        List<User> res = service.findByName("Olex");
        Assert.assertTrue(res.contains(user5));
    }

    @Test
    public void findInRange() throws Exception {
        Thread.sleep(2000);
        List<User> res = service.findInRange(3400, 4600);
        List<User> expected = new ArrayList<>();
        expected.add(user2);
        expected.add(user5);
        Assert.assertTrue(res.containsAll(expected));
    }

    @Test
    public void findByDate() throws Exception {
        Thread.sleep(2000);
        List<User> actual = service.findByDate(date2000, date2010);
        Assert.assertTrue(actual.contains(user2)
                && actual.contains(user5)
                && !actual.contains(user4));
    }
}
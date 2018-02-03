package db;

import db.model.City;
import db.model.Department;
import db.model.User;
import org.hamcrest.CoreMatchers;
import org.junit.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by serhii on 03.02.18.
 */
public class IDBTest {
    // todo add your implementation
    private static IDB idb;

    @BeforeClass
    public static void createtables() {
        idb.createTable(Department.class);
        idb.createTable(City.class);
        idb.createTable(User.class);
    }

    @Before
    public void before() {

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
        department2.setId(1);
        department2.setName("IT");

        User user3 = new User(3, "Yura", 35, 1500, department2, kiev, null);
        User user1 = new User(1, "Ivan", 30, 2500, department2, kiev, user3);
        User user2 = new User(2, "Oleg", 33, 3500, department2, odessa, user3);
        User user4 = new User(4, "Serhii", 22, 2500, department1, kiev, user3);
        User user5 = new User(5, "Olex", 24, 4500, department1, odessa, user3);

        idb.addCity(kiev);
        idb.addCity(odessa);
        idb.addDepart(department1);
        idb.addDepart(department2);
        idb.addUser(user3);
        idb.addUser(user1);
        idb.addUser(user2);
        idb.addUser(user4);
        idb.addUser(user5);
    }

    @After
    public void after() {
        idb.removeAllValues(Department.class);
        idb.removeAllValues(City.class);
        idb.removeAllValues(User.class);
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertThat(idb.getAll().size(), CoreMatchers.equalTo(5));
    }

    @Test
    public void selectWithFilter() throws Exception {
        City value = new City();
        value.setName("Kiev");

        Map<Field, Object> map = new HashMap<>();
        map.put(User.class.getDeclaredField("city"), value);
        idb.selectWithFilter(map, User.class.getDeclaredField("salary"), 2);
    }

    @Test
    public void fillTable() throws Exception {

    }

    @Test
    public void createTable() throws Exception {

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
    public void addUser() throws Exception {
        User yura = new User(3, "Yura", 35, 1500, null, null, null);
        User actual = idb.addUser(yura);
        Assert.assertThat(actual.getName(), CoreMatchers.equalTo("Yura"));
    }

    @Test
    public void removeUser() throws Exception {
        User yura = new User(3, "Yura", 35, 1500, null, null, null);
        User actual = idb.removeUser(yura);
        Assert.assertThat(actual.getName(), CoreMatchers.equalTo("Yura"));
    }

    @Test
    public void addCity() throws Exception {
        City kiev = new City();
        kiev.setId(1);
        kiev.setName("Kiev");

        City city = idb.addCity(kiev);


        Assert.assertThat(city.getId(), CoreMatchers.equalTo(1));
    }

    @Test
    public void addDepart() throws Exception {

        Department department1 = new Department();
        department1.setId(1);
        department1.setName("IT");

        Department department = idb.addDepart(department1);
        Assert.assertThat(department.getId(), CoreMatchers.equalTo(1));

    }

    @Test
    public void dropTable() throws Exception {
    }

    @Test
    public void removeAllValues() throws Exception {
    }

    @Test
    public void removeCity() throws Exception {
    }

    @Test
    public void removeDepart() throws Exception {
    }

}
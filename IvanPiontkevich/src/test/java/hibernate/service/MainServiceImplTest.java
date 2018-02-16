package hibernate.service;

import hibernate.dao.DepartmentDao;
import hibernate.dao.DepartmentDaoImpl;
import hibernate.dao.UserDao;
import hibernate.dao.UserDaoImpl;
import hibernate.exception.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import hibernate.wrap.TransactionWrapper;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class MainServiceImplTest {


    private MainService service;
    private EntityManagerFactory factory;
    private UserDao userDao;
    private DepartmentDao departmentDao;

    private List<User> users;
    private List<City> cities;
    private List<Department> departments;



    @Before
    public void setUp() throws AppException {
        factory = Persistence.createEntityManagerFactory("hibernate-unit");
        this.userDao = new UserDaoImpl(factory);
        this.departmentDao = new DepartmentDaoImpl(factory);
        service = new MainServiceImpl(userDao, departmentDao);
        users = new ArrayList<>();
        cities = new ArrayList<>();
        departments = new ArrayList<>();

        City kiev = new City();
        kiev.setName("Kiev");

        City odessa = new City();
        odessa.setName("Oddessa");

        cities.add(kiev);
        cities.add(odessa);
        TransactionWrapper.wrap(factory, new City()).executeWithTransaction((manager, entity) -> {
            cities.forEach(manager::persist);
        });

        Department department1 = new Department();
        department1.setName("IT");


        Department department2 = new Department();
        department2.setName("QA");
        departments.add(department1);
        departments.add(department2);
        TransactionWrapper.wrap(factory, new Department()).executeWithTransaction((manager, entity) -> {
            departments.forEach(manager::persist);
        });

        User user3 = new User( "Yura", 35, 1500, department1, odessa, null);
        users.add(user3);
        User user1 = new User( "Yura", 30, 1500, department1, kiev, user3);
        User user2 = new User( "Oleg", 33, 3000, department1, odessa, user3);
        User user4 = new User( "Serhii", 22, 2500, department2, kiev, user3);
        users.add(user1);
        users.add(user2);
        users.add(user4);
        TransactionWrapper.wrap(factory, new User()).executeWithTransaction((manager, entity) -> {
            users.forEach(manager::persist);
        });




    }

    @After
    public void tearDown(){
        factory.close();
    }


    @Test
    public void register() throws Exception {
        User user = new User("Yura", 24, 1450, null, null, null);
        User userFromDB = service.register(user);
        assertEquals(user.getName(), userFromDB.getName());
        assertEquals(user.getAge(), userFromDB.getAge());
    }

    @Test
    public void addDepartment() throws Exception {
        Department department = new Department("HR");
        Department fromBD = service.addDepartment(department);
        assertEquals(department.getName(), fromBD.getName());
        assertThat(fromBD.getId(), not(0));

    }

    @Test
    public void removeDepartment(){
        Department department = departmentDao.remove(3);
        assertEquals(department.getName(), "IT");
    }

    @Test
    public void update() throws Exception {

        User forUpate = service.findById(6);
        forUpate.setCity(new City("Donetsk"));
        forUpate.setName("Vasia");
        forUpate.setDepartment(new Department("HR"));
        User fromDB = service.update(forUpate);
        assertThat(fromDB.getName(), CoreMatchers.equalTo("Vasia"));
        assertThat(fromDB.getCity().getName(), CoreMatchers.equalTo("Donetsk"));

        System.out.println(fromDB.toString());
        assertThat(fromDB.getId(), not(0));
    }

    @Test
    public void remove() throws Exception {
        User forDelete = new User();
        forDelete.setId(6);
        User deletedUser = service.remove(forDelete);
        assertEquals(deletedUser.getName(), "Yura");
        assertEquals(deletedUser.getAge(), 30);

    }

    @Test
    public void getUsersGroupByDepartment() throws Exception {
        Map<Department, List<User>> groupByDepartment = service.getUsersGroupByDepartment();
        assertEquals(groupByDepartment.size(), 2);
        List<User> allUsers = new ArrayList<>();
        for (List<User> list : groupByDepartment.values()) {
            allUsers.addAll(list);
        }
        assertEquals(allUsers.size(), 4);
    }

    @Test
    public void getAvgSalaryGroupByDepartment() throws Exception {
        Map<Department, Double> departmentIntegerMap = service.getAvgSalaryGroupByDepartment();
        assertEquals(departmentIntegerMap.keySet().size(), 2);
        assertEquals(departmentIntegerMap.get(new Department(3, "IT")), new Double(2000));
        assertEquals(departmentIntegerMap.get(new Department(4, "QA")), new Double(2500));
    }

    @Test
    public void getUsersGroupByManagersAndOrderedThatLiveInKiev() throws Exception {
        Map<User, List<User>> groupByManager = service.getUsersGroupByManagersAndOrderedThatLiveInKiev();
        assertThat(groupByManager.size(), is(1));
        List<User> users = groupByManager.get(new User(5,"Yura", 35, 1500, null,null,null));
        assertThat(users.get(0).getCity().getName(), is("Kiev"));
    }

    @Test
    public void findByName() throws Exception {
        List<User> usersByName = service.findByName("Yura");
        assertEquals(usersByName.size(), 2);
        assertEquals(usersByName.get(0).getName(), "Yura");
        assertEquals(usersByName.get(1).getName(), "Yura");
    }

    @Test(expected = AppException.class)
    public void findByNameNegative() throws Exception{
        service.findByName(null);
    }

    @Test
    public void findInRange() throws Exception {
        List<User> usersInRange = service.findInRange(1000, 2000);
        assertEquals(usersInRange.size(), 2);
    }

    @Test
    public void findByDate() throws Exception {
        List<User> usersByDate = service.findByDate(LocalDateTime.of(2000,11, 20,12,22), LocalDateTime.of(2019, 01,1,2,34));
        assertEquals(usersByDate.size(), 4);
    }

}
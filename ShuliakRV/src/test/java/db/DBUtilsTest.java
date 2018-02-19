package db;

import db.model.City;
import db.model.Department;
import db.model.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class DBUtilsTest {
    @Test
    public void createTable() throws Exception {

        IDB idb = new DBUtils("jdbc:sqlite:D:\\DB\\database.db");

        idb.createTable(Department.class);
        idb.createTable(City.class);
        idb.createTable(User.class);

        idb.dropTable(User.class);
        idb.dropTable(Department.class);
        idb.dropTable(City.class);
    }

    @Test
    public void addUser1() throws Exception {

        IDB idb = new DBUtils("jdbc:sqlite:D:\\DB\\database.db");

        idb.createTable(Department.class);
        idb.createTable(City.class);
        idb.createTable(User.class);

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

        idb.dropTable(User.class);
        idb.dropTable(Department.class);
        idb.dropTable(City.class);


    }

    @Test
    public void setFieldValue() throws Exception {

        IDB idb = new DBUtils("jdbc:sqlite:D:\\DB\\database.db");

        idb.createTable(User.class);

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

        User user3 = new User(3, "Yura", 35, 1500, department2, kiev, null);

        user3.setAge(5);

        assertEquals(user3.getAge(),5);

        idb.dropTable(User.class);

    }

    @Test
    public void executeSQL(String sql) {

        IDB idb = new DBUtils("jdbc:sqlite:D:\\DB\\database.db");

        idb.createTable(User.class);

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

    }

    @Test
    public void addUser() throws Exception {

        IDB idb = new DBUtils("jdbc:sqlite:D:\\DB\\database.db");

        idb.createTable(User.class);

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


        User yura = new User(120, "TestUser", 35, 1500, null, null, null);
        User actual = idb.addUser(yura);
        assertThat(actual.getName(), equalTo("TestUser"));
    }

    @Test
    public void removeUser() throws Exception {

        IDB idb = new DBUtils("jdbc:sqlite:D:\\DB\\database.db");

        idb.createTable(User.class);

        User yura = new User(3, "Yura", 35, 1500, null, null, null);
        User actual = idb.removeUser(yura);
        assertThat(actual.getName(), equalTo("Yura"));
    }
}
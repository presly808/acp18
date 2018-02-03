package db;

import db.model.City;
import db.model.Department;
import db.model.User;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by serhii on 03.02.18.
 */
public class IDBTest {
    // todo add your implementation
    private IDB idb;

    @Before
    public void before(){
        idb.createTable(Department.class);
        idb.createTable(City.class);
        idb.createTable(User.class);

        idb.fillTable("");
    }

    @After
    public void after(){
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
    }

    @Test
    public void createTableAndFill() throws Exception {
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
    }

    @Test
    public void removeUser() throws Exception {
    }

    @Test
    public void addCity() throws Exception {
    }

    @Test
    public void addDepart() throws Exception {
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
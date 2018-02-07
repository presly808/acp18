package db;

import db.model.Base;
import db.model.City;
import db.model.Department;
import db.model.User;
import org.junit.Test;

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

}
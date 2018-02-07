package db.utils;

import db.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DBUtilsTestWithMySQL {

    private static DBUtils dbUtils;

    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private static final String URL = "jdbc:mysql://localhost:3306/ACP18";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static boolean tableIsCreated = false;

    @Before
    public void setUp() throws Exception {
        Class.forName(DRIVER_CLASS_NAME);
        dbUtils = new DBUtilsImpl(URL, USER, PASSWORD);

        nativelyCreateUserTable();
    }

    @After
    public void tearDown() throws Exception {
        nativelyDropUserTable();
    }

    @Test
    public void createTable() throws Exception {
        nativelyDropUserTable();    // to support @Before

        assertTrue(dbUtils.createTable(User.class));
    }

    @Test
    public void dropTable() throws Exception {
        assertTrue(dbUtils.dropTable(User.class));

        nativelyCreateUserTable();  // to support @After
    }

    @Test
    public void removeAllValues() throws Exception {
        String nativeInsertQuery1 = "INSERT INTO User(id, name) VALUES(1, 'Alex');";
        String nativeInsertQuery2 = "INSERT INTO User(id, name) VALUES(2, 'Max');";
        dbUtils.nativeSQL(nativeInsertQuery1);
        dbUtils.nativeSQL(nativeInsertQuery2);

        assertTrue(dbUtils.removeAllValues(User.class));

        String actualResult = null;

        try (
                ResultSet resultSet = DriverManager
                        .getConnection(URL, USER, PASSWORD)
                        .createStatement()
                        .executeQuery("SELECT * FROM User;")
                ) {

            actualResult = Converter.resultSetToString(resultSet);
        }

        assertEquals(0, actualResult.length());
    }

    @Test
    public void nativeSQL() throws Exception {
        String nativeInsertQuery = "INSERT INTO User(id, name) VALUES(1, 'Alex');";
        String nativeSelectQuery = "SELECT id, name from User;";
        String expectedResult = "1 Alex";

        assertNull(dbUtils.nativeSQL(nativeInsertQuery));
        assertEquals(expectedResult, dbUtils.nativeSQL(nativeSelectQuery));
    }

    // TODO complete implementation
    @Ignore
    @Test
    public void getAllValues() throws Exception {
        List<User> expectedList = new ArrayList<>();
        expectedList.add(new User(1, "Alex", 20));
        expectedList.add(new User(2, "John", 21));

        try (
                Statement statement = DriverManager
                        .getConnection(URL, USER, PASSWORD)
                        .createStatement()
                ) {
            statement.execute("INSERT INTO User (id, name, age) VALUES (1, 'Alex', 20);");
            statement.execute("INSERT INTO User (id, name, age) VALUES (2, 'John', 21);");
        }

        List<User> actualList = dbUtils.getAllValues(User.class);

        assertEquals(expectedList, actualList);
    }

    // TODO complete implementation
    @Ignore
    @Test
    public void selectWithFilter() throws Exception {
    }

    @Test
    public void add() throws Exception {
        User user = new User(1, "Alex", 20);
        assertEquals(user, dbUtils.add(User.class, user));

        String expectedResult = "1 Alex 20";
        String actualResult = null;

        try (
                ResultSet resultSet = DriverManager
                        .getConnection(URL, USER, PASSWORD)
                        .createStatement()
                        .executeQuery("SELECT id, name, age FROM User;")
        ) {
            actualResult = Converter.resultSetToString(resultSet);
        }

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void remove() throws Exception {

        User expectedUser = new User(1, "Alex", 20);

        try (
                Statement statement = DriverManager
                        .getConnection(URL, USER, PASSWORD)
                        .createStatement()
        ) {
            statement.execute("INSERT INTO User (id, name, age) VALUES (1, 'Alex', 20);");
        }

        User removedUser = dbUtils.remove(User.class, expectedUser);
        assertEquals(expectedUser, removedUser);

        String actualResult = null;

        try (
                ResultSet resultSet = DriverManager
                        .getConnection(URL, USER, PASSWORD)
                        .createStatement()
                        .executeQuery("SELECT * FROM User;")
        ) {
            actualResult = Converter.resultSetToString(resultSet);
        }

        assertEquals(0, actualResult.length());
    }

    private static void nativelyCreateUserTable() {
        try (
                Statement statement = DriverManager
                        .getConnection(URL, USER, PASSWORD)
                        .createStatement()
                ) {

            statement.execute(
                    "CREATE TABLE User (\n" +
                            "id INT,\n" +
                            "name VARCHAR(255),\n" +
                            "salary DOUBLE,\n" +
                            "age INT,\n" +
                            "department_id INT,\n" +
                            "city_id INT,\n" +
                            "manage_id INT,\n" +
                            "PRIMARY KEY (id)\n" +
                            ");"
            );
            System.out.println("@Before:setUp(): Table User was successfully created!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void nativelyDropUserTable() {
        try (
                Statement statement = DriverManager
                        .getConnection(URL, USER, PASSWORD)
                        .createStatement()
        ) {

            statement.execute("DROP TABLE User;");
            System.out.println("@After:tearDown(): Table User was successfully dropped!\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
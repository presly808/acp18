package db;

import db.model.City;
import db.model.Department;
import db.model.User;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 03.02.18.
 */
public class DBUtils implements IDB {

    private String url;

    public DBUtils(String url) {
        this.url = url;
    }

    public List<User> getAll(){
        String url = "jdbc:sqlite:/home/andriy/IdeaProjects/acp18/AndriySmikhun/src/main/java/db/MyDB.bd";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(conn);
        return null;
    }

    @Override
    public List<User> selectWithFilter(Map<Field, Object> filters, Field orderBy, int limit) {
        return null;
    }

    @Override
    public boolean fillTable(String csvUrl) {
        return false;
    }

    @Override
    public boolean createTable(Class clazz) {

        String url = "jdbc:sqlite:C:/Users/smikhun/IdeaProjects/acp18/AndriySmikhun/src/main/java/db/MyDB.bd";

        String sql = "CREATE TABLE " +
                clazz.getSimpleName() + " (\n" +
                "id integer PRIMARY KEY);"
                ;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeAllValues(Class clazz) {
        return false;
    }

    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() {
        return null;
    }

    @Override
    public Map<Department, Integer> getAvgSalaryGroupByDepartment() {
        return null;
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() {
        return null;
    }

    @Override
    public User addUser(User userWithoutId) {
        return null;
    }

    @Override
    public User removeUser(User user) {
        return null;
    }

    @Override
    public City addCity(City city) {
        return null;
    }

    @Override
    public City removeCity(City city) {
        return null;
    }

    @Override
    public Department addDepart(Department department) {
        return null;
    }

    @Override
    public Department removeDepart(Department department) {
        return null;
    }

    @Override
    public boolean dropTable(Class clazz) {
        return false;
    }

    @Override
    public String nativeSQL(String sql) {
        return null;
    }


}

package db;

import db.model.City;
import db.model.Department;
import db.model.User;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 03.02.18.
 */
public class DBUtils implements IDB {

    private String url;

    private Map<String, String> keyValueMap;

    private Connection conn;

    public DBUtils(String url) {

        this.url = url;

        try {
            // db parameters
            //String url = "jdbc:sqlite:C:/sqlite/db/chinook.db";
            // create a connection to the database
            //
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } /*finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }*/

        keyValueMap = new HashMap<>();
        keyValueMap.put("int", "INTEGER");
        keyValueMap.put("double", "REAL");
        keyValueMap.put("String", "TEXT");

    }

    public List<User> getAll() {
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

        StringBuilder sqlCreateTable = new StringBuilder();

        sqlCreateTable.append("CREATE TABLE ").append(clazz.getSimpleName()).append(" (");

        while (clazz != null) {

            for (Field field : clazz.getDeclaredFields()) {

                String fieldName = field.getName();

                String fieldType = field.getType().getSimpleName();

                fieldType = keyValueMap.get(fieldType);

                if (fieldType == null) fieldType = "INTEGER";

                sqlCreateTable.append(fieldName + " " + fieldType + ",");
            }

            clazz = clazz.getSuperclass();

        }

        sqlCreateTable.deleteCharAt(sqlCreateTable.length() - 1).append(");");

        try {
            Statement statement = conn.createStatement();
            System.out.println(sqlCreateTable.toString());
            statement.execute(sqlCreateTable.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
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

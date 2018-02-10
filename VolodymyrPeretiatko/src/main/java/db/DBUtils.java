package db;

import db.model.Base;
import db.model.City;
import db.model.Department;
import db.model.User;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by serhii on 03.02.18.
 */
public class DBUtils implements IDB {

    private String url;

    public DBUtils(String url) {
        this.url = url;
    }


    private static String convertToSQLType(Field field) {

        String sqlType;
        String sqlName = field.getName();

        String type = field.getType().getSimpleName();

        if ("int".equals(type)){
            sqlType = "INT";
        } else if ("double".equals(type)){
            sqlType = "DOUBLE";
        } else if ("String".equals(type)){
            sqlType = "VARCHAR(255)";
        } else {
            sqlName += "_id";
            sqlType = "INT";
        }

        if ("id".equals(sqlName)){
            sqlType = "INTEGER PRIMARY KEY AUTOINCREMENT";
        }

        return sqlName + " " + sqlType;
    }

    private static List<Field> getAllFields(Class type) {
        List<Field> fields = new ArrayList<>();
        for (Class c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    private static String getSQLFieldsFromCls(Class target) {

        List<Field> fields = getAllFields(target);

        String result = "";

        result += fields.stream()
                .map(f -> convertToSQLType(f))
                .collect(Collectors.joining(",\n"));

        return result;
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private boolean executeStatement(String sql){

        Connection conn = connect();
        try {
            Statement stmt = conn.createStatement();
            return stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;

    }

    private PreparedStatement getPreparedStatement(String sql){

        Connection conn = connect();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return  null;
    }

    private int getId(Base b){
        if (b == null){
            return 0;
        }
        return b.getId();
    }

    public List<User> getAll(){

        ArrayList<User> users = new ArrayList<>();

        String sql = "SELECT id, name, age FROM user";

        PreparedStatement pstmt = getPreparedStatement(sql);

        try {
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return users;
    }

    @Override
    public <T> List<T> getAllValues(Class<T> type) {
        return null;
    }

    @Override
    public <T> List<T> selectWithFilter(Class<T> type, Map<Field, Object> filters, Field orderBy, int limit) {
        return null;
    }

    @Override
    public <T> T addGen(Class<T> tClass, T obj) {
        return null;
    }

    @Override
    public <T> T removeGen(Class<T> tClass, T obj) {
        return null;
    }

    @Override
    public boolean createTable(Class clazz) {

        String sql = "CREATE TABLE IF NOT EXISTS " + clazz.getSimpleName() + " (\n";
        sql += getSQLFieldsFromCls(clazz) + ");";
        System.out.println(sql);
        return executeStatement(sql);

    }

    @Override
    public boolean removeAllValues(Class clazz) {
        return executeStatement("DELETE FROM " + clazz.getSimpleName());
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

        String sql = "INSERT INTO User(name, age, salary, department_id, city_id, manage_id) VALUES(?,?,?,?,?,?) ;";

        PreparedStatement pstmt = getPreparedStatement(sql);

        try {
            pstmt.setString(1, userWithoutId.getName());
            pstmt.setInt(2, userWithoutId.getAge());
            pstmt.setDouble(3, userWithoutId.getSalary());
            pstmt.setInt(4, getId(userWithoutId.getDepartment()));
            pstmt.setInt(5, getId(userWithoutId.getCity()));
            pstmt.setInt(6, getId(userWithoutId.getManage()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userWithoutId;
    }

    @Override
    public User removeUser(User user) {

        String sqlSelect = "SELECT id, name, age FROM User WHERE id = ?;";

        String sqlDel = "DELETE FROM User WHERE id = ?;";

        PreparedStatement pstmt = getPreparedStatement(sqlSelect);
        User delUser = null;

        try {
            pstmt.setInt(1, user.getId());
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                delUser = new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
            }

            if (delUser != null){
                pstmt = getPreparedStatement(sqlDel);
                pstmt.setInt(1, user.getId());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return delUser;

    }

    @Override
    public City addCity(City city) {

        String sql = "INSERT INTO City(name) VALUES(?);";

        PreparedStatement pstmt = getPreparedStatement(sql);

        try {
            pstmt.setString(1, city.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return city;
    }

    @Override
    public City removeCity(City city) {
        return null;
    }

    @Override
    public Department addDepart(Department department) {

        String sql = "INSERT INTO Department(name) VALUES(?);";

        PreparedStatement pstmt = getPreparedStatement(sql);

        try {
            pstmt.setString(1, department.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return department;
    }

    @Override
    public Department removeDepart(Department department) {
        return null;
    }

    @Override
    public boolean dropTable(Class clazz) {
        return executeStatement("DROP TABLE " + clazz.getSimpleName());
    }

    @Override
    public String nativeSQL(String sql) {
        return null;
    }


}

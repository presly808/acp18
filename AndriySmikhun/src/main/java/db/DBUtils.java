package db;

import db.model.Base;
import db.model.City;
import db.model.Department;
import db.model.User;

import java.lang.reflect.Field;
import java.sql.*;
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
        String sql = "SELECT * FROM user";

        try {
            Connection conn = connect();
            Statement stmp = conn.createStatement();
            ResultSet rs = stmp.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



        return null;
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
        String sql = "";

        if (clazz.getSimpleName().equals("User"))
        {sql = "CREATE TABLE user (\n" +
                "id integer PRIMARY KEY, \n" +
                "name text NOT NULL, \n" +
                "age integer NOT NULL, \n" +
                "salary float NOT NULL, \n" +
                "department integer, \n" +
                "city integer, \n" +
                "manage integer, \n" +
                "FOREIGN KEY (department) REFERENCES department(id),\n" +
                "FOREIGN KEY (city) REFERENCES city(id)\n" +
                 ");";

        }
        if (clazz.getSimpleName().equals("Department")){
            sql = "CREATE TABLE department (\n" +
                    "id integer Primary KEY, \n" +
                    "name text NOT NULL);";
        }
        if (clazz.getSimpleName().equals("City")){
            sql = "CREATE TABLE city (\n" +
                    "id integer Primary KEY, \n" +
                    "name text NOT NULL);";
        }
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            return stmt.execute(sql);
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
        String sql = "INSERT INTO user(id,name,age,salary,department,city,manage) " +
                "VALUES(?,?,?,?,?,?,?)";
        try
            (Connection conn = connect();
                    PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1,userWithoutId.getId());
            pstmt.setString(2,userWithoutId.getName());
            pstmt.setInt(3,userWithoutId.getAge());
            pstmt.setDouble(4,userWithoutId.getSalary());
            pstmt.setInt(5,userWithoutId.getDepartment().getId());
            pstmt.setInt(6,userWithoutId.getCity().getId());
            pstmt.setInt(7,userWithoutId.getManage().getId());

                  }catch (SQLException e){
            System.out.println(e);
        }

        String sqlSelect = "SELECT id,name,age,salary,department, city, manage " +
                "FROM user WHERE id = " + userWithoutId.getId();
        User rsUser = new User();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)) {

                rsUser.setId(rs.getInt("id"));
                rsUser.setName(rs.getString("name"));
                rsUser.setAge(rs.getInt("age"));
                rsUser.setSalary(rs.getDouble("salary"));
                rsUser.setDepartment(null);
                rsUser.setCity(null);
                rsUser.setManage(null);

            return rsUser;
        }catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public User removeUser(User user) {
        return null;
    }

    @Override
    public City addCity(City city) {

        String sql = "INSERT INTO city(id,name) " +
                "VALUES(?,?);";
        String sqlSelect = "SELECT id,name FROM city WHERE id=" + city.getId() + ";";

        try
                (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,city.getId());
            pstmt.setString(2,city.getName());

        }catch (SQLException e){
            System.out.println(e);
        }

        City rsCity = new City();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)) {

                rsCity.setId(rs.getInt("id"));
                rsCity.setName(rs.getString("name"));

        }catch (SQLException e){}

        return rsCity;
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

        String sql = "DROP TABLE " + clazz.getSimpleName() + ";";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public String nativeSQL(String sql) {
        return null;
    }

    public Connection connect(){
        String url = "jdbc:sqlite:database.db";
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connect;
    }

}

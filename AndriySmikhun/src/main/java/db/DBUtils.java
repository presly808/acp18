package db;

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
        {sql = "CREATE TABLE " +
                clazz.getSimpleName() + " (\n" +
                "id integer PRIMARY KEY" +
                 ");";

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
        String sql = "INSERT IN TO user(id,name,age,salary,departament, city, manage) " +
                "VALUES(?,?,?,?,?,?,?)";
        try
            (Connection conn = connect();
                    PreparedStatement pstmt = conn.prepareStatement(sql)){

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
        String url = "jdbc:sqlite:C:/Users/smikhun/IdeaProjects/acp18/AndriySmikhun/src/main/java/db/MyDB.bd";
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connect;
    }

}

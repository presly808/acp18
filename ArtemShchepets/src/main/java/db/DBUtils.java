package db;

import db.model.City;
import db.model.Department;
import db.model.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
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

    private static final String URL = "jdbc:mysql://localhost:3306/acp18";

    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static final String ADD_USER = "INSERT INTO User (id, name, age, salary, department_id, city_id, user_id) VALUES (?,?,?,?,?,?,?)";
    private static final String DELETE_USER = "DELETE FROM User WHERE id=?";
    private static final String ADD_CITY = "INSERT INTO City (id, name) VALUES (?,?)";
    private static final String DELETE_CITY = "DELETE FROM Department WHERE id=?";
    private static final String ADD_DEPARTMENT = "INSERT INTO Department (id, name) VALUES (?,?)";
    private static final String DELETE_DEPARTMENT = "DELETE FROM Department WHERE id=?";
    private static final String DROP_TABLE = "DROP TABLE %s";

    // TODO You had union of all tables, change query
    private static final String GET_ALL_USERS = "SELECT u.id, u.name, u.age, u.salary, dep.id, city.id, man.id" +
            " FROM User u LEFT JOIN Department dep ON u.department_id=dep.id" +
            " LEFT JOIN City city ON u.city_id=city_id " +
            "LEFT JOIN User man ON u.user_id=man_id";
    private static final String GET_DEPARTMENT_NAME = "SELECT name FROM Department";
    private static final String GET_CITY_NAME = "SELECT name FROM City";
    private static final String GET_USER = "SELECT id, name, age, salary, department_id, city_id, man_id FROM User";

    private String url;

    public DBUtils(String url) {
        this.url = url;
    }

    public List<User> getAll() {

        List<User> listOfUsers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(GET_ALL_USERS);

            preparedStatement.execute();

            ResultSet rs = preparedStatement.getResultSet();

            while (rs.next()) {

                User user = new User();

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                user.setSalary(rs.getDouble("salary"));

                Department department = new Department();
                department.setId(rs.getInt("department_id"));

                PreparedStatement preparedStatement2 = connection.prepareStatement(GET_DEPARTMENT_NAME);
                ResultSet rs2 = preparedStatement2.getResultSet();
                department.setName(rs2.getString("name"));

                City city = new City();
                city.setId(rs.getInt("city_id"));

                PreparedStatement preparedStatement3 = connection.prepareStatement(GET_CITY_NAME);
                ResultSet rs3 = preparedStatement3.getResultSet();
                city.setName(rs3.getString("name"));

                User manager = new User();
                manager.setId(rs.getInt("user_id"));

                PreparedStatement preparedStatement4 = connection.prepareStatement(GET_USER);
                ResultSet rs4 = preparedStatement4.getResultSet();
                manager.setId(rs.getInt("id"));
                manager.setName(rs.getString("name"));
                manager.setAge(rs.getInt("age"));
                manager.setSalary(rs.getDouble("salary"));

                user.setDepartment(department);
                user.setCity(city);
                user.setManage(manager);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfUsers;
    }

    @Override
    public List<User> selectWithFilter(Map<Field, Object> filters, Field orderBy, int limit) {
        return null;
    }

    @Override
    public boolean fillTable(String csvUrl) {

        return true;
    }

    @Override
    public boolean createTable(Class clazz) {

        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toList());

        StringBuilder request = new StringBuilder
                (String.format("CREATE TABLE %s(\n", clazz.getSimpleName()));

        for (Field field : fields) {
            request.append(field.getName());

            String fieldType = field.getType().getSimpleName().toLowerCase();

            switch (fieldType) {

                case "String":
                    request.append(" VARCHAR(100),\n");
                case "int":
                    request.append(" INT,\n");
                case "double":
                    request.append("DOUBLE,\n");
                default:
                    request.append(fieldType).append("_id").append(" INT,\n");
            }

            request.deleteCharAt(request.length() - 2).append("PRIMARY KEY ( id )\n)");
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(String.format(request.toString(), clazz.getSimpleName()));

            preparedStatement.execute();


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

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(ADD_USER);

            preparedStatement.setInt(1, userWithoutId.getId());
            preparedStatement.setString(2, userWithoutId.getName());
            preparedStatement.setInt(3, userWithoutId.getAge());
            preparedStatement.setDouble(4, userWithoutId.getSalary());
            preparedStatement.setInt(5, userWithoutId.getDepartment().getId());
            preparedStatement.setInt(6, userWithoutId.getCity().getId());
            preparedStatement.setInt(7, userWithoutId.getManage().getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userWithoutId;
    }

    @Override
    public User removeUser(User user) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(DELETE_USER);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public City addCity(City city) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(ADD_CITY);
            preparedStatement.setInt(1, city.getId());
            preparedStatement.setString(2, city.getName());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return city;
    }

    @Override
    public City removeCity(City city) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(DELETE_CITY);
            preparedStatement.setInt(1, city.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return city;
    }

    @Override
    public Department addDepart(Department department) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(ADD_DEPARTMENT);
            preparedStatement.setInt(1, department.getId());
            preparedStatement.setString(2, department.getName());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return department;
    }

    @Override
    public Department removeDepart(Department department) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(DELETE_DEPARTMENT);
            preparedStatement.setInt(1, department.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return department;
    }

    @Override
    public boolean dropTable(Class clazz) {

        String tableName = clazz.getSimpleName();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(String.format(DROP_TABLE, tableName));
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}

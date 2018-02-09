package db;

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
            sqlType += " PRIMARY KEY";
        }

        return sqlName + " " + sqlType;
    }

    private static List<Field> getAllFields(Class type) {
        List<Field> fields = new ArrayList<Field>();
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

    private boolean executeStatement(String sql){

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            return stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



        return false;

    }

    public List<User> getAll(){
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

        String sql = "CREATE TABLE IF NOT EXISTS " + clazz.getSimpleName() + " (\n";
        sql += getSQLFieldsFromCls(clazz) + ");";
        System.out.println(sql);
        return executeStatement(sql);

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
        return executeStatement("DROP TABLE " + clazz.getSimpleName());
    }

    @Override
    public String nativeSQL(String sql) {
        return null;
    }


}

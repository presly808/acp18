package db;

import db.model.City;
import db.model.Department;
import db.model.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 03.02.18.
 */
public class DBUtils implements IDB {

    private String url;

    private Map<String, String> keyValueMap;


    public DBUtils(String url) {

        this.url = url;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        keyValueMap = new HashMap<>();
        keyValueMap.put("int", "INTEGER");
        keyValueMap.put("double", "REAL");
        keyValueMap.put("String", "TEXT");

    }

    public List<User> getAll() {
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

        StringBuilder sqlCreateTable = new StringBuilder();

        sqlCreateTable.append("CREATE TABLE ").append(clazz.getSimpleName()).append(" (");

        while (clazz != null) {

            for (Field field : clazz.getDeclaredFields()) {

                if (!field.isSynthetic()) {

                    String fieldName = field.getName();

                    String fieldType = field.getType().getSimpleName();

                    fieldType = keyValueMap.get(fieldType);

                    if (fieldType == null) fieldType = "INTEGER";

                    sqlCreateTable.append(fieldName + " " + fieldType + ",");
                }
            }

            clazz = clazz.getSuperclass();

        }

        sqlCreateTable.deleteCharAt(sqlCreateTable.length() - 1).append(");");

        boolean result;
        try {
            result = executeSQL(sqlCreateTable.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return result;
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

        return addObject(userWithoutId);
    }

    @Override
    public User removeUser(User user) {
        return null;
    }

    @Override
    public City addCity(City city) {

        return addObject(city);
    }

    @Override
    public City removeCity(City city) {
        return null;
    }

    @Override
    public Department addDepart(Department department) {

        return addObject(department);
    }

    @Override
    public Department removeDepart(Department department) {
        return null;
    }

    @Override
    public boolean dropTable(Class clazz) {

        StringBuilder sqlDropTable = new StringBuilder();

        sqlDropTable.append("DROP TABLE ").append(clazz.getSimpleName()).append(";");

        boolean result = false;
        try {
            result = executeSQL(sqlDropTable.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return result;
    }

    @Override
    public String nativeSQL(String sql) {

        return null;
    }


    public boolean executeSQL(String sql) throws SQLException {

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement();) {
            return statement.execute(sql.toString());
        }
    }

    private <T> Map<String, String> getFieldsFromObject(T obj) {

        Class clazz = obj.getClass();

        Map<String, String> map = new HashMap<>();

        while (clazz != null) {

            for (Field field : clazz.getDeclaredFields()) {

                if (!field.isSynthetic()) {

                    String fieldName = field.getName();

                    String fieldValue = "";

                    String fieldType = field.getType().getSimpleName();

                    fieldType = keyValueMap.get(fieldType);

                    if (fieldType == null) {

                        Object target = null;
                        try {
                            target = field.get(obj);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }

                        if (target == null) {
                            fieldValue = "NULL";

                        } else {
                            fieldName = "Id";
                            fieldValue = getFieldValue(target, fieldName);
                        }

                    } else {
                        fieldValue = getFieldValue(obj, fieldName);
                        if (fieldType.equals("TEXT")) {
                            fieldValue = String.format("'%s'", fieldValue);
                        }
                    }
                    map.put(fieldName, fieldValue);
                }
            }

            clazz = clazz.getSuperclass();
        }

        return map;
    }

    private <T> String getFieldValue(T obj, String fieldName) {

        String value = null;

        try {
            value = obj.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)).
                    invoke(obj).toString();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return value;
    }

    private <T> T addObject(T obj) {

        StringBuilder sqlHeader = new StringBuilder();
        StringBuilder sqlValues = new StringBuilder();

        Class classUser = obj.getClass();

        sqlHeader.append("INSERT INTO ").append(classUser.getSimpleName()).append(" (");

        Map<String, String> map = getFieldsFromObject(obj);

        for (String key : map.keySet()) {
            sqlHeader.append(key).append(",");
            sqlValues.append(map.get(key)).append(",");
        }
        sqlHeader.deleteCharAt(sqlHeader.length() - 1).append(") ").
                append("VALUES (");
        sqlValues.deleteCharAt(sqlValues.length() - 1).append(");");

        try {
            executeSQL(sqlHeader.append(sqlValues).toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return obj;
    }


}

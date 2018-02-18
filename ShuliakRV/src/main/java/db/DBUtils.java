package db;

import db.model.City;
import db.model.Department;
import db.model.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 03.02.18.
 */
public class DBUtils implements IDB {

    private String url;

    private Map<String, String> fieldTypeMap;


    public DBUtils(String url) {

        this.url = url;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        fieldTypeMap = new HashMap<>();
        fieldTypeMap.put("int", "INTEGER");
        fieldTypeMap.put("double", "REAL");
        fieldTypeMap.put("String", "TEXT");

    }

    public List<User> getAll() {
        try {
            return querySQL(User.class,"SELECT * FROM USER");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

                    fieldType = fieldTypeMap.get(fieldType);

                    if (fieldType == null) {
                        fieldType = "INTEGER";
                        fieldName += "Id";
                    }

                    sqlCreateTable.append(fieldName + " " + fieldType + ",");
                }
            }
            clazz = clazz.getSuperclass();
        }
        sqlCreateTable.deleteCharAt(sqlCreateTable.length() - 1).append(");");

        return executeSQL(sqlCreateTable.toString()) >= 0 ? true : false;
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

        try {
            return addObject(userWithoutId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User removeUser(User user) {
        return null;
    }

    @Override
    public City addCity(City city) {

        try {
            return addObject(city);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public City removeCity(City city) {
        return null;
    }

    @Override
    public Department addDepart(Department department) {

        try {
            return addObject(department);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Department removeDepart(Department department) {
        return null;
    }

    @Override
    public boolean dropTable(Class clazz) {

        StringBuilder sqlDropTable = new StringBuilder();

        sqlDropTable.append("DROP TABLE ").append(clazz.getSimpleName()).append(";");

        return executeSQL(sqlDropTable.toString()) >= 0 ? true : false;
    }

    @Override
    public String nativeSQL(String sql) {

        return null;
    }


    private int executeSQL(String sql) {

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement();) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            return -1;
        }

    }

    private <T> List<T> querySQL(Class<T> clazz, String sql) throws InvocationTargetException,
            SQLException, InstantiationException,
            NoSuchMethodException, IllegalAccessException {

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql);) {
            return getListFromResultSet(clazz, rs);
        }
    }

    private <T> List<T> getListFromResultSet(Class<T> clazz, ResultSet rs)
            throws SQLException, IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {

        List<T> list = new ArrayList<>();

        while (rs.next()) {

            T obj = clazz.newInstance();

            while (clazz != null) {

                for (Field field : clazz.getDeclaredFields()) {

                    if (!field.isSynthetic()) {

                        String fieldName = field.getName();

                        String fieldType = field.getType().getSimpleName();

                        Object fieldValue;

                        fieldType = fieldTypeMap.get(fieldType);

                        String fieldNameTable = fieldName;

                        if (fieldType == null) {
                            fieldNameTable = fieldName + "Id";
                        }

                        fieldValue = rs.getObject(fieldNameTable);

                        setFieldValue(obj, fieldName, fieldValue);

                    }
                }

                clazz = (Class<T>) clazz.getSuperclass();

            }

            list.add(obj);

        }

        return list;

    }


    private <T> Map<String, String> getFieldsFromObject(T obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Class<T> clazz = (Class<T>) obj.getClass();

        Map<String, String> map = new HashMap<>();

        while (clazz != null) {

            for (Field field : clazz.getDeclaredFields()) {

                if (!field.isSynthetic()) {

                    String fieldName = field.getName();

                    String fieldValue = "";

                    String fieldType = field.getType().getSimpleName();

                    fieldType = fieldTypeMap.get(fieldType);

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
                            fieldName += "Id";
                            fieldValue = (String) getFieldValue(target, "Id");
                        }

                    } else {
                        fieldValue = (String) getFieldValue(obj, fieldName);
                        if (fieldType.equals("TEXT")) {
                            fieldValue = String.format("'%s'", fieldValue);
                        }
                    }
                    map.put(fieldName, fieldValue);
                }
            }

            clazz = (Class<T>) clazz.getSuperclass();
        }

        return map;
    }

    private <T> Object getFieldValue(T obj, String fieldName)
            throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {

        Object value;

        value = obj.getClass().getMethod("get" +
                fieldName.substring(0, 1).toUpperCase() +
                fieldName.substring(1)).
                invoke(obj).toString();

        return value;
    }

    public <T> void setFieldValue(T obj, String fieldName, Object fieldValue) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        obj.getClass().getMethod("set" +
                fieldName.substring(0, 1).toUpperCase() +
                fieldName.substring(1)).
                invoke(obj, fieldValue);
    }


    private <T> T addObject(T obj) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        StringBuilder sqlHeader = new StringBuilder();
        StringBuilder sqlValues = new StringBuilder();

        Class<T> classUser = (Class<T>) obj.getClass();

        sqlHeader.append("INSERT INTO ").append(classUser.getSimpleName()).append(" (");

        Map<String, String> map = getFieldsFromObject(obj);

        for (String key : map.keySet()) {
            sqlHeader.append(key).append(",");
            sqlValues.append(map.get(key)).append(",");
        }
        sqlHeader.deleteCharAt(sqlHeader.length() - 1).append(") ").
                append("VALUES (");
        sqlValues.deleteCharAt(sqlValues.length() - 1).append(");");

        return executeSQL(sqlHeader.append(sqlValues).toString()) >= 0 ? obj : null;
    }


}

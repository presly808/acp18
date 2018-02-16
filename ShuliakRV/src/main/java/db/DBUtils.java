package db;

import db.model.City;
import db.model.Department;
import db.model.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement();) {
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

        StringBuilder sqlHeader = new StringBuilder();
        StringBuilder sqlValues = new StringBuilder();

        Class classUser = userWithoutId.getClass();

        sqlHeader.append("INSERT INTO ").append(classUser.getSimpleName()).append(" (");

        while (classUser != null) {

            for (Field field : classUser.getDeclaredFields()) {

                if (!field.isSynthetic()) {

                    sqlHeader.append(field.getName()).append(",");

                    try {
                        String fieldType = field.getType().getSimpleName();

                        fieldType = keyValueMap.get(fieldType);

                        String fieldValue = "";

                        if (fieldType == null) {

                            Object target = field.get(userWithoutId);

                            if (target == null) {
                                fieldValue = "NULL";

                            } else {

                                try {
                                    fieldValue = target.getClass().getMethod("getId").
                                            invoke(target).toString();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                }

                            }

                        } else {
                            String fieldName = field.getName();
                            try {
                                fieldValue = userWithoutId.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)).
                                        invoke(userWithoutId).toString();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }

                            if (fieldType.equals("TEXT")) {
                                fieldValue = String.format("'%s'", fieldValue);
                            }

                        }

                        sqlValues.append(fieldValue).append(",");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            classUser = classUser.getSuperclass();
        }
        sqlValues.deleteCharAt(sqlValues.length() - 1).append(");");
        sqlHeader.deleteCharAt(sqlHeader.length() - 1).append(") ").
                append("VALUES (").append(sqlValues);

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement statement = conn.createStatement();)

        {
            conn.setAutoCommit(true);
            statement.execute(sqlHeader.toString());
        } catch (
                SQLException e)

        {
            e.printStackTrace();
            return null;
        }

        return userWithoutId;
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

        StringBuilder sqlCreateTable = new StringBuilder();

        sqlCreateTable.append("DROP TABLE ").append(clazz.getSimpleName()).append(";");

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement();) {
            statement.execute(sqlCreateTable.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public String nativeSQL(String sql) {
        return null;
    }


}

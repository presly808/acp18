package db;

import db.model.City;
import db.model.Department;
import db.model.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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
            return querySQL(User.class, "SELECT * FROM USER");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> getAllValues(Class<T> type) {
        try {
            return querySQL(type, "SELECT * FROM " + type.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> selectWithFilter(Class<T> type, Map<Field, Object> filters, Field orderBy, int limit) {

        List<T> list;

        StringBuilder joinStatement = new StringBuilder();

        StringBuilder whereStatement = new StringBuilder();


        for (Field key : filters.keySet()) {

            String fieldName = key.getName();

            String fieldType = key.getType().getSimpleName();

            Object fieldvalue = filters.get(key);

            fieldType = fieldTypeMap.get(fieldType);

            if (fieldType == null) {
                fieldName += "Id";

                joinStatement.append("JOIN ").append(key.getType().getSimpleName()).
                        append(" ON ").append(type.getSimpleName() + "." + fieldName + " = " +
                        key.getType().getSimpleName() + ".id");
            } else {
                whereStatement.append(fieldName + " = " + fieldvalue.toString()).append(" AND ");
            }

        }

        try {
            list = querySQL(type, "SELECT * FROM " + type.getSimpleName() + " " + joinStatement
                    + " WHERE " + whereStatement.toString() + " 1=1 " + " ORDER BY " + orderBy.getName() +
                    " LIMIT " + limit);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public <T> T addGen(Class<T> tClass, T obj) {

        StringBuilder sqlHeader = new StringBuilder();
        StringBuilder sqlValues = new StringBuilder();

        sqlHeader.append("INSERT INTO ").append(tClass.getSimpleName()).append(" (");

        Map<String, String> map = null;
        try {
            map = getFieldsFromObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        for (String key : map.keySet()) {
            sqlHeader.append(key).append(",");
            sqlValues.append(map.get(key)).append(",");
        }
        sqlHeader.deleteCharAt(sqlHeader.length() - 1).append(") ").
                append("VALUES (");
        sqlValues.deleteCharAt(sqlValues.length() - 1).append(");");

        return executeSQL(sqlHeader.append(sqlValues).toString()) > 0 ? obj : null;
    }


    @Override
    public <T> T removeGen(Class<T> tClass, T obj) {

        StringBuilder sql = new StringBuilder();

        sql.append("DELETE FROM ").append(tClass.getSimpleName()).append(" WHERE id = ");

        Map<String, String> map = null;
        try {
            map = getFieldsFromObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        sql.append(map.get("id")).append(";");

        return executeSQL(sql.toString()) > 0 ? obj : null;
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

        return executeSQL(sqlCreateTable.toString()) == 0 ? true : false;
    }

    @Override
    public boolean removeAllValues(Class clazz) {

        return executeSQL("DELETE FROM " + clazz.getSimpleName() + ";") > 0 ? true : false;
    }

    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() {

        Map<Department, List<User>> map = new HashMap<>();

        List<User> list = new ArrayList<User>();

        try {
            list = querySQL(User.class, "SELECT * FROM USER");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        for (User user : list) {

            List<User> l = map.get(user.department);

            if (l == null) {
                l = new ArrayList<User>();
            }
            l.add(user);
            map.put(user.getDepartment(), l);
        }

        return map;

    }

    @Override
    public Map<Department, Double> getAvgSalaryGroupByDepartment() {

        Map<Department, Double> map;

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement();) {

            map = new HashMap<>();

            ResultSet rs = statement.executeQuery("SELECT departmentId,AVG(salary) " +
                    "FROM USER GROUP BY departmentId");

            StringBuilder str = new StringBuilder();

            while (rs.next()) {

                map.put(querySQL(Department.class, "SELECT * FROM DEPARTMENT " +
                        "WHERE id = " + rs.getInt(1)).get(0), rs.getDouble(2));

            }

        } catch (Exception e) {
            return null;
        }

        return map;
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() {

        Map<User,List<User>> map = new HashMap<>();

        List<User> list = new ArrayList<User>();

        try {
            list = querySQL(User.class, "SELECT u2.* " +
                    "FROM USER u1, USER u2 WHERE u1.id = u2.manageId " +
                    "AND u1.cityId = (SELECT id FROM CITY WHERE name = 'Kiev')");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        for (User user : list) {

            List<User> l = map.get(user.manage);

            if (l == null) {
                l = new ArrayList<User>();
            }
            l.add(user);
            map.put(user.getManage(), l);
        }

        return map;
    }

    @Override
    public User addUser(User userWithoutId) {

        return addGen(User.class, userWithoutId);

    }

    @Override
    public User removeUser(User user) {

        return removeGen(User.class, user);
    }

    @Override
    public City addCity(City city) {

        return addGen(City.class, city);
    }

    @Override
    public City removeCity(City city) {

        List<City> list;

        try {
            list = querySQL(City.class,
                    "SELECT * FROM City WHERE id = " + city.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if ((list.size() > 0) && (removeGen(City.class, city) != null)) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public Department addDepart(Department department) {

        return addGen(Department.class, department);
    }

    @Override
    public Department removeDepart(Department department) {

        List<Department> list;

        try {
            list = querySQL(Department.class,
                    "SELECT * FROM Department WHERE id = " + department.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if ((list.size() > 0) && (removeGen(Department.class, department) != null)) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public boolean dropTable(Class clazz) {

        StringBuilder sqlDropTable = new StringBuilder();

        sqlDropTable.append("DROP TABLE ").append(clazz.getSimpleName()).append(";");

        return executeSQL(sqlDropTable.toString()) == 0 ? true : false;
    }

    @Override
    public String nativeSQL(String sql) {

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement();) {

            StringBuilder str = new StringBuilder();

            boolean res = statement.execute(sql);

            if (res) {
                ResultSet rs = statement.getResultSet();
                int columnCount = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    for (int i = 0; i < columnCount; ) {
                        str.append(rs.getString(i + 1));
                        if (++i < columnCount) str.append(",");
                    }
                    str.append(System.getProperty("line.separator"));
                }
            } else {
                str.append(String.valueOf(statement.getUpdateCount()));
            }

            return str.toString();

        } catch (SQLException e) {
            return "Error";
        }

    }

    private int executeSQL(String sql) {

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement();) {
            System.out.println(sql);
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            return -1;
        }

    }

    private <T> List<T> querySQL(Class<T> clazz, String sql) throws
            InvocationTargetException, SQLException, InstantiationException,
            NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql);) {
            return getListFromResultSet(clazz, rs);
        }
    }

    private <T> List<T> getListFromResultSet(Class<T> clazz, ResultSet rs)
            throws SQLException, IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        List<T> list = new ArrayList<>();

        while (rs.next()) {

            T obj = clazz.newInstance();

            Class<T> cl = clazz;

            while (cl != null) {

                for (Field field : cl.getDeclaredFields()) {

                    if (!field.isSynthetic()) {

                        String fieldName = field.getName();

                        String fieldType = field.getType().getSimpleName();

                        Object fieldValue;

                        fieldType = fieldTypeMap.get(fieldType);

                        if (fieldType == null) {

                            fieldValue = rs.getObject(fieldName + "Id");

                            if (fieldValue != null) {

                                fieldValue = querySQL(field.getType(),
                                        "SELECT * FROM " + field.getType().getSimpleName() + " WHERE id = "
                                                + rs.getInt(fieldName + "Id") + ";").get(0);
                            }
                        } else {

                            fieldValue = rs.getObject(fieldName);
                        }

                        setFieldValue(obj, fieldName, field.getType(), fieldValue);

                    }
                }

                cl = (Class<T>) cl.getSuperclass();

            }

            list.add(obj);

        }

        return list;

    }

    private <T> Map<String, String> getFieldsFromObject(T obj) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        Class clazz = obj.getClass();

        Map<String, String> map = new HashMap<>();

        while (clazz != null) {

            for (Field field : clazz.getDeclaredFields()) {

                if (!field.isSynthetic()) {

                    String fieldName = field.getName();

                    String fieldValue = "NULL";

                    String fieldType = field.getType().getSimpleName();

                    Object value = getFieldValue(obj, fieldName);

                    fieldType = fieldTypeMap.get(fieldType);

                    if (fieldType == null) {

                        fieldName += "Id";
                    }

                    if (value != null) {

                        if (fieldType == null) {

                            fieldValue = getFieldValue(value, "id").toString();

                        } else

                        {
                            fieldValue = value.toString();

                            if (fieldType.equals("TEXT")) {
                                fieldValue = String.format("'%s'", fieldValue);
                            }
                        }
                    }

                    map.put(fieldName, fieldValue);
                }

            }

            clazz = clazz.getSuperclass();
        }

        return map;
    }


    private <T> Object getFieldValue(T obj, String fieldName)
            throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {

        Method meth = obj.getClass().getMethod("get" +
                fieldName.substring(0, 1).toUpperCase() +
                fieldName.substring(1));

        return meth.invoke(obj);
    }

    private <T> void setFieldValue(T obj, String fieldName, Class fieldType, Object fieldValue) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method meth = obj.getClass().getMethod("set" +
                fieldName.substring(0, 1).toUpperCase() +
                fieldName.substring(1), fieldType);

        meth.invoke(obj, fieldValue);

    }


}

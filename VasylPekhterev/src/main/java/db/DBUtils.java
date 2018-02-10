package db;

import db.Reflection.*;
import db.model.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static db.Reflection.ReflectionUtils.*;

/**
 * Created by serhii on 03.02.18.
 */
public class DBUtils implements IDB {

    private SqlUtils sqlUtils;

    DBUtils(String url) {
        sqlUtils = new SqlUtils(url);
    }

    public List<User> getAll() {
        return sqlUtils.getAllValues(User.class);
    }

    @Override
    public <T> List<T> getAllValues(Class<T> clazz) {
        return sqlUtils.getAllValues(clazz);
    }

    @Override
    public boolean removeAllValues(Class clazz) {
        int count = sqlUtils.removeAllValues(clazz);
        return count > 0;
    }

    @Override
    public <T> List<T> selectWithFilter(Class<T> type, Map<Field, Object> filters, Field orderBy, int limit) {
        return sqlUtils.selectWithFilter(type, filters, orderBy, limit);
    }

    @Override
    public <T> T addGen(Class<T> tClass, T obj) {
        return sqlUtils.addObject(tClass, obj) ? obj : null;
    }

    @Override
    public <T> T removeGen(Class<T> tClass, T obj) {
        try {
            Field primaryKeyField = getPrimaryKeyField(tClass);
            Object primaryKeyValue = getFieldValue(tClass, obj, primaryKeyField);
            T fullObject = sqlUtils.selectByPrimaryKey(tClass, primaryKeyValue);
            return sqlUtils.removeObject(tClass, primaryKeyValue) ? fullObject : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createTable(Class clazz) {
        return clazz.getAnnotation(TableClass.class) != null && sqlUtils.createTable(clazz);
    }

    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() {
        Field groupField = null;
        try {
            groupField = User.class.getDeclaredField("department");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return getEntitiesGroupByField(User.class, groupField);
    }

    private <K, T> Map<K, List<T>> getEntitiesGroupByField(Class<T> clazz, Field groupField) {

        try {
            List<T> allEntities = getAllValues(clazz);
            Function<T, K> groupFunc = convertGetterToFunc(clazz, groupField);
            return allEntities.stream().collect(Collectors.groupingBy(groupFunc));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<Department, Integer> getAvgSalaryGroupByDepartment() {
        Field groupField = null;
        Field agrField = null;
        try {
            agrField = User.class.getDeclaredField("salary");
            groupField = User.class.getDeclaredField("department");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return getAgrFuncOnFieldGroupByField(User.class, "AVG", agrField, groupField);
    }

    public Map<City, Integer> countGroupByCity() {
        Field groupField = null;
        Field agrField = null;
        try {
            agrField = User.class.getDeclaredField("age");
            groupField = User.class.getDeclaredField("city");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return getAgrFuncOnFieldGroupByField(User.class, "COUNT", agrField, groupField);
    }

    private <T, V, K> Map<K, V> getAgrFuncOnFieldGroupByField(
            Class<T> clazz, String agrFunc, Field agrField, Field groupField) {

        return sqlUtils.getAgrFuncOnFieldGroupByField(clazz, agrFunc, agrField, groupField);
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() {
        List<User> allUsers = getAllValues(User.class);
        List<User> usersWithNotNullManagers = allUsers.stream().filter(u -> u.getManage() != null).collect(Collectors.toList());
        Map<User, List<User>> usersGroupByManagers = usersWithNotNullManagers.stream().collect(Collectors.groupingBy(User::getManage));

        return usersGroupByManagers.entrySet().stream().filter(e -> e.getKey().getCity().getName().equals("Kiev")).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
        return removeGen(City.class, city);
    }

    @Override
    public Department addDepart(Department department) {
        return addGen(Department.class, department);
    }

    @Override
    public Department removeDepart(Department department) {
        return removeGen(Department.class, department);
    }

    @Override
    public boolean dropTable(Class clazz) {
        return sqlUtils.dropTable(clazz);
    }

    @Override
    public String nativeSQL(String sql) {
        return sqlUtils.executeQuery(sql);
    }
}

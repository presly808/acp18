package db;

import db.model.City;
import db.model.Department;
import db.model.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 03.02.18.
 */
public interface IDB {

    List<User> getAll();

    <T> List<T> getAllValues(Class<T> type);

    <T> List<T> selectWithFilter(Class<T> type, Map<Field,Object> filters, Field orderBy, int limit);

    <T> T addGen(Class<T> tClass, T obj);

    <T> T removeGen(Class<T> tClass, T obj);

    boolean createTable(Class clazz);

    boolean removeAllValues(Class clazz);

    Map<Department, List<User>> getUsersGroupByDepartment();

    Map<Department, Integer> getAvgSalaryGroupByDepartment();

    Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev();

    User addUser(User userWithoutId);

    User removeUser(User user);

    City addCity(City city);

    City removeCity(City city);

    Department addDepart(Department department);
    Department removeDepart(Department department);

    boolean dropTable(Class clazz);

    String nativeSQL(String sql);

    public <T> void setFieldValue(T obj, String fieldName, Object fieldValue) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;

}

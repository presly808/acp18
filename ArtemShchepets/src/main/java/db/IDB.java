package db;

import db.model.City;
import db.model.Department;
import db.model.User;
import multithreading.forkJoin.Filter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 03.02.18.
 */
public interface IDB {

    List<User> getAll();

    List<User> selectWithFilter(Map<Field, Object> filters, Field orderBy, int limit);

    boolean fillTable(String csvUrl);

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

}

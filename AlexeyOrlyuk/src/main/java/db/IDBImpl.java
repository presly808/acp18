package db;

import db.model.City;
import db.model.Department;
import db.model.User;
import db.utils.DBUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by alex323glo on 04.02.18.
 */
public class IDBImpl implements IDB {

    private DBUtils dbUtils;

    public IDBImpl(DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    @Override
    public List<User> getAll() {
        return dbUtils.getAllValues(User.class);
    }

    @Override
    public <T> List<T> getAllValues(Class<T> type) {
        return dbUtils.getAllValues(type);
    }

    @Override
    public <T> List<T> selectWithFilter(Class<T> type, Map<Field, Object> filters, Field orderBy, int limit) {
        return dbUtils.selectWithFilter(type, filters, orderBy, limit);
    }

    @Override
    public <T> T addGen(Class<T> tClass, T obj) {
        return dbUtils.add(tClass, obj);
    }

    @Override
    public <T> T removeGen(Class<T> tClass, T obj) {
        return dbUtils.remove(tClass, obj);
    }

    @Override
    public boolean createTable(Class clazz) {
        return dbUtils.createTable(clazz);
    }

    @Override
    public boolean removeAllValues(Class clazz) {
        return dbUtils.removeAllValues(clazz);
    }

    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() {
        // TODO complete
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<Department, Integer> getAvgSalaryGroupByDepartment() {
        // TODO complete
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() {
        // TODO complete
        throw new UnsupportedOperationException();
    }

    @Override
    public User addUser(User userWithoutId) {
        return dbUtils.add(User.class, userWithoutId);
    }

    @Override
    public User removeUser(User user) {
        return dbUtils.remove(User.class, user);
    }

    @Override
    public City addCity(City city) {
        return dbUtils.add(City.class, city);
    }

    @Override
    public City removeCity(City city) {
        return dbUtils.remove(City.class, city);
    }

    @Override
    public Department addDepart(Department department) {
        return dbUtils.add(Department.class, department);
    }

    @Override
    public Department removeDepart(Department department) {
        return dbUtils.remove(Department.class, department);
    }

    @Override
    public boolean dropTable(Class clazz) {
        return dbUtils.dropTable(clazz);
    }

    @Override
    public String nativeSQL(String sql) {
        return dbUtils.nativeSQL(sql);
    }

}

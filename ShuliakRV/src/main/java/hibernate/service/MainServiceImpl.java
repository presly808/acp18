package hibernate.service;


import hibernate.dao.CityDao;
import hibernate.dao.DepartmentDao;
import hibernate.dao.UserDao;
import hibernate.exception.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 10.02.18.
 */
public class MainServiceImpl implements MainService {

    private final Logger logger = Logger.getLogger(MainServiceImpl.class);

    private UserDao userDao;
    private DepartmentDao departmentDao;
    private CityDao cityDao;

    private EntityManagerFactory factory;

    public MainServiceImpl(EntityManagerFactory factory) {

        this.factory = factory;

        userDao = new UserDao(factory, User.class);
        departmentDao = new DepartmentDao(factory, Department.class);
        cityDao = new CityDao(factory,City.class);

    }

    @Override
    public User register(User user) throws AppException {

        if (user == null) {
            logger.info("User can't registered!");
            throw new AppException("User is NULL!");
        }

        User entity = userDao.update(user);

        if (entity == null) {
            logger.info("User wasn't registered!");
            throw new AppException("User wasn't registered!");
        }

        logger.info("User was registered!");

        return entity;
    }

    @Override
    public City addCity(City city) throws AppException {

        if (city == null) {
            logger.info("City can't registered!");
            throw new AppException("City is NULL!");
        }

        City entity = cityDao.update(city);

        if (entity == null) {
            logger.info("City wasn't added!");
            throw new AppException("City wasn't added!");
        }

        logger.info("City was added!");

        return entity;
    }

    @Override
    public Department addDepartment(Department department) throws AppException {

        if (department == null) {
            logger.info("Department can't registered!");
            throw new AppException("Department is NULL!");
        }

        Department entity = departmentDao.update(department);

        if (entity == null) {
            logger.info("Department wasn't added!");
            throw new AppException("Department wasn't added!");
        }

        logger.info("Department was added!");

        return entity;
    }

    @Override
    public List<User> findAll() throws AppException {

        List<User> list = userDao.findAll();

        if (list == null) {
            logger.info("Users wasn't found!");
            throw new AppException("Users wasn't found!");
        }

        logger.info("Users was found!");

        return list;
    }

    @Override
    public User update(User user) throws AppException {

        if (user == null) {
            logger.info("User can't updated!");
            throw new AppException("User is NULL!");
        }

        User entity = userDao.update(user);

        if (entity == null) {
            logger.info("User wasn't updated!");
            throw new AppException("User wasn't updated!");
        }

        logger.info("User was updated!");

        return entity;
    }

    @Override
    public User remove(User user) throws AppException {

        if (user == null) {
            logger.info("User can't removed!");
            throw new AppException("User is NULL!");
        }

        User entity = userDao.remove(user.getId());

        if (entity == null) {
            logger.info("User wasn't removed!");
            throw new AppException("User wasn't removed!");
        }

        logger.info("User was removed!");

        return entity;
    }

    private <K, V> Map<K, List<V>> fromListToMap(List<Object[]> list) {

        if (list == null) return null;

        Map<K, List<V>> map = new HashMap<>();

        for (Object[] fields : list) {

            List<V> listValue = map.get((K) fields[0]);

            if (listValue == null) {
                listValue = new ArrayList<V>();
            }

            listValue.add((V) fields[1]);

            map.put((K) fields[0], listValue);

        }

        return map;
    }

    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() throws AppException {

        Map<Department, List<User>> map;

        map = fromListToMap(userDao.getUsersGroupByDepartment());

        if (map == null) {
            logger.info("Users grouped by department wasn't found!");
            throw new AppException("Users grouped by department wasn't found!");
        }

        logger.info("Users grouped by department was found!");

        return map;
    }

    @Override
    public Map<Department, Double> getAvgSalaryGroupByDepartment() throws AppException {

        Map<Department, Double> map;

        map = fromListToMap(userDao.getAvgSalaryGroupByDepartment());

        if (map == null) {
            logger.info("Avarage salary grouped by department wasn't found!");
            throw new AppException("Avarage salary grouped by department wasn't found!");
        }

        logger.info("Avarage salary grouped by department was found!");

        return map;
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() throws AppException {

        Map<User, List<User>> map;

        map = fromListToMap(userDao.getUsersGroupByManagersAndOrderedThatLiveInCity("Kiev"));

        if (map == null) {
            logger.info("Users grouped by managers that live in Kiev wasn't found!");
            throw new AppException("Users grouped by managers that live in Kiev wasn't found!");
        }

        logger.info("Users grouped by managers that live in Kiev was found!");

        return map;
    }

    @Override
    public List<User> findByName(String name) throws AppException {

        if (name == null) {
            logger.info("Users can't found!");
            throw new AppException("Name is NULL!");
        }

        List<User> list = userDao.findByName(name);

        if (list == null) {
            logger.info("Users wasn't found!");
            throw new AppException("Users wasn't found!");
        }

        logger.info("Users was found!");

        return list;
    }

    @Override
    public List<User> findInRange(double minSal, double maxSal) throws AppException {

        if ((minSal < 0 || maxSal < 0) || (maxSal < minSal)) {
            logger.info("Users can't found!");
            throw new AppException("Incorrect range!");
        }

        List<User> list = userDao.findInRange(minSal, maxSal);

        if (list == null) {
            logger.info("Users wasn't found!");
            throw new AppException("Users wasn't found!");
        }

        logger.info("Users was found!");

        return list;

    }

    @Override
    public List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppException {

        if (start.isAfter(end)) {
            logger.info("Users can't found!");
            throw new AppException("Incorrect dates!");
        }

        List<User> list = userDao.findByDate(start, end);

        if (list == null) {
            logger.info("Users wasn't found!");
            throw new AppException("Users wasn't found!");
        }

        logger.info("Users was found!");

        return list;

    }
}

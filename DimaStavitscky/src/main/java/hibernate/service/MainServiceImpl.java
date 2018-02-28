package hibernate.service;

import hibernate.dao.DepartmentDao;
import hibernate.dao.UserDao;
import hibernate.dao.exclude.CityDaoImpl;
import hibernate.dao.Dao;
import hibernate.dao.exclude.DepartmentDaoImpl;
import hibernate.dao.exclude.UserDaoImpl;
import hibernate.exception.exclude.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by serhii on 10.02.18.
 */

@Component
public class MainServiceImpl implements MainService {

    private static final Logger LOGGER = Logger.getLogger(MainServiceImpl.class);
    @Autowired
    private UserDao daoUser;
    @Autowired
    private Dao<City, Integer> daoCity;
    @Autowired
    private DepartmentDao daoDepartment;

    int a;
    String line;
    protected final User initializeBlock() {
        a = 5;
        line = "Dima";
        return new User();
    }


    /*public MainServiceImpl(EntityManagerFactory factory) {
        daoUser = new UserDaoImpl(factory);
        daoCity = new CityDaoImpl(factory);
        daoDepartment = new DepartmentDaoImpl(factory);
    }*/

    @Override
    public List<User> getAllUsers() throws AppException {
        return daoUser.findAll();
    }

    @Override
    public User login(String login, String pass) throws IllegalArgumentException {
        return daoUser.getUserByLoginAndPass(login, pass);
    }

    @Override
    public User register(User user) throws AppException {
        if (user == null) {
            LOGGER.error("user is null");

        } else {
            daoUser.create(user);
        }
        return user;
    }

    @Override
    public Department addDepartment(Department department) throws AppException {
        if (department == null) {
            LOGGER.error("department is null");

        } else {
            daoDepartment.create(department);
        }
        return department;
    }

    @Override
    public City addCity(City city) throws AppException {
        if (city == null) {
            throw new AppException("city is null");

        } else {
            return daoCity.create(city);
        }
    }

    @Override
    public User update(User user) throws AppException {
        if (user == null) {
            LOGGER.error("user is null");

        } else {
            User updated = daoUser.update(user);
            if(updated == null) throw new AppException("user not in database");
        }
        return user;
    }

    @Override
    public User remove(User user) throws AppException {
        if (user == null) {
            LOGGER.error("user is null");

        } else {
            daoUser.remove(user.getId());
        }
        return user;
    }

    @Override
    public User findById(int id) throws AppException {
        return daoUser.find(id);
    }

    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() throws AppException {
        return daoDepartment.getUsersGroupByDepartment();
        /*List<User> users = daoUser.findAll();
        List<Department> departments = daoDepartment.findAll();

        if (users.isEmpty()) {
            LOGGER.error("no users in the database");
            return null;

        } else if (departments.isEmpty()) {
            LOGGER.error("no departments in the database");
            return null;
        }

        Map<Department, List<User>> resMap = new HashMap<>();

        departments.forEach(d -> resMap.put(d, new ArrayList<>()));
        users.forEach(u -> resMap.get(u.getDepartment()).add(u));

        return resMap;*/
    }

    @Override
    public Map<Department, Integer> getAvgSalaryGroupByDepartment() throws AppException {
        Map<Department, List<User>> map = getUsersGroupByDepartment();

        if (map == null) return null;

        Map<Department, Integer> resMap = new HashMap<>();

        map.forEach((key, value) -> resMap.put(key, (int) value
                .stream()
                .mapToDouble(User::getSalary)
                .average()
                .orElse(0)));

        return resMap;
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() throws AppException {
        return daoUser.ge
        /*Map<User, List<User>> users = daoUser.getUsersGroupByManager();

        if (users.isEmpty()) {
            LOGGER.error("no users in the database");
            return null;
        }

        Map<User, List<User>> resMap = new HashMap<>();
        String city = "Kiev";

        // add to resMap keys
        users.stream()
                .filter(u -> !resMap.containsKey(u.getManage()))
                .forEach(u -> resMap.put(u.getManage(), new ArrayList<>()));

        // add to resMap values
        users.stream()
                .filter(u -> u.getCity().getName().equals(city))
                .forEach(u -> resMap.get(u.getManage()).add(u));

        return resMap;*/
    }

    @Override
    public List<User> findByName(String name) throws AppException {
        List<User> users = daoUser.findAll();

        if (users.isEmpty()) {
            LOGGER.error("no users in the database");
            return null;
        }

        return users.stream()
                .filter(u -> u.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findInRange(double minSal, double maxSal) throws AppException {
        List<User> users = daoUser.findAll();

        if (users.isEmpty()) {
            LOGGER.error("no users in the database");
            return null;
        }

        return users.stream()
                .filter(u -> u.getSalary() > minSal && u.getSalary() < maxSal)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppException {
        List<User> users = daoUser.findAll();
        if (users.isEmpty()) {
            LOGGER.error("no users in the database");
            return null;
        }

        return users.stream()
                .filter(u -> u.getLocalDateTime().isAfter(start)
                        && u.getLocalDateTime().isBefore(end))
                .collect(Collectors.toList());

    }
}

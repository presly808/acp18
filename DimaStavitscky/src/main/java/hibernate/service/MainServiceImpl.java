package hibernate.service;

import hibernate.dao.CityDao;
import hibernate.dao.DepartmentDao;
import hibernate.dao.UserDao;
import hibernate.exception.exclude.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MainServiceImpl implements MainService {

    private static final Logger LOGGER = Logger.getLogger(MainServiceImpl.class);
    @Autowired
    private UserDao daoUser;
    @Autowired
    private CityDao daoCity;
    @Autowired
    private DepartmentDao daoDepartment;

    @Transactional
    @Override
    public List<User> getAllUsers() throws AppException {
        return daoUser.findAll();
    }

    @Transactional
    @Override
    public User login(String login, String pass) throws IllegalArgumentException {
        if(login == null || pass == null ||
                login.isEmpty() || pass.isEmpty()) {

            LOGGER.error("Can't log in, login or pass is null or is empty, time: " +
            LocalDateTime.now().toString());
            throw new IllegalArgumentException("Incorrect login or password format");
        }
        return daoUser.getUserByLoginAndPass(login, pass);
    }

    @Transactional
    @Override
    public User register(User user) throws AppException {
        if (user == null) {
            logErrorAndThrowExc("User", "register");

        } else {
            daoUser.create(user);
        }
        return user;
    }

    @Transactional
    @Override
    public Department addDepartment(Department department) throws AppException {
        if (department == null) {
            logErrorAndThrowExc("Department", "add");

        } else {
            daoDepartment.create(department);
        }
        return department;
    }

    @Transactional
    @Override
    public City addCity(City city) throws AppException {
        if (city == null) {
            logErrorAndThrowExc("City", "add");
        }
        return daoCity.create(city);
    }

    @Transactional
    @Override
    public User update(User user) throws AppException {
        if (user == null) {
            logErrorAndThrowExc("User", "update");

        } else {
            User updated = daoUser.update(user);
            if(updated == null) throw new AppException("user not in database");
        }
        return user;
    }

    @Transactional
    @Override
    public User remove(User user) throws AppException {
        if (user == null) {
            logErrorAndThrowExc("User", "remove");

        } else {
            daoUser.remove(user.getId());
        }
        return user;
    }

    @Transactional
    @Override
    public User findById(int id) throws AppException {
        if (id < 1) {
            logErrorAndThrowExc("id", "find by");
        }

        User foundUser = daoUser.find(id);
        if (foundUser == null) {
            throw new AppException("User didn't find in DB, id: " + id);
        }

        return foundUser;
    }

    @Transactional
    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() throws AppException {
        return daoDepartment.getUsersGroupByDepartment();
    }

    @Transactional
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

    @Transactional
    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() throws AppException {
        return daoUser.getUsersGroupByManagerAndOrderByCity("Kiev");
    }

    @Transactional
    @Override
    public List<User> findByName(String name) throws AppException {
        if(name == null || name.isEmpty()) {
            logErrorAndThrowExc("name", "find by");
        }
        return daoUser.findByName(name);
    }

    @Transactional
    @Override
    public List<User> findInRange(double minSal, double maxSal) throws AppException {
        List<User> users = daoUser.findAll();

        return users.stream()
                .filter(u -> u.getSalary() > minSal && u.getSalary() < maxSal)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppException {
        return daoUser.findByDate(start, end);
    }

    /*
    @return: Integer count of deleted objects
     */
    @Transactional
    @Override
    public Integer dropAllTables() {
        return daoUser.deleteTable() +
                daoDepartment.deleteTable() +
                daoCity.deleteTable();
    }

    @Transactional
    @Override
    public Department findDepartmentByName(String name) throws AppException {
        try {
            return daoDepartment.findByName(name);

        } catch (NoResultException e){
            throw new AppException("Can't find the department, it is not in the database, name: " +
            name);
        }
    }

    @Transactional
    @Override
    public City findCityByName(String name) throws AppException {
        try {
            return daoCity.findByName(name);

        } catch (NoResultException e){
            throw new AppException("Can't find the city, it is not in the database, name: " +
                    name);
        }
    }

    private void logErrorAndThrowExc(String entityName, String methodName) throws AppException {
        LOGGER.error(String.format("Can't %s a %s, %s is null, time: %s", methodName,
                entityName, entityName, LocalDateTime.now().toString()));

        throw new AppException(entityName + " is null");
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public UserDao getDaoUser() {
        return daoUser;
    }

    public void setDaoUser(UserDao daoUser) {
        this.daoUser = daoUser;
    }

    public CityDao getDaoCity() {
        return daoCity;
    }

    public void setDaoCity(CityDao daoCity) {
        this.daoCity = daoCity;
    }

    public DepartmentDao getDaoDepartment() {
        return daoDepartment;
    }

    public void setDaoDepartment(DepartmentDao daoDepartment) {
        this.daoDepartment = daoDepartment;
    }
}

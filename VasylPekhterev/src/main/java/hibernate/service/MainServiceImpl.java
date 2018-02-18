package hibernate.service;

import hibernate.dao.Dao;
import hibernate.exception.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by serhii on 10.02.18.
 */
public class MainServiceImpl implements MainService {

    private final Dao<City, Integer> cityDao;
    private final Dao<Department, Integer> departmentDao;
    private final Dao<User, Integer> userDao;

    public MainServiceImpl(Dao<City, Integer> cityDao, Dao<Department, Integer> departmentDao, Dao<User, Integer> userDao) {
        this.cityDao = cityDao;
        this.departmentDao = departmentDao;
        this.userDao = userDao;
    }

    @Override
    public User register(User user){
        return userDao.create(user);
    }

    @Override
    public Department addDepartment(Department department) throws AppException {
        return departmentDao.create(department);
    }

    @Override
    public User update(User user) throws AppException {
        return userDao.update(user);
    }

    @Override
    public User remove(User user) throws AppException {
        return userDao.remove(user.getId());
    }

    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() throws AppException {
        List<User> allUsers = userDao.findAll();
        return allUsers.stream().collect(Collectors.groupingBy(User::getDepartment));
    }

    @Override
    public Map<Department, Double> getAvgSalaryGroupByDepartment() throws AppException {
        return userDao.getAvgSalaryGroupByDepartment();
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() throws AppException {
        List<User> allUsers = userDao.findAll();
        List<User> usersWithNotNullManagers = allUsers.stream().filter(u -> u.getManage() != null).collect(Collectors.toList());
        Map<User, List<User>> usersGroupByManagers = usersWithNotNullManagers.stream().collect(Collectors.groupingBy(User::getManage));

        return usersGroupByManagers.entrySet().stream().filter(e -> e.getKey().getCity().getName().equals("Kiev")).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<User> findByName(String name) throws AppException {
        return userDao.findByName(name);
    }

    @Override
    public List<User> findInRange(double minSal, double maxSal) throws AppException {
        return userDao.findInRange(minSal, maxSal);
    }

    @Override
    public List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppException {
        return userDao.findByDate(start, end);
    }
}

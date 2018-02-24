package hibernate.service;

import hibernate.dao.Dao;
import hibernate.exception.AppException;
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

    private Dao<User, Integer> userDao;
    private Dao<Department, Integer> departmentDao;

    public MainServiceImpl(Dao<User, Integer> userDao, Dao<Department, Integer> deptDao) {
        this.userDao = userDao;
        this.departmentDao = deptDao;
    }

    //TODO add validation and OrderByKiev method

    @Override
    public User register(User user) throws AppException {
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

        return userDao.findAll().stream().collect(Collectors.groupingBy(User::getDepartment));
    }
    @Override
    public Map<Department, Double> getAvgSalaryGroupByDepartment() throws AppException {

        return userDao.findAll().stream()
                .collect(Collectors.groupingBy(
                        User::getDepartment,
                        Collectors.averagingDouble(User::getSalary)
                ));
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() throws AppException {
        return null;
    }

    @Override
    public List<User> findByName(String name) throws AppException {
        return userDao.findAll().stream()
                .filter(user -> name.equals(user.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findInRange(double minSal, double maxSal) throws AppException {
        return userDao.findAll().stream()
                .filter(user -> user != null && user.getSalary() >= minSal && user.getSalary() <= maxSal)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppException {
        return userDao.findAll().stream()
                .filter(user -> user != null &&
                        !user.getLocalDateTime().isBefore(start) &&
                        !user.getLocalDateTime().isAfter(end))
                .collect(Collectors.toList());
    }
}

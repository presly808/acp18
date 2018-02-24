package hibernate.service;

import hibernate.dao.*;
import hibernate.exception.AppException;
import hibernate.model.Department;
import hibernate.model.User;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by serhii on 10.02.18.
 */
public class MainServiceImpl implements MainService {

    private static final Logger LOGGER = Logger.getLogger(MainServiceImpl.class);

    private UserDao userDao;
    private DepartmentDao departmentDao;

    public MainServiceImpl(UserDao userDao, DepartmentDao departmentDao) {
        this.userDao = userDao;
        this.departmentDao = departmentDao;
    }

    @Override
    public User register(User user) throws AppException {
        if (user == null){
            LOGGER.error("User is null!");
            throw new AppException("NullUserException");
        }
        LOGGER.info("start registration  User: " + user.getName());
       return userDao.create(user);

    }

    @Override
    public Department addDepartment(Department department) throws AppException {
        if (department == null) {
            LOGGER.error("Departments is null!");
            throw new AppException("NullDepartmentException");
        }
        return departmentDao.create(department);
    }

    @Override
    public User update(User user) throws AppException {
        return userDao.update(user);
    }

    @Override
    public User remove(User user) throws AppException {
        if (user == null || user.getId() <=0){
            LOGGER.error("User is null");
            throw new AppException("NullUserException");
        }
        return userDao.remove(user.getId());
    }

    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() throws AppException {
        Map<Department, List<User>> userMapByDepartment = new HashMap<>();
        List<User> allUsers = userDao.findAll();
        if (allUsers == null || allUsers.size() ==0){
            LOGGER.error("No users. Table users is empty");
        }
        List<Department> allDepartments = departmentDao.findAll();
        if (allDepartments.size() == 0){
            LOGGER.error("No departments. Table departments is empty");
        }
        for (Department department : allDepartments) {
           userMapByDepartment.put(department, allUsers.stream()
                   .filter(user -> user.getDepartment()
                   .equals(department))
                   .collect(Collectors.toList()));
        }
        return userMapByDepartment;
    }

    @Override
    public Map<Department, Double> getAvgSalaryGroupByDepartment() throws AppException {
        List<User> allUsers = userDao.findAll();
        if (allUsers == null || allUsers.size() ==0){
            LOGGER.error("No users. Table users is empty");
        }
        Map<Department, Double> avgSalByDep = allUsers.stream()
                .collect(Collectors.groupingBy(User::getDepartment,
                        Collectors.averagingDouble(User::getSalary)));
        return avgSalByDep;
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() throws AppException {
        List<User> allUsers = userDao.findAll();
        if (allUsers == null || allUsers.size() ==0){
            LOGGER.error("No users. Table users is empty");
        }
        Map<User, List<User>> groupByManagers = allUsers.stream()
                .filter(user -> user.getManage()!=null)
                .collect(Collectors.groupingBy(
                        User::getManage,
                        Collectors.collectingAndThen(Collectors.toList(), list->{
                            list.sort((el1, el2) ->
                            "Kiev".equals(el1.getCity().getName()) ? -1 :
                            "Kiev".equals(el2.getCity().getName()) ? 1 : el2.getCity().getName().compareTo(el1.getCity().getName()));
                            return list;
                        })));
        if (groupByManagers==null){
            LOGGER.error("Users weren't grouped by Department");
        }
        return groupByManagers;
    }

    @Override
    public List<User> findByName(String name) throws AppException {
        if (name == null){
            LOGGER.error("Name is null");
            throw new AppException("NullNameException");
        }
        //todo maybe better write analog method in dao
        List<User> listByName = userDao.findAll().stream().filter(user ->
            name.equals(user.getName())
        ).collect(Collectors.toList());
        if (listByName == null || listByName.size() == 0){
            LOGGER.error(String.format("DB don`t have users with name: %s", name));
        }
        return listByName;
    }

    @Override
    public List<User> findInRange(double minSal, double maxSal) throws AppException {
        if (minSal < 0 || maxSal < 0){
            LOGGER.error("Wrong input min/max salary!");
            throw new AppException("Invalid range");
        }
       List<User> listByRange = userDao.findAll().stream().filter(user ->
                user.getSalary() > minSal && user.getSalary() < maxSal).collect(Collectors.toList());
        if (listByRange == null || listByRange.size() == 0){
            LOGGER.error("DB don`t have users with this salary range");
        }
        return listByRange;
    }

    @Override
    public List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppException {
        if (start == null || end == null){
            LOGGER.error("Invalid date");
            throw new AppException("NullDateException");
        }
        List<User> userByDate = userDao.findAll().stream().filter(user ->
        user.getLocalDateTime().isAfter(start) && user.getLocalDateTime().isBefore(end)).collect(Collectors.toList());
        if (userByDate == null || userByDate.size() ==0){
            LOGGER.error("DB don`t have userd with this date parameters");
        }
        return userByDate;
    }

}

package hibernate.service;

import hibernate.dao.Dao;
import hibernate.dao.DaoCity;
import hibernate.dao.DaoUser;
import hibernate.exception.AppExceptionExclude;
import hibernate.model.Department;
import hibernate.model.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

/**
 * Created by serhii on 10.02.18.
 */
public class MainServiceImpl implements MainService {

    private static final Logger LOGGER = Logger.getLogger((MainServiceImpl.class));

    private DaoUser userDao;
    private Dao<Department, Integer> departmentDao;


    public MainServiceImpl(DaoUser userDao, Dao<Department, Integer> departmentDao, DaoCity daoCity) {
        this.userDao = userDao;
        this.departmentDao = departmentDao;
    }

    @Override
    public User register(User user) throws AppExceptionExclude {

        User createdUser = userDao.create(user);

        if (createdUser == null) {
            LOGGER.error("User wasn't registered");
        } else {
            LOGGER.info("User was registered");
        }

        return createdUser;
    }

    @Override
    public Department addDepartment(Department department) throws AppExceptionExclude {

        Department createdDepartment = departmentDao.create(department);

        if (createdDepartment == null) {
            LOGGER.error("Department wasn't added");
        } else {
            LOGGER.info("Department was added");
        }

        return createdDepartment;

    }

    @Override
    public User update(User user) throws AppExceptionExclude {

        User oldUser = userDao.update(user);

        if (oldUser == null) {
            LOGGER.error("User wasn't added");
            throw new AppExceptionExclude("User wasn't added");
        } else {
            LOGGER.info("User was successfully added");
        }

        return oldUser;
    }

    @Override
    public User remove(User user) throws AppExceptionExclude {

        User removedUser = userDao.remove(user.getId());

        if (removedUser == null) {
            LOGGER.error("User wasn't removed");
            throw new AppExceptionExclude("User wasn't removed");
        } else {
            LOGGER.info("User was removed");
        }

        return removedUser;
    }

    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() throws AppExceptionExclude {

        List<User> allUsers = userDao.findAll();

        Map<Department, List<User>> departmentListMap = allUsers.stream()
                .collect(Collectors.groupingBy(User::getDepartment, Collectors.toList()));

        return departmentListMap;
    }

    @Override
    public Map<Department, Double> getAvgSalaryGroupByDepartment() throws AppExceptionExclude {

        List<Object[]> resultQuery = userDao.getAvgSalaryGroupByDepartment();

        Map<Department, Double> result = new HashMap<>();

        for(Object[] row : resultQuery){
            result.put(((Department) row[0]), (Double) row[1]);
        }

        return result;
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() throws AppExceptionExclude {

        List<User> resultQuery = userDao.getUsersByCityAndOrdered("Kiev");

        Map<User, List<User>> departmentListMap = resultQuery.stream()
                .collect(Collectors.groupingBy(User::getManage, Collectors.toList()));

        return departmentListMap;
    }

    @Override
    public List<User> findByName(String name) throws AppExceptionExclude {
        return userDao.findByName(name);
    }

    @Override
    public List<User> findInRange(double minSal, double maxSal) throws AppExceptionExclude {
        return userDao.findInSalaryRange(minSal, maxSal);
    }

    @Override
    public List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppExceptionExclude {
        return userDao.findByDateRange(start, end);
    }

    public User findUserById(Integer id){
        return userDao.find(id);
    }
}

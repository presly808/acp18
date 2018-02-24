package hibernate.service;

import hibernate.dao.Dao;
import hibernate.dao.DaoImpl;
import hibernate.exception.AppException;
import hibernate.model.Department;
import hibernate.model.User;
import org.apache.log4j.Logger;
import spring.UserDao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 10.02.18.
 */
public class MainServiceImpl implements MainService {

    private final Logger logger = Logger.getLogger(MainServiceImpl.class);

    private Dao<User, Integer> userDao;
    private Dao<Department, Integer> departmentDao;
    private EntityManagerFactory factory;

    public MainServiceImpl() {
        factory = Persistence.createEntityManagerFactory("hibernate-unit");
        userDao = new DaoImpl<User, Integer>(factory);
        departmentDao = new DaoImpl<Department, Integer>(factory);
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
    public User update(User user) throws AppException {
        return null;
    }

    @Override
    public User remove(User user) throws AppException {
        return null;
    }

    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() throws AppException {
        return null;
    }

    @Override
    public Map<Department, Integer> getAvgSalaryGroupByDepartment() throws AppException {
        return null;
    }

    @Override
    public Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() throws AppException {
        return null;
    }

    @Override
    public List<User> findByName(String name) throws AppException {
        return null;
    }

    @Override
    public List<User> findInRange(double minSal, double maxSal) throws AppException {
        return null;
    }

    @Override
    public List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppException {
        return null;
    }
}

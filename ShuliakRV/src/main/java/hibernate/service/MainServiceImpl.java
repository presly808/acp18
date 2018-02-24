package hibernate.service;


import hibernate.dao.DepartmentDao;
import hibernate.dao.UserDao;
import hibernate.exception.AppException;
import hibernate.model.Department;
import hibernate.model.User;
import org.apache.log4j.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 10.02.18.
 */
public class MainServiceImpl implements MainService {

    private final Logger logger = Logger.getLogger(MainServiceImpl.class);

    private UserDao userDao;
    private DepartmentDao departmentDao;
    private EntityManagerFactory factory;

    public MainServiceImpl() {

        factory = Persistence.createEntityManagerFactory("hibernate-unit");
        userDao = new UserDao(factory);
        departmentDao = new DepartmentDao(factory);

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

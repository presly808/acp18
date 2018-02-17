package hibernate.service;

import hibernate.dao.Dao;
import hibernate.dao.DaoImpl;
import hibernate.exception.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 10.02.18.
 */
public class MainServiceImpl implements MainService {

    private Dao dao;

    public MainServiceImpl() {
        this.dao = new DaoImpl();
    }

    @Override
    public User register(User user) throws AppException {
        dao.create(user);
        return user;
    }

    @Override
    public Department addDepartment(Department department) throws AppException {

        dao.create(department);

        return department;
    }

    @Override
    public City addCity(City city) throws AppException {
        dao.create(city);
        return city;
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

        List<User> users = dao.findAll();
        
//
//        for (User :
//             ) {
//
//        }

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

    @Override
    public List<User> findAll() {
        return this.dao.findAll();
    }

}

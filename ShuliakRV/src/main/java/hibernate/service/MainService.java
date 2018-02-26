package hibernate.service;

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
public interface MainService {

    User register(User user) throws AppException;

    Department addDepartment(Department department) throws AppException;

    City addCity(City city) throws AppException;

    User update(User user) throws AppException;

    User remove(User user) throws AppException;

    List<User> findAll() throws AppException;

    Map<Department, List<User>> getUsersGroupByDepartment() throws AppException;

    Map<Department, Double> getAvgSalaryGroupByDepartment() throws AppException;

    Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() throws AppException;

    List<User> findByName(String name) throws AppException;

    List<User> findInRange(double minSal, double maxSal) throws AppException;

    List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppException;


}

package hibernate.service;

import hibernate.exception.AppExceptionExclude;
import hibernate.model.Department;
import hibernate.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 10.02.18.
 */
public interface MainService {

    User register(User user) throws AppExceptionExclude;

    Department addDepartment(Department department) throws AppExceptionExclude;

    User update(User user) throws AppExceptionExclude;

    User remove(User user) throws AppExceptionExclude;

    Map<Department, List<User>> getUsersGroupByDepartment() throws AppExceptionExclude;

    Map<Department, Double> getAvgSalaryGroupByDepartment() throws AppExceptionExclude;

    Map<User, List<User>> getUsersGroupByManagersAndOrderedThatLiveInKiev() throws AppExceptionExclude;

    List<User> findByName(String name) throws AppExceptionExclude;

    List<User> findInRange(double minSal, double maxSal) throws AppExceptionExclude;

    List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppExceptionExclude;


}

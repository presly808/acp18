package hibernate.dao;

import hibernate.exception.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface DaoUser extends Dao<User, Integer> {

    Map<Department, Integer> getAvgSalaryGroupByDepartment() throws AppException;
    Map<User, List<User>> getUsersByCityGroupByManagersAndOrdered(City city) throws AppException;
    List<User> findByName(String name) throws AppException;
    List<User> findInSalaryRange(double minSal, double maxSal) throws AppException;
    List<User> findByDateRange(LocalDateTime start, LocalDateTime end) throws AppException;

}

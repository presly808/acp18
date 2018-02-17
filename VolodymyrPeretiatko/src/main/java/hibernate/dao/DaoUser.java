package hibernate.dao;

import hibernate.exception.AppException;
import hibernate.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface DaoUser extends Dao<User, Integer> {

    List getAvgSalaryGroupByDepartment() throws AppException;
    List getUsersByCityAndOrdered(String city) throws AppException;
    List<User> findByName(String name) throws AppException;
    List<User> findInSalaryRange(double minSal, double maxSal) throws AppException;
    List<User> findByDateRange(LocalDateTime start, LocalDateTime end) throws AppException;

}

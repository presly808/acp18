package hibernate.dao;

import hibernate.exception.AppExceptionExclude;
import hibernate.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface DaoUser extends Dao<User, Integer> {

    List getAvgSalaryGroupByDepartment() throws AppExceptionExclude;
    List getUsersByCityAndOrdered(String city) throws AppExceptionExclude;
    List<User> findByName(String name) throws AppExceptionExclude;
    List<User> findInSalaryRange(double minSal, double maxSal) throws AppExceptionExclude;
    List<User> findByDateRange(LocalDateTime start, LocalDateTime end) throws AppExceptionExclude;

}

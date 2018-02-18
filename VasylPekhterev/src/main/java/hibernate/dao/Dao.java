package hibernate.dao;

import hibernate.model.Department;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 10.02.18.
 */
public interface Dao<T,ID> {

    T create(T entity);
    List<T> findAll();
    List<T> findAll(int offset, int length);
    T find(ID id);
    T remove(ID id);
    T update(T entity);
    void removeAll();
    List<T> findByName(String name);

    List<T> findInRange(double minSal, double maxSal);

    List<T> findByDate(LocalDateTime start, LocalDateTime end);

    Map<Department,Double> getAvgSalaryGroupByDepartment();
}

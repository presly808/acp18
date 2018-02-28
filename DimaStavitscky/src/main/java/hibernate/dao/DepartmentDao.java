package hibernate.dao;

import hibernate.exception.exclude.AppException;
import hibernate.model.Department;
import hibernate.model.User;

import java.util.List;
import java.util.Map;

public interface DepartmentDao extends Dao<Department, Integer>{

    Map<Department, List<User>> getUsersGroupByDepartment() throws AppException;
}

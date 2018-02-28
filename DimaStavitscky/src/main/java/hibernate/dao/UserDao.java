package hibernate.dao;

import hibernate.exception.exclude.AppException;
import hibernate.model.Base;
import hibernate.model.Department;
import hibernate.model.User;
import javassist.NotFoundException;

import java.util.List;
import java.util.Map;

public interface UserDao extends Dao<User, Integer> {

    Map<User, List<User>> getUsersGroupByManager() throws AppException;

    User getUserByLoginAndPass(String login, String pass) throws IllegalArgumentException;

    Map<User, List<User>> getUsersGroupByManagerAndOrderByCity(String city)
        throws AppException;
}

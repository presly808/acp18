package hibernate.dao;

import hibernate.exception.exclude.AppException;
import hibernate.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface UserDao extends Dao<User, Integer> {

    Map<User, List<User>> getUsersGroupByManager() throws AppException;

    User getUserByLoginAndPass(String login, String pass) throws IllegalArgumentException;

    Map<User, List<User>> getUsersGroupByManagerAndOrderByCity(String city)
        throws AppException;

    List<User> findByName (String name) throws AppException;

    List<User> findByDate(LocalDateTime start, LocalDateTime end) throws AppException;
}

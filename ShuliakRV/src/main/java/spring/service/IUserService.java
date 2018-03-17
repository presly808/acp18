package spring.service;

import spring.exception.AppException;
import spring.model.User;

import java.util.List;

/**
 * Created by serhii on 17.02.18.
 */
public interface IUserService {

    User save(User user) throws AppException;

    User delete(int id) throws AppException;

    User findById(int id) throws AppException;

    User findByNameAndPassword(String name, String password)
            throws AppException;

    List<User> findAll() throws AppException;

}

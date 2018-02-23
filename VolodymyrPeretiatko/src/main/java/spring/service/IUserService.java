package spring.service;

import spring.exeption.AppUserException;
import spring.model.User;

/**
 * Created by serhii on 17.02.18.
 */
public interface IUserService {

    User save(User user) throws AppUserException;
    User delete(int id) throws AppUserException;
    User findById(int id) throws AppUserException;

}

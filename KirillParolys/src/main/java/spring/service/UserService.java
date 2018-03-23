package spring.service;

import spring.exception.NoUserFoundException;
import spring.model.User;

/**
 * Created by serhii on 17.02.18
 */
public interface UserService {

    User login(User user) throws NoUserFoundException;

    User delete(User user) throws NoUserFoundException;

}

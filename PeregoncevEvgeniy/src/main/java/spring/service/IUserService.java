package spring.service;

import spring.model.User;

/**
 * Created by serhii on 17.02.18.
 */
public interface IUserService {

    void save(User user);

    void deleteById(int id);

    User findById(int id);

}

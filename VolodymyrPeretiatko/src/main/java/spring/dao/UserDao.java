package spring.dao;

import spring.model.User;

/**
 * Created by serhii on 17.02.18.
 */
public interface UserDao {

    User save(User user);
    User delete(int id);
    User findById(int id);

}

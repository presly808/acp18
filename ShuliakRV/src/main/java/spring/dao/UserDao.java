package spring.dao;

import spring.model.User;

import java.util.List;

/**
 * Created by serhii on 17.02.18.
 */
public interface UserDao {

    List<User> findAll();
    User find(int id);
    User remove(int id);
    User update(User entity);

}

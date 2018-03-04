package spring.dao;

import spring.model.User;

import java.util.List;

/**
 * Created by serhii on 17.02.18.
 */
public interface  UserDao {
    User  create (User user);
    User delete (int id);
    User find(int id);
    List<User> findAll();
}

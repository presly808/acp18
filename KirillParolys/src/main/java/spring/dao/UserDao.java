package spring.dao;

import spring.exception.NoUserFoundException;
import spring.model.User;

public interface UserDao {

    User findById(int id) throws NoUserFoundException;

    User findByName(String name) throws NoUserFoundException;

    String getUserInfo(User user) throws NoUserFoundException;

}

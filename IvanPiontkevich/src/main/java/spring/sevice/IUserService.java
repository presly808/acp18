package spring.sevice;

import spring.model.User;

import java.util.List;

/**
 * Created by serhii on 17.02.18.
 */
public interface IUserService {


    User save(User user);
    User delete(int id);
    User findById(int id);
    List<User> findAll();

}

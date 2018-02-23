package spring.DAO;

import spring.model.User;


/**
 * Created by serhii on 17.02.18.
 */
public interface IUserDao {

    void addUser(User user);

    User findById(int id);

    void delete(int id);

}

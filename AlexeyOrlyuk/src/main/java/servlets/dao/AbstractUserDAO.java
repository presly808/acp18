package servlets.dao;

import servlets.model.User;

import java.util.List;

/**
 * Created by alex323glo on 24.02.18.
 */
public abstract class AbstractUserDAO {

    public abstract User create(User user);

    public abstract User findById(int id);

    public abstract User findByEmail(String email);

    public abstract User update(User user);

    public abstract User delete(int id);

    public abstract List<User> listAll();

    public abstract List<User> listAllWithLimit(int offset, int count);

    public abstract List<User> removeAllUsers();
}

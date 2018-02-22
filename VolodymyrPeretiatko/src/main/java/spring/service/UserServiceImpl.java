package spring.service;

import spring.dao.UserDao;
import spring.model.User;

public class UserServiceImpl implements IUserService {

    private UserDao dao;

    public UserServiceImpl() {
    }

    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public User save(User user) {
        return dao.save(user);
    }

    @Override
    public User delete(int id) {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }
}

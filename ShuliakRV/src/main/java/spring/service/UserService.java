package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dao.UserDao;
import spring.model.User;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao Dao;

    @Override
    public User save(User user) {
        return null;
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

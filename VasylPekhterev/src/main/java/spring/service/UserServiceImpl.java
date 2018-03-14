package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.dao.IUserDao;
import spring.model.User;

@Component
public class UserServiceImpl implements  IUserService{

    @Autowired
    private IUserDao userDao;

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public User delete(int id) {
        return userDao.delete(id);
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }
}

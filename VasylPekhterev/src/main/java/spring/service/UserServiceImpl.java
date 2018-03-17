package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.dao.IUserDao;
import spring.model.User;

@Component
public class UserServiceImpl implements  IUserService{

    @Autowired
    private IUserDao userDao;

    @Override
    @Transactional
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    @Transactional
    public User delete(int id) {
        return userDao.delete(id);
    }

    @Override
    @Transactional
    public User findById(int id) {
        return userDao.findById(id);
    }
}

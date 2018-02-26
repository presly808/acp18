package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    public UserServiceImpl() {
    }

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
        return userDao.find(id);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }
}

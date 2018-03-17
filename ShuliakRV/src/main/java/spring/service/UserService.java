package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dao.UserDao;
import spring.exception.AppException;
import spring.model.User;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User save(User user) throws AppException {

        if (user == null) {
            throw new AppException("User is NULL!");
        }

        User entity = userDao.update(user);

        if (entity == null) {
            throw new AppException("User wasn't registered!");
        }

        return entity;
    }

    @Override
    public User delete(int id) throws AppException {

        if (id == 0) {
            throw new AppException("User isn't exist!");
        }

        User entity = userDao.remove(id);

        if (entity == null) {
            throw new AppException("User wasn't removed!");
        }

        return entity;
    }

    @Override
    public User findById(int id) throws AppException {

        if (id == 0) {
            throw new AppException("User isn't exist!");
        }

        User entity = userDao.find(id);

        if (entity == null) {
            throw new AppException("User wasn't found!");
        }

        return entity;
    }

    @Override
    public User findByNameAndPassword(String name, String password)
            throws AppException {

        if (name == null || password == null) {
            throw new AppException("User isn't exist!");
        }

        try {
            User entity = userDao.findByNameAndPassword(name, password);
            return entity;
        }
        catch (Exception e) {
            throw new AppException("User wasn't found!");
        }

    }

    @Override
    public List<User> findAll() throws AppException {

        List<User> list = userDao.findAll();

        if (list == null) {
            throw new AppException("Users wasn't found!");
        }

        return list;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}

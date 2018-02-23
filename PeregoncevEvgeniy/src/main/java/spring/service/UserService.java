package spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dao.IUserDao;
import spring.dao.UserDao;

import spring.model.User;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public UserService() {
    }

    public void setDao(UserDao Dao) {
        dao = Dao;
    }

    public IUserDao getDao() {
        return dao;
    }

    @Override
    public void save(User user) {
        dao.addUser(user);
    }

    @Override
    public void deleteById(int id) {
        dao.delete(id);
    }

    @Override
    public User findById(int id) {
        return dao.findById(id);
    }


}

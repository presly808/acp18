package spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.DAO.IUserDao;
import spring.DAO.UserDao;

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
    public User save(User user) {
        dao.register(user);
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

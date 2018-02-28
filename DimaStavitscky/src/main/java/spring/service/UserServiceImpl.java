package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import spring.dao.UserDao;
import spring.model.User;

@Component(value = "userService")
public class UserServiceImpl implements IUserService {


    private UserDao dao;

    public UserServiceImpl() {
    }

   /* public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }*/

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public User save(User user) {
        return user;
    }

    @Override
    public User delete(int id) {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }


    public UserDao getDao() {
        return dao;
    }

    public void setDao(UserDao dao) {
        this.dao = dao;
    }
}

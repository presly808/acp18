package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spring.dao.UserDao;
import spring.exception.NoUserFoundException;
import spring.model.User;

// imp of UserService

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao dao;

    public UserDao getDao() {
        return dao;
    }

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @MyAnnotation
    @Override
    public User login(User user) throws NoUserFoundException {
        if (dao.findById(user.getId()) == user) {
            System.out.println("User " + user.toString() + " logged in");
            return user;
        } else throw new NoUserFoundException("No such user found");
    }

    @MyAnnotation
    @Override
    public User delete(User user) throws NoUserFoundException {
        System.out.println("Deleting user " + user.toString() + "...");
        return dao.findById(user.getId());
    }
}

package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import spring.dao.UserDao;
import spring.exeption.AppUserException;
import spring.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao dao;

    public UserServiceImpl() {
    }

    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public User save(User user) throws AppUserException {

        user = dao.save(user);

        if(user == null){
            throw new AppUserException("student didn't save");
        }

        return user;
    }

    @Override
    public User delete(int id) throws AppUserException {

        User user = dao.delete(id);

        if(user == null){
            throw new AppUserException("student didn't remove");
        }

        return user;
    }

    @Override
    public User findById(int id) throws AppUserException {

        User user = dao.delete(id);

        if(user == null){
            throw new AppUserException("student didn't remove");
        }

        return user;
    }
}

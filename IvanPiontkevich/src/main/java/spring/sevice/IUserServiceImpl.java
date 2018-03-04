package spring.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.dao.UserDao;
import spring.model.User;

import java.util.List;


@Component(value = "userService")
public class IUserServiceImpl implements IUserService{

    @Autowired
    private UserDao dao;


    @Override
    public User save(User user) {
        return dao.create(user);
    }

    @Override
    public User delete(int id) {
        return dao.delete(id);
    }

    @Override
    public User findById(int id) {
        return dao.find(id);
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

}

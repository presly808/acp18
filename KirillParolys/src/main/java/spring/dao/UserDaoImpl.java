package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.exception.NoUserFoundException;
import spring.model.User;

@Component
public class UserDaoImpl implements UserDao {

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    User user;

    @Autowired
    public UserDaoImpl(User user) {
        this.user = user;
    }

    @Override
    public User findById(int id) throws NoUserFoundException {
        if (user.getId() == id) {
            return user;
        } else throw new NoUserFoundException("No user with such id");
    }


    @Override
    public User findByName(String name) throws NoUserFoundException {
        if (user.getName().equals(name)) {
            return user;
        } else throw new NoUserFoundException("No user with such name found");
    }

    @Override
    public String getUserInfo(User user) throws NoUserFoundException {
        if (findById(user.getId()) == user) {
            return user.toString();
        } else throw new NoUserFoundException("No such user found");
    }
}

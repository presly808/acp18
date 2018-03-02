package servlet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import servlet.dao.Dao;
import servlet.exception.ServletAppException;
import servlet.model.ServUser;
import servlet.validator.Validator;

import java.util.List;

@Service(value = "service")
public class UserServiceImpl implements UserService {

    @Autowired
    private Dao<ServUser> userDao;
    @Autowired
    private Validator validator;


    @Override
    @Transactional
    public ServUser login(String email, String pass) throws ServletAppException {
        if (!validator.checkEmail(email) || !validator.checkPass(pass)){
            throw new ServletAppException("invalid email or pass");
        }
        ServUser find = userDao.findByEmail(email);
        if (find != null && find.getPass().equals(pass)) {
            return find;
        }
        throw new ServletAppException("wrong email or pass, don`t have servUser with this parameters");
    }

    @Override
    @Transactional
    public ServUser addUser(ServUser servUser) throws ServletAppException {
        if (servUser !=null && !validator.checkUser(servUser)) throw new ServletAppException("Invalid email or pass");
        return userDao.save(servUser);
    }

    @Override
    @Transactional
    public List<ServUser> allUsers() {
        return userDao.findAll();
    }
}

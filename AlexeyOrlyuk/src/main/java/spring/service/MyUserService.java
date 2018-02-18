package spring.service;

import spring.dao.IMyUserDao;
import spring.exception.AppException;
import spring.model.MyUser;

/**
 * Created by alex323glo on 18.02.18.
 */
public class MyUserService implements IMyUserService {

    private IMyUserDao myUserDao;

    public IMyUserDao getMyUserDao() {
        return myUserDao;
    }

    public void setMyUserDao(IMyUserDao myUserDao) {
        this.myUserDao = myUserDao;
    }

    @Override
    public MyUser save(MyUser user) throws AppException {

        // validate user

        MyUser savedUser = myUserDao.create(user);

        if (savedUser == null) {
            throw new AppException("can't create save user");
        }

        return savedUser;
    }

    @Override
    public MyUser delete(int id) throws AppException {

        // validate id

        MyUser removedUser = myUserDao.remove(id);

        if (removedUser == null) {
            throw new AppException("can't delete user");
        }

        return removedUser;
    }

    @Override
    public MyUser findById(int id) throws AppException {

        // validate id

        MyUser foundUser = myUserDao.find(id);

        if (foundUser == null) {
            throw new AppException("can't find user");
        }

        return foundUser;
    }
}

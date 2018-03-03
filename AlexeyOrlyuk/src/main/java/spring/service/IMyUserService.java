package spring.service;

import spring.exception.AppException;
import spring.model.MyUser;

/**
 * Created by serhii on 17.02.18.
 */
public interface IMyUserService {

    MyUser save(MyUser user) throws AppException;

    MyUser delete(int id) throws AppException;

    MyUser findById(int id) throws AppException;

}

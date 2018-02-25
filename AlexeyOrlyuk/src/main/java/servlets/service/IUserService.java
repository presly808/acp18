package servlets.service;

import servlets.exception.AppException;
import servlets.model.User;

import java.util.List;

public interface IUserService {

    User register(String email, String password) throws AppException;

    User login(String email, String password) throws AppException;

    List<User> listAllRegisteredUsers() throws AppException;

}

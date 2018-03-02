package servlet.service;

import servlet.exception.ServletAppException;
import servlet.model.ServUser;

import java.util.List;

public interface UserService {

    ServUser login(String email, String pass) throws ServletAppException;
    ServUser addUser(ServUser servUser) throws ServletAppException;
    List<ServUser> allUsers();
}

package servlets.service;

import servlets.exception.ServletAppException;
import servlets.model.ServUser;

import java.util.List;

public interface UserService {

    ServUser login(String email, String pass) throws ServletAppException;
    ServUser addUser(ServUser servUser) throws ServletAppException;
    List<ServUser> allUsers();
}

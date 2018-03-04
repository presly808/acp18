package servlets.service;

import servlets.exception.AppException;
import servlets.model.User;

import java.util.List;

/**
 * User Service main interface.
 * Defines User's general business logic.
 *
 * @author alex323glo
 * @version 1.0
 */
public interface IUserService {

    /**
     * Registers new User to system.
     * Both, email and password, must pass validation (servlets.util.Validator class)!
     *
     * @param email new (unique) User's email.
     * @param password new User's password.
     * @return registered User instance, if operation was successful.
     * @throws AppException if operation wasn't successful.
     *
     * @see servlets.util.Validator
     */
    User register(String email, String password) throws AppException;

    /**
     * Authorizes registered User to system.
     * Both, email and password, must pass validation (servlets.util.Validator class)!
     *
     * @param email registered (existent) User's email.
     * @param password correct User's password.
     * @return authorized (registered) User instance, if operation was successful.
     * @throws AppException if operation wasn't successful.
     *
     * @see servlets.util.Validator
     */
    User login(String email, String password) throws AppException;

    /**
     * Shows all registered Users.
     *
     * @return List of all registered Users, if operation was successful.
     * @throws AppException if operation wasn't successful.
     */
    List<User> listAllRegisteredUsers() throws AppException;

}

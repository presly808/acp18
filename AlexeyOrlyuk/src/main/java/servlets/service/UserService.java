package servlets.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import servlets.dao.UserDAO;
import servlets.exception.AppException;
import servlets.exception.AuthorizationException;
import servlets.exception.RegistrationException;
import servlets.model.User;
import servlets.util.Validator;

import java.util.List;

import static servlets.util.Validator.*;

/**
 * Implementation of User Service main interface.
 * Contains realisation of defined (in interface) User's general business logic.
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see IUserService
 */
@Component
public class UserService implements IUserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    public static final int MIN_PASSWORD_LENGTH = 8;

    @Autowired
    private UserDAO userDAO;

    /**
     * Registers new User to system.
     * Both, email and password, must pass validation (servlets.util.Validator class)!
     *
     * @param email    new (unique) User's email.
     * @param password new User's password.
     * @return registered User instance, if operation was successful.
     * @throws AppException if operation wasn't successful.
     * @see Validator
     */
    @Transactional
    @Override
    public User register(String email, String password) throws AppException {
        LOGGER.info("registration: trying to register new User (" +
                "email: \"" + email + "\", " +
                "password: \"" + password + "\");");

        if (!emailIsValid(email) || !passwordIsValid(password, MIN_PASSWORD_LENGTH)) {
            LOGGER.error("registration failed: validation wasn't successful");
            throw new RegistrationException("email or password is not valid");
        }
        LOGGER.info("registration: validation was successful");

        if (userDAO.findByEmail(email) != null) {
            LOGGER.error("registration failed: try to register User with  duplicate email");
            throw new RegistrationException("such email (\"" + email + "\") is already in use");
        }

        User newUser = userDAO.create(new User(email, password));

        if (newUser == null) {
            LOGGER.error("registration failed: can't save user to DB");
            throw new RegistrationException("User wasn't saved to DB");
        }
        LOGGER.info("registration: new User was successfully saved to DB");

        return newUser;
    }

    /**
     * Authorizes registered User to system.
     * Both, email and password, must pass validation (servlets.util.Validator class)!
     *
     * @param email    registered (existent) User's email.
     * @param password correct User's password.
     * @return authorized (registered) User instance, if operation was successful.
     * @throws AppException if operation wasn't successful.
     * @see Validator
     */
    @Transactional
    @Override
    public User login(String email, String password) throws AppException {
        LOGGER.info("authorization: trying to authorize User (" +
                "email: \"" + email + "\", " +
                "password: \"" + password + "\");");

        if (!emailIsValid(email) || !passwordIsValid(password, MIN_PASSWORD_LENGTH)) {
            LOGGER.error("authorization failed: validation wasn't successful");
            throw new AuthorizationException("email or password is not valid");
        }
        LOGGER.info("authorization: validation was successful");

        User existentUser = userDAO.findByEmail(email);

        if (existentUser == null) {
            LOGGER.error("authorization failed: can't find User by email (\"" + email + "\") in DB");
            throw new AuthorizationException("User wasn't saved to DB");
        }

        if (!password.equals(existentUser.getPassword())) {
            LOGGER.error("authorization failed: wrong password was sent");
            throw new AuthorizationException("proposed password is not equal to registered one");
        }
        LOGGER.info("authorization: existent User (with proposed email) has equal password to proposed");

        return existentUser;
    }

    /**
     * Shows all registered Users.
     *
     * @return List of all registered Users, if operation was successful.
     * @throws AppException if operation wasn't successful.
     */
    @Transactional
    @Override
    public List<User> listAllRegisteredUsers() throws AppException {
        LOGGER.info("listing_all_users: trying to List all registered Users");

        List<User> userList = userDAO.listAll();

        if (userList == null) {
            LOGGER.error("listing_all_users failed: can't get List of registered Users from DB");
            throw new AppException("can't get all Users from DB");
        }
        LOGGER.info("listing_all_users: successfully got List of all registered Users from DB");

        return userList;
    }
}

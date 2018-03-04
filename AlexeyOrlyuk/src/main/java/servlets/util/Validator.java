package servlets.util;

import org.apache.log4j.Logger;

/**
 * Validation Utils' Container class.
 *
 * @author alex323glo
 * @version 1.0
 */
public class Validator {

    private static final Logger LOGGER = Logger.getLogger(Validator.class);

    /**
     * Validates email address String.
     *
     * To pass validation, email String must:
     *  - be NotNull;
     *  - contain '@' character.
     *
     * @param email tested email String.
     * @return true, if validation was passed, and false, if it wasn't.
     */
    public static boolean emailIsValid(String email) {
        if (email == null) {
            LOGGER.error("email validation failed: email is null");
            return false;
        } else if (!email.contains("@")) {
            LOGGER.error("email validation failed: email doesn't contain '@' charcter (email: \"" + email + "\")");
            return false;
        } else {
            LOGGER.info("email validation passed (email: \"" + email + "\")");
            return true;
        }
    }

    /**
     * Validates password String.
     *
     * To pass validation, password String must:
     *  - be NotNull;
     *  - be not shorter then minCharacters param value.
     *
     * @param password tested password String.
     * @param minCharacters required minimum for number of characters in password String.
     * @return true, if validation was passed, and false, if it wasn't.
     */
    public static boolean passwordIsValid(String password, int minCharacters) {
        if (password == null) {
            LOGGER.error("password validation failed: password is null");
            return false;
        } else if (password.length() < minCharacters) {
            LOGGER.error("password validation failed: password is too short (password: \"" + password + "\")");
            return false;
        } else {
            LOGGER.info("password validation passed (password: \"" + password + "\")");
            return true;
        }
    }

}

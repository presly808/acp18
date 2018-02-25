package servlets.util;

import org.apache.log4j.Logger;

/**
 * Created by alex323glo on 24.02.18.
 */
public class Validator {

    private static final Logger LOGGER = Logger.getLogger(Validator.class);

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

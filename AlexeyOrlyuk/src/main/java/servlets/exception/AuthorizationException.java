package servlets.exception;

/**
 * Created by alex323glo on 24.02.18.
 */
public class AuthorizationException extends AppException {

    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }
}

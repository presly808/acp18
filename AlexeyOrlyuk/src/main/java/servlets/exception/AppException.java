package servlets.exception;

/**
 * Created by alex323glo on 24.02.18.
 */
public class AppException extends Exception {

    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }
}

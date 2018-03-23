package spring.exception;

// custom webapp.exception
public class NoUserFoundException extends Exception {

    public NoUserFoundException(String message) {
        super(message);
    }

}

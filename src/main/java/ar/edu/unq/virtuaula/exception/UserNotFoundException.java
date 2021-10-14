package ar.edu.unq.virtuaula.exception;

public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = -9115320169312381049L;

    public UserNotFoundException(String message) {
        super(message);
    }
}

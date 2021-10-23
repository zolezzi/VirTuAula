package ar.edu.unq.virtuaula.exception;

public class AccountNotFoundException extends Exception {

    private static final long serialVersionUID = -8142466419243923155L;

    public AccountNotFoundException(String message) {
        super(message);
    }
}

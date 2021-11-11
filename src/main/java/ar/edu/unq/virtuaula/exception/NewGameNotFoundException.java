package ar.edu.unq.virtuaula.exception;

public class NewGameNotFoundException extends Exception {

    private static final long serialVersionUID = 8922679902844603074L;

    public NewGameNotFoundException(String message) {
        super(message);
    }
}

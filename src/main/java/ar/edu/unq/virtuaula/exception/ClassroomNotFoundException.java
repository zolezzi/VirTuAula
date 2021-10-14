package ar.edu.unq.virtuaula.exception;

public class ClassroomNotFoundException extends Exception {

    private static final long serialVersionUID = 8922679902844603074L;

    public ClassroomNotFoundException(String message) {
        super(message);
    }
}

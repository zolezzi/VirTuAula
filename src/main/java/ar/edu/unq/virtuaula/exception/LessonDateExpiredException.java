package ar.edu.unq.virtuaula.exception;

public class LessonDateExpiredException extends Exception{

	private static final long serialVersionUID = 397298862499692919L;

    public LessonDateExpiredException(String message) {
        super(message);
    }
}

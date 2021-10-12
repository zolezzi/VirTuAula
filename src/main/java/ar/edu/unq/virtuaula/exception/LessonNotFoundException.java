package ar.edu.unq.virtuaula.exception;

public class LessonNotFoundException extends Exception{

	private static final long serialVersionUID = 4545309751760038285L;

	public LessonNotFoundException(String message) {
		super(message);
	}
}

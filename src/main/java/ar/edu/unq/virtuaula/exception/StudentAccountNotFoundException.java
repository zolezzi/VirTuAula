package ar.edu.unq.virtuaula.exception;

public class StudentAccountNotFoundException extends Exception{

	private static final long serialVersionUID = -8365532472407251270L;

	public StudentAccountNotFoundException(String message, Exception e) {
		super(message, e);
	}
	
}

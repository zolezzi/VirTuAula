package ar.edu.unq.virtuaula.exception;

public class TeacherNotFoundException extends Exception{

	private static final long serialVersionUID = 7013076244230092051L;

	public TeacherNotFoundException(String message, Exception e) {
		super(message, e);
	}
	
}

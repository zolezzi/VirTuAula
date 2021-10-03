package ar.edu.unq.virtuaula.exception;

public class ExceptionTeacherNotFound extends Exception{

	private static final long serialVersionUID = 7013076244230092051L;

	public ExceptionTeacherNotFound(String message, Exception e) {
		super(message, e);
	}
	
}

package ar.edu.unq.virtuaula.exception;

public class PlayerMissionNotFoundException extends Exception{

	private static final long serialVersionUID = 4150373187765069938L;

	public PlayerMissionNotFoundException(String message) {
        super(message);
    }
}

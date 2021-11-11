package ar.edu.unq.virtuaula.exception;

public class CampaignDateExpiredException extends Exception{

	private static final long serialVersionUID = 397298862499692919L;

    public CampaignDateExpiredException(String message) {
        super(message);
    }
}

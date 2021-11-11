package ar.edu.unq.virtuaula.exception;

public class CampaignNotFoundException extends Exception {

    private static final long serialVersionUID = 4545309751760038285L;

    public CampaignNotFoundException(String message) {
        super(message);
    }
}

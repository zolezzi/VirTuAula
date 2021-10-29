package ar.edu.unq.virtuaula.message;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseMessage implements Serializable {

	private static final long serialVersionUID = 5633833861385631421L;
	private String message;
	
	public ResponseMessage(String message) {
		this.message = message;
	} 
}

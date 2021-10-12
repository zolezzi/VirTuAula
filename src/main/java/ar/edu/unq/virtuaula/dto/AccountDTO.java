package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AccountDTO implements Serializable{
	
	private static final long serialVersionUID = -6757812414340431178L;
	private Long accountId;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private AccountTypeDTO accountType;

}

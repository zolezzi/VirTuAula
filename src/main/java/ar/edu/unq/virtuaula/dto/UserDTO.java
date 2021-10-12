package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDTO implements Serializable{
	
	private static final long serialVersionUID = 38253469512896303L;
	private Long id;
    private String username;
    private String password;
    private String email;
    private String repeatPassword;
    private String token;
    private AccountDTO account;
}

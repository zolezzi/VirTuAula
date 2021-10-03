package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PrivilegeDTO implements Serializable{

	private static final long serialVersionUID = 25317735297962704L;
	private Long id;
    private String name;
}

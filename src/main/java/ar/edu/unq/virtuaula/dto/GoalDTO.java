package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class GoalDTO implements Serializable{

	private static final long serialVersionUID = -5471201444972726394L;
	private Long id;
	private String name;
	private String description;
}

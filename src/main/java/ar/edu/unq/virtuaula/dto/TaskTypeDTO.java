package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class TaskTypeDTO implements Serializable {

	private static final long serialVersionUID = 6338082848177720917L;
	private Long id;
    private String name;

}

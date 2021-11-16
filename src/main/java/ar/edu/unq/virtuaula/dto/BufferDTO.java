package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class BufferDTO implements Serializable{
	
	private static final long serialVersionUID = -1927081597083036480L;
    private Long id;
    private String name;
    private String description;
}

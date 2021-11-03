package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LevelDTO implements Serializable {
	
	private static final long serialVersionUID = -7495454168604963198L;
	private Long id;
    private String name;
    private Integer numberLevel;
    private String description;
    private String imagePath;
}

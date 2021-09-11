package ar.edu.unq.virtuaula.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Classroom implements Serializable{

	private static final long serialVersionUID = 768575911005782319L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	public Classroom() {}
	
	public Classroom(String name) {
		this.name = name;
	}

}

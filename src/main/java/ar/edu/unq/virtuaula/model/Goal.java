package ar.edu.unq.virtuaula.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Goal implements Serializable {
	
	private static final long serialVersionUID = 9041215411594157972L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	private String name;
	private String description;
	
    @ManyToOne
    private Level level;

}

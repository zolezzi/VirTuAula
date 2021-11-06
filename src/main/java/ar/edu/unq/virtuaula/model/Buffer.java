package ar.edu.unq.virtuaula.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "buffer_type")
public abstract class Buffer implements Serializable {
	
	private static final long serialVersionUID = 3545416115402926606L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String description;

	private String operator;
    
    public abstract boolean isExperience();
    
    public abstract boolean isLife();
    
    public abstract void apply(StudentAccount student, Double value);
}

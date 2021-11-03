package ar.edu.unq.virtuaula.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Level implements Serializable {
	
	private static final long serialVersionUID = -1353675160364636848L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private Integer numberLevel;
    
    private String nameNextLevel;
    
    private String description;
    
    private Double minValue;
    
    private Double maxValue;
    
    private String imagePath;

}

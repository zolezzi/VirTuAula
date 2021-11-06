package ar.edu.unq.virtuaula.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    @JsonIgnoreProperties("level")
    private List<Buffer> buffers = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    @JsonIgnoreProperties("level")
    private List<Goal> goals = new ArrayList<>();

}

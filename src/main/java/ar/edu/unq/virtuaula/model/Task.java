package ar.edu.unq.virtuaula.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String statement;
	
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    @JsonIgnoreProperties("tasks")
    private Lesson lesson;

	public Task() { }
    
	public Task(String statement) {
		this.statement = statement;
	}

}

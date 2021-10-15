package ar.edu.unq.virtuaula.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class StudentTask implements Serializable{

	private static final long serialVersionUID = -5949337932012342390L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private Long answer;
	
    @Enumerated(EnumType.STRING)
    private State state;
	
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
	private Lesson lesson;
	
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonIgnoreProperties("resultsTasks")
    private StudentAccount studentAccount;


    public void complete() {
        this.state = State.COMPLETED;
    }

    public void uncomplete() {
        this.state = State.UNCOMPLETED;
    }
}

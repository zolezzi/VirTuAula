package ar.edu.unq.virtuaula.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
	
    @OneToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
	private Lesson lesson;
	
    @OneToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;
    
    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private StudentAccount studentAccount;

    private String story;

    public void complete() {
        this.state = State.COMPLETED;
    }

    public void uncomplete() {
        this.state = State.UNCOMPLETED;
    }
}
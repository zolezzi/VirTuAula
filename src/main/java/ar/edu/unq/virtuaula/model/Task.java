package ar.edu.unq.virtuaula.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Task implements Serializable {

    private static final long serialVersionUID = 768575911005782319L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statement;

    private int score;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    @JsonIgnoreProperties("tasks")
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    private State state;

    private Long answer;

    private Long correctAnswer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    @JsonIgnoreProperties("task")
    private List<OptionTask> options = new ArrayList<>();

    public void complete() {
        this.state = State.COMPLETED;
    }

    public void uncomplete() {
        this.state = State.UNCOMPLETED;
    }

    public void addOption(OptionTask option) {
        this.options.add(option);
    }

}

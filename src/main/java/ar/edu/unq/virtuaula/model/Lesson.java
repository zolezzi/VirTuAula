package ar.edu.unq.virtuaula.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Lesson implements Serializable {

    private static final long serialVersionUID = 768575911005782319L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int maxNote;
    
    private Date deliveryDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    @JsonIgnoreProperties("lesson")
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public int progress(List<StudentTask> tasks) {
        int completed = (int) tasks.stream().filter(task -> State.COMPLETED.equals(task.getState())).count();
        return tasks.isEmpty() ? 0 : completed * 100 / tasks.size();
    }

    public double qualification(List<StudentTask> tasks) {
        double completed = (double) tasks.stream()
                .filter(studentTask -> State.COMPLETED.equals(studentTask.getState()) && studentTask.getTask().getCorrectAnswer().equals(studentTask.getAnswer()))
                .mapToDouble(studentTask -> studentTask.getTask().getScore())
                .sum();
        return tasks.isEmpty() ? 0 : validate(completed);
    }

    private double validate(double completed) {
        Assert.isTrue(this.maxNote >= completed, "The qualification exceed the max note.");
        return completed;
    }
}

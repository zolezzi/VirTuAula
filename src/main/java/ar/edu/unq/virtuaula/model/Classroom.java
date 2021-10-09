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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Classroom implements Serializable {

    private static final long serialVersionUID = 768575911005782319L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    @JsonIgnoreProperties("classroom")
    private List<Lesson> lessons = new ArrayList<>();
    
    @ManyToMany(mappedBy = "classrooms")
    private List<Account> accounts;

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public int progress() {
        int completed = this.lessons.stream().mapToInt(lesson -> lesson.progress()).sum();
        return this.lessons.isEmpty() ? 0 : completed / this.lessons.size();
    }
}

package ar.edu.unq.virtuaula.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;

public interface TaskRepository  extends JpaRepository<Task, Long> {

	List<Task> findByLesson(Lesson lesson);
}

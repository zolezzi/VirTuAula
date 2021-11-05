package ar.edu.unq.virtuaula.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.repository.StudentTaskRepository;

@Component
public class CalculatedProgressUtil {

	@Autowired
	private StudentTaskRepository studentTaskRepository;
	
    public Integer getProgress(Classroom classroom, Long accountId) {
    	return calculateProgress(classroom, accountId);
    }

	private int calculateProgress(Classroom classroom, Long accountId) {
        int completed = classroom.getLessons().stream()
        		.mapToInt(lesson -> lesson.progress(studentTaskRepository.findByLessonAndStudent(lesson.getId(), accountId)))
        		.sum();
        return classroom.getLessons().isEmpty() ? 0 : completed / classroom.getLessons().size();
	}
}

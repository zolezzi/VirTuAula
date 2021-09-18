package ar.edu.unq.virtuaula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.repository.GuestClassromRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VirtuaulaApplicationTests {

    @Autowired
    protected GuestClassromRepository guestClassroomRepository;
    
    protected Classroom createClassroomWithName(String name) {
        Classroom classroom = new Classroom(name);
        return guestClassroomRepository.save(classroom);
    }
    
    protected Lesson createLessonWithName(String name, Classroom classroom) {
        Lesson lesson = new Lesson(name);
        classroom.addLesson(lesson);
        Classroom classroomPersist = guestClassroomRepository.save(classroom);
        return classroomPersist.getLessons().get(0);
    }
    
    protected Task createTask(Classroom classroom, Lesson lesson, String statement) {
    	Task task = new Task(statement);
    	lesson.addTask(task);
    	classroom.addLesson(lesson);
    	guestClassroomRepository.save(classroom);
    	return task;
    }
}

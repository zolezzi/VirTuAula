package ar.edu.unq.virtuaula;

import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.repository.GuestClassromRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

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
        Classroom classroomPersisted = guestClassroomRepository.save(classroom);
        return lesson;
    }
}

package ar.edu.unq.virtuaula.model;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.builder.ClassroomBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ClassroomTest extends VirtuaulaApplicationTests {

    @Test
    public void addLessonWithNoLessonsOnClassroomHasALesson() {
        Classroom classroom = ClassroomBuilder.classroomWithName("Matematicas").build();
        Lesson lesson = Mockito.mock(Lesson.class);
        int sizeBeforeAdd = classroom.getLessons().size();

        classroom.addLesson(lesson);

        assertEquals(sizeBeforeAdd + 1, classroom.getLessons().size());
    }

}

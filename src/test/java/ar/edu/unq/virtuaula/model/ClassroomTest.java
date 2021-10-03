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

    @Test
    public void progressWithALessonWithProgress100Has100OfProgress() {
        Classroom classroom = ClassroomBuilder.classroomWithName("Matematicas").build();
        Lesson lesson = Mockito.mock(Lesson.class);
        Mockito.when(lesson.progress()).thenReturn(100);

        classroom.addLesson(lesson);

        assertEquals(100, classroom.progress());
    }

    @Test
    public void progressWithALessonWithProgress100AndALessonWithProgress50Has75OfProgress() {
        Classroom classroom = ClassroomBuilder.classroomWithName("Matematicas").build();
        Lesson lesson1 = Mockito.mock(Lesson.class);
        Lesson lesson2 = Mockito.mock(Lesson.class);
        Mockito.when(lesson1.progress()).thenReturn(100);
        Mockito.when(lesson2.progress()).thenReturn(50);

        classroom.addLesson(lesson1);
        classroom.addLesson(lesson2);

        assertEquals(75, classroom.progress());
    }

    @Test
    public void progressWithoutLessonsHas0OfProgress() {
        Classroom classroom = ClassroomBuilder.classroomWithName("Matematicas").build();

        assertEquals(0, classroom.progress());
    }
}

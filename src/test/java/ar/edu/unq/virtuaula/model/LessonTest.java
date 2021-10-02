package ar.edu.unq.virtuaula.model;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.builder.LessonBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LessonTest extends VirtuaulaApplicationTests {

    @Test
    public void addTaskWithNoTaskOnALessonHasATask() {
        Lesson lesson = LessonBuilder.lessonWithName("Ecuaciones").build();
        Task task = Mockito.mock(Task.class);
        int sizeBeforeAdd = lesson.getTasks().size();

        lesson.addTask(task);

        assertEquals(sizeBeforeAdd + 1, lesson.getTasks().size());
    }

    @Test
    public void progressWithOneTaskCompletedHas100Progress() {
        Lesson lesson = LessonBuilder.lessonWithName("Ecuaciones").build();
        Task task = Mockito.mock(Task.class);
        Mockito.when(task.getState()).thenReturn(State.COMPLETED);

        lesson.addTask(task);

        assertEquals(100, lesson.progress());
    }

    @Test
    public void progressWithTwoTasksOneCompletedAndOtherUncompletedHas50Progress() {
        Lesson lesson = LessonBuilder.lessonWithName("Ecuaciones").build();
        Task task1 = Mockito.mock(Task.class);
        Task task2 = Mockito.mock(Task.class);
        Mockito.when(task1.getState()).thenReturn(State.COMPLETED);
        Mockito.when(task2.getState()).thenReturn(State.UNCOMPLETED);

        lesson.addTask(task1);
        lesson.addTask(task2);

        assertEquals(50, lesson.progress());
    }

    @Test
    public void progressWithoutTasksHas0Progress() {
        Lesson lesson = LessonBuilder.lessonWithName("Ecuaciones").build();

        assertEquals(0, lesson.progress());
    }

    @Test
    public void qualifyWithOneTaskEvaluateAnswerCorrect() {
        Lesson lesson = LessonBuilder.lessonWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 10;
        Task task = Mockito.mock(Task.class);
        Mockito.when(task.getState()).thenReturn(State.COMPLETED);
        Mockito.when(task.getAnswer()).thenReturn(1l);
        Mockito.when(task.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(task.getScore()).thenReturn(10.0);
        lesson.addTask(task);
        assertEquals(expected, lesson.qualification());
    }

    @Test
    public void qualifyWithTwoTaskEvaluateAnswerIncorrect() {
        Lesson lesson = LessonBuilder.lessonWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 5;
        Task task = Mockito.mock(Task.class);
        Task task2 = Mockito.mock(Task.class);
        Mockito.when(task.getState()).thenReturn(State.COMPLETED);
        Mockito.when(task.getAnswer()).thenReturn(1l);
        Mockito.when(task.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(task.getScore()).thenReturn(5.0);

        Mockito.when(task2.getState()).thenReturn(State.COMPLETED);
        Mockito.when(task2.getAnswer()).thenReturn(1l);
        Mockito.when(task2.getCorrectAnswer()).thenReturn(2l);
        Mockito.when(task2.getScore()).thenReturn(5.0);

        lesson.addTask(task);
        lesson.addTask(task2);

        assertEquals(expected, lesson.qualification());
    }

    @Test
    public void qualifyWithUncompleteTaskHas0Qualification() {
        Lesson lesson = LessonBuilder.lessonWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 0;
        Task task = Mockito.mock(Task.class);
        Mockito.when(task.getState()).thenReturn(State.UNCOMPLETED);
        Mockito.when(task.getAnswer()).thenReturn(null);
        Mockito.when(task.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(task.getScore()).thenReturn(10.0);

        lesson.addTask(task);

        assertEquals(expected, lesson.qualification());
    }

    @Test
    public void qualifyWithoutTaskEvaluateAnswerCorrect() {
        Lesson lesson = LessonBuilder.lessonWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 0;
        assertEquals(expected, lesson.qualification());
    }

    @Test
    public void qualifyWithQualificationMoreThanMaxNoteThrowsException() {
        Lesson lesson = LessonBuilder.lessonWithNameAndMaxNote("Ecuaciones", 10).build();

        Task task = Mockito.mock(Task.class);
        Mockito.when(task.getState()).thenReturn(State.COMPLETED);
        Mockito.when(task.getAnswer()).thenReturn(1l);
        Mockito.when(task.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(task.getScore()).thenReturn(100.0);
        lesson.addTask(task);

        IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class, () -> {
            lesson.qualification();
        });

        assertTrue(assertThrows.getMessage().contains("The qualification exceed the max note."));
    }

}

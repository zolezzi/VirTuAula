package ar.edu.unq.virtuaula.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.builder.LessonBuilder;

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
        
        Task task1 = Mockito.mock(Task.class);
        Mockito.when(task1.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(task1.getScore()).thenReturn(100.0);
        
        Task task2 = Mockito.mock(Task.class);
        Mockito.when(task2.getCorrectAnswer()).thenReturn(2l);
        Mockito.when(task2.getScore()).thenReturn(100.0);
        
        StudentTask studenttask1 = Mockito.mock(StudentTask.class);
        StudentTask studenttask2 = Mockito.mock(StudentTask.class);

        Mockito.when(studenttask1.getAnswer()).thenReturn(1l);
        Mockito.when(studenttask2.getAnswer()).thenReturn(1l);
        Mockito.when(studenttask1.getState()).thenReturn(State.COMPLETED);
        Mockito.when(studenttask2.getState()).thenReturn(State.COMPLETED);
        Mockito.when(studenttask1.getTask()).thenReturn(task1);
        Mockito.when(studenttask2.getTask()).thenReturn(task2);

        List<StudentTask> tasks = new ArrayList<>();
        tasks.add(studenttask1);
        tasks.add(studenttask2);
        assertEquals(100, lesson.progress(tasks));
    }

    @Test
    public void progressWithTwoTasksOneCompletedAndOtherUncompletedHas50Progress() {
        Lesson lesson = LessonBuilder.lessonWithName("Ecuaciones").build();
        Task task1 = Mockito.mock(Task.class);
        Mockito.when(task1.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(task1.getScore()).thenReturn(100.0);
        
        Task task2 = Mockito.mock(Task.class);
        Mockito.when(task2.getCorrectAnswer()).thenReturn(2l);
        Mockito.when(task2.getScore()).thenReturn(100.0);
        
        StudentTask studenttask1 = Mockito.mock(StudentTask.class);
        StudentTask studenttask2 = Mockito.mock(StudentTask.class);

        Mockito.when(studenttask1.getAnswer()).thenReturn(1l);
        Mockito.when(studenttask2.getAnswer()).thenReturn(1l);
        Mockito.when(studenttask1.getState()).thenReturn(State.COMPLETED);
        Mockito.when(studenttask2.getState()).thenReturn(State.UNCOMPLETED);
        Mockito.when(studenttask1.getTask()).thenReturn(task1);
        Mockito.when(studenttask2.getTask()).thenReturn(task2);

        List<StudentTask> tasks = new ArrayList<>();
        tasks.add(studenttask1);
        tasks.add(studenttask2);
        assertEquals(50, lesson.progress(tasks));
    }

    @Test
    public void progressWithoutTasksHas0Progress() {
        Lesson lesson = LessonBuilder.lessonWithName("Ecuaciones").build();
        List<StudentTask> tasks = new ArrayList<>();
        assertEquals(0, lesson.progress(tasks));
    }

    @Test
    public void qualifyWithOneTaskEvaluateAnswerCorrect() {
        Lesson lesson = LessonBuilder.lessonWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 10;
        Task task = Mockito.mock(Task.class);
        Mockito.when(task.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(task.getScore()).thenReturn(10.0);
        StudentTask studentTask = Mockito.mock(StudentTask.class);
        Mockito.when(studentTask.getAnswer()).thenReturn(1l);
        Mockito.when(studentTask.getState()).thenReturn(State.COMPLETED);
        Mockito.when(studentTask.getTask()).thenReturn(task);
        lesson.addTask(task);
        List<StudentTask> tasks = new ArrayList<>();
        tasks.add(studentTask);
        assertEquals(expected, lesson.qualification(tasks));
    }

    @Test
    public void qualifyWithTwoTaskEvaluateAnswerIncorrect() {
        Lesson lesson = LessonBuilder.lessonWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 5;
        Task task = Mockito.mock(Task.class);
        Task task2 = Mockito.mock(Task.class);
        Mockito.when(task.getAnswer()).thenReturn(1l);
        Mockito.when(task.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(task.getScore()).thenReturn(5.0);

        Mockito.when(task2.getAnswer()).thenReturn(1l);
        Mockito.when(task2.getCorrectAnswer()).thenReturn(2l);
        Mockito.when(task2.getScore()).thenReturn(5.0);
        
        StudentTask studentTask = Mockito.mock(StudentTask.class);
        Mockito.when(studentTask.getAnswer()).thenReturn(1l);
        Mockito.when(studentTask.getState()).thenReturn(State.COMPLETED);
        Mockito.when(studentTask.getTask()).thenReturn(task);
        
        StudentTask studentTask2 = Mockito.mock(StudentTask.class);
        Mockito.when(studentTask2.getAnswer()).thenReturn(1l);
        Mockito.when(studentTask2.getState()).thenReturn(State.COMPLETED);
        Mockito.when(studentTask2.getTask()).thenReturn(task2);

        List<StudentTask> tasks = new ArrayList<>();
        tasks.add(studentTask);
        tasks.add(studentTask2);

        lesson.addTask(task);
        lesson.addTask(task2);

        assertEquals(expected, lesson.qualification(tasks));
    }

    @Test
    public void qualifyWithUncompleteTaskHas0Qualification() {
        Lesson lesson = LessonBuilder.lessonWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 0;
        Task task = Mockito.mock(Task.class);
        Mockito.when(task.getAnswer()).thenReturn(null);
        Mockito.when(task.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(task.getScore()).thenReturn(10.0);

        StudentTask studentTask = Mockito.mock(StudentTask.class);
        Mockito.when(studentTask.getAnswer()).thenReturn(1l);
        Mockito.when(studentTask.getState()).thenReturn(State.UNCOMPLETED);
        Mockito.when(studentTask.getTask()).thenReturn(task);
        
        lesson.addTask(task);
        List<StudentTask> tasks = new ArrayList<>();
        tasks.add(studentTask);

        assertEquals(expected, lesson.qualification(tasks));
    }

    @Test
    public void qualifyWithoutTaskEvaluateAnswerCorrect() {
        Lesson lesson = LessonBuilder.lessonWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 0;
        List<StudentTask> tasks = new ArrayList<>();
        assertEquals(expected, lesson.qualification(tasks));
    }

    @Test
    public void qualifyWithQualificationMoreThanMaxNoteThrowsException() {
        Lesson lesson = LessonBuilder.lessonWithNameAndMaxNote("Ecuaciones", 10).build();

        Task task = Mockito.mock(Task.class);
        Mockito.when(task.getAnswer()).thenReturn(1l);
        Mockito.when(task.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(task.getScore()).thenReturn(100.0);
        
        StudentTask studentTask = Mockito.mock(StudentTask.class);
        Mockito.when(studentTask.getAnswer()).thenReturn(1l);
        Mockito.when(studentTask.getState()).thenReturn(State.COMPLETED);
        Mockito.when(studentTask.getTask()).thenReturn(task);
        lesson.addTask(task);
        List<StudentTask> tasks = new ArrayList<>();
        tasks.add(studentTask);
        IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class, () -> {
            lesson.qualification(tasks);
        });

        assertTrue(assertThrows.getMessage().contains("The qualification exceed the max note."));
    }

}

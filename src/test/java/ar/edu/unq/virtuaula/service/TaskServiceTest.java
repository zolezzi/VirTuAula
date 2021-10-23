package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.exception.LessonNotFoundException;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.vo.TaskStudentVO;

public class TaskServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private TaskService guestTaskService;

    @Test
    public void getAllTaskByLessonWithTaskReturnListWithTaskWithStatement() {
        Classroom classroom = createOneClassroom();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        List<TaskStudentVO> result = guestTaskService.getAllTaskByLessonForStudent(classroom.getLessons().get(0), 1L);
        assertEquals(task.getStatement(), result.get(0).getStatement());
    }

    @Test
    public void getAllTaskByLessonWithTaskReturnListWithTaskWithId() {
        Classroom classroom = createOneClassroom();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        List<TaskStudentVO> result = guestTaskService.getAllTaskByLessonForStudent(classroom.getLessons().get(0), 1L);
        assertNotNull(result.get(0).getId());
        assertEquals(task.getId(), result.get(0).getId());
    }

    @Test
    public void getAllTaskByLessonWithTaskAndTeacherReturnListWithTaskWithStatement() throws LessonNotFoundException {
        Classroom classroom = createOneClassroom();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        TeacherAccount account = (TeacherAccount) createOneTeacherAccountWithClassroom(classroom);
        List<TaskDTO> result = guestTaskService.getAllTaskByLesson(classroom.getLessons().get(0), account);
        assertEquals(task.getStatement(), result.get(0).getStatement());
    }

    @Test
    public void getAllTaskByLessonWithTaskAndTeacherWithoutClassroomReturnNotFoundLesson() throws LessonNotFoundException {
        Classroom classroom = createOneClassroom();
        TeacherAccount account = (TeacherAccount) createOneTeacherAccount();
        Exception exception = assertThrows(LessonNotFoundException.class, () -> {
            guestTaskService.getAllTaskByLesson(classroom.getLessons().get(0), account);
        });
        String expectedMessage = "Not found lesson id: 1 for teacher account id: 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getAllTaskByLessonWithTaskAndTeacherWithClassroomButWithoutLessonReturnNotFoundLesson() throws LessonNotFoundException {
        Classroom classroom = createOneClassroom();
        Classroom classroom2 = createOneClassroom();
        TeacherAccount account = (TeacherAccount) createOneTeacherAccountWithClassroom(classroom2);
        Exception exception = assertThrows(LessonNotFoundException.class, () -> {
            guestTaskService.getAllTaskByLesson(classroom.getLessons().get(0), account);
        });
        String expectedMessage = "Not found lesson id: 1 for teacher account id: 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getAllTaskByLessonWithTaskAndTeacherReturnListWithTaskWithId() throws LessonNotFoundException {
        Classroom classroom = createOneClassroom();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        TeacherAccount account = (TeacherAccount) createOneTeacherAccountWithClassroom(classroom);
        List<TaskDTO> result = guestTaskService.getAllTaskByLesson(classroom.getLessons().get(0), account);
        assertNotNull(result.get(0).getId());
        assertEquals(task.getId(), result.get(0).getId());
    }

    @Test
    public void getAllTaskByLessonWithTaskReturnListWithTaskWithOptionTaskWithId() {
        Classroom classroom = createOneClassroomWithTwoTasksAndTwoOptionTasks();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        List<TaskStudentVO> result = guestTaskService.getAllTaskByLessonForStudent(classroom.getLessons().get(0), 1L);
        assertNotNull(result.get(0).getId());
        assertEquals(task.getId(), result.get(0).getId());
    }
    
    @Test
    public void getAllTaskByLessonWithStudentTaskReturnListWithStudentTaskWithOptionTaskWithId() {
        Classroom classroom = createOneClassroomWithTwoTasksAndTwoOptionTasks();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        StudentAccount student = (StudentAccount) createOneStudentAccount();
        createOneStudentTasktWithLessonAndTaskAndStudentAccount(lesson, task, student);
        List<TaskStudentVO> result = guestTaskService.getAllTaskByLessonForStudent(classroom.getLessons().get(0), 1L);
        assertNotNull(result.get(0).getId());
        assertEquals(task.getId(), result.get(0).getId());
    }
    
    private Lesson getFirstLesson(Classroom classroom) {
		return classroom.getLessons().get(0);
	}


	private Task getFirstTask(Lesson lesson) {
		return lesson.getTasks().get(0);
	}
}

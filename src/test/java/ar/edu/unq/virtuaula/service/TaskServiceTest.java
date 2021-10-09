package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.vo.TaskStudentVO;
import ar.edu.unq.virtuaula.vo.TaskVO;

public class TaskServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private TaskService guestTaskService;

    @Test
    public void getAllTaskByLessonWithTaskReturnListWithTaskWithStatement() {
        Classroom classroom = createOneClassroom();
        List<TaskStudentVO> result = guestTaskService.getAllTaskByLessonForStudent(classroom.getLessons().get(0));
        assertEquals(classroom.getLessons().get(0).getTasks().get(0).getStatement(), result.get(0).getStatement());
    }

    @Test
    public void getAllTaskByLessonWithTaskReturnListWithTaskWithId() {
        Classroom classroom = createOneClassroom();
        List<TaskStudentVO> result = guestTaskService.getAllTaskByLessonForStudent(classroom.getLessons().get(0));
        assertNotNull(result.get(0).getId());
        assertEquals(classroom.getLessons().get(0).getTasks().get(0).getId(), result.get(0).getId());
    }
    

    @Test
    public void getAllTaskByLessonWithTaskAndTeacherReturnListWithTaskWithStatement() {
        Classroom classroom = createOneClassroom();
        TeacherAccount account = (TeacherAccount) createOneTeacherAccountWithClassroom(classroom);
        List<TaskDTO> result = guestTaskService.getAllTaskByLesson(classroom.getLessons().get(0), account);
        assertEquals(classroom.getLessons().get(0).getTasks().get(0).getStatement(), result.get(0).getStatement());
    }

    @Test
    public void getAllTaskByLessonWithTaskAndTeacherReturnListWithTaskWithId() {
        Classroom classroom = createOneClassroom();
        TeacherAccount account = (TeacherAccount) createOneTeacherAccountWithClassroom(classroom);
        List<TaskDTO> result = guestTaskService.getAllTaskByLesson(classroom.getLessons().get(0), account);
        assertNotNull(result.get(0).getId());
        assertEquals(classroom.getLessons().get(0).getTasks().get(0).getId(), result.get(0).getId());
    }
}

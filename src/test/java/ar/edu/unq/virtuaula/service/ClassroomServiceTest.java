package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.exception.ClassroomNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.model.TeacherAccount;

public class ClassroomServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private ClassroomService guestClassroomService;

    @Test
    public void getAllWithClassroomReturnNotEmptyList() {
        int expected = 1;
        createOneClassroom();
        List<ClassroomDTO> result = guestClassroomService.getAll();
        assertEquals(expected, result.size());
    }

    @Test
    public void getAllWithClassroomReturnClassroomWithId() {
        Classroom classroom = createOneClassroom();
        List<ClassroomDTO> result = guestClassroomService.getAll();
        assertNotNull(result.get(0).getId());
        assertEquals(classroom.getId(), result.get(0).getId());
    }

    @Test
    public void getAllWithClassroomReturnMathClassroomName() {
        Classroom classroom = createOneClassroom();
        List<ClassroomDTO> result = guestClassroomService.getAll();
        assertEquals(classroom.getName(), result.get(0).getName());
    }

    @Test
    public void getAllWithoutClassroomsReturnEmptyList() {
        int expected = 0;
        List<ClassroomDTO> result = guestClassroomService.getAll();
        assertEquals(expected, result.size());
    }

    @Test
    public void getAllWithClassroomReturnClassroomWithProgress() {
        int expected = 0;
        createOneClassroom();
        List<ClassroomDTO> result = guestClassroomService.getAll();
        assertEquals(expected, result.get(0).getProgress());
    }

    @Test
    public void getAllWithClassroomWithAccountReturnClassroomWithProgress() {
        int expected = 100;
        Classroom classroom = createOneClassroom();
        Lesson lesson = classroom.getLessons().get(0);
        Task task = lesson.getTasks().get(0);
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccountWithClassroom(classroom);
        createOneStudentTasktWithLessonAndTaskAndStudentAccount(lesson, task, studentAccount);
        List<ClassroomDTO> result = guestClassroomService.findByAccount(studentAccount);
        assertEquals(expected, result.get(0).getProgress());
    }
    
    @Test
    public void getAllWithClassroomWithoutLessonAndAccountReturnClassroomWithProgress() {
        int expected = 0;
        Classroom classroom = createOneClassroomWithoutLesson();
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccountWithClassroom(classroom);
        List<ClassroomDTO> result = guestClassroomService.findByAccount(studentAccount);
        assertEquals(expected, result.get(0).getProgress());
    }
    
    @Test
    public void findByIdWithClassroomReturnClassroom() {
        Classroom classroom = createOneClassroom();
        Classroom classroomReturn = guestClassroomService.findById(classroom.getId());
        assertEquals(classroom.getId(), classroomReturn.getId());
        assertEquals(classroom.getName(), classroomReturn.getName());
    }
    
    @Test
    public void findByAccountIdWithoutClassroomReturnEmptyList() {
    	int expected = 0;
    	Account account = createOneTeacherAccount();
        List<ClassroomDTO> classroomReturn = guestClassroomService.findByAccount(account);
        assertEquals(expected, classroomReturn.size());
    }
    
    @Test
    public void findByAccountIdWithClassroomReturnContainsClassroom() {
    	Classroom classroom = createOneClassroom();
    	TeacherAccount account = (TeacherAccount) createOneTeacherAccountWithClassroom(classroom);
    	Boolean result = account.containsClassroom(classroom);
        assertTrue(result);
    }
    
    @Test
    public void whenAccountQueryContainsClassroomWithoutClassroomReturnFalse() {
    	Classroom classroom = createOneClassroom();
    	Classroom classroom2 = createOneClassroom();
    	TeacherAccount account = (TeacherAccount) createOneTeacherAccountWithClassroom(classroom);
    	Boolean result = account.containsClassroom(classroom2);
        assertFalse(result);
    }
    
    @Test
    public void whenCreateNewClassroomWithTeacherAccountThenClassroomWithId() {
    	int expected = 2;
    	List<Long> accounts = new ArrayList<>();
    	accounts.add(1l);
    	Classroom classroom = createOneClassroom();
    	StudentAccount student = (StudentAccount) createOneStudentAccount();
    	TeacherAccount teacher = (TeacherAccount) createOneTeacherAccountWithClassroomAndStudent(classroom, student);
    	ClassroomDTO classroomDTO = Mockito.mock(ClassroomDTO.class);
	    Mockito.when(classroomDTO.getName()).thenReturn("Algebra");
	    Mockito.when(classroomDTO.getDescription()).thenReturn("Materia dada en el horario del 14 a 18");
	    ClassroomDTO result = guestClassroomService.create(teacher, classroomDTO, accounts);
        assertNotNull(result);
        assertEquals(expected, teacher.getClassrooms().size());
    }
    @Test
    public void whenAssignClassroomWithTeacherAccountThenClassroomWithId() throws ClassroomNotFoundException {
    	List<Long> accounts = new ArrayList<>();
    	accounts.add(1l);
    	accounts.add(2l);
    	Classroom classroom = createOneClassroom();
        Lesson lesson = classroom.getLessons().get(0);
        Task task = lesson.getTasks().get(0);
        StudentAccount student = (StudentAccount) createOneStudentAccount();
    	student = (StudentAccount) createOneStudentTasktWithLessonAndTaskAndStudentAccount(lesson, task, student);
    	TeacherAccount teacher = (TeacherAccount) createOneTeacherAccountWithClassroomAndStudent(classroom, student);
	    ResponseMessage result = guestClassroomService.assign(teacher, 1l, accounts);
        assertNotNull(result);
    }

}
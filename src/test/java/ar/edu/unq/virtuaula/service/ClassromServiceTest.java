package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.TeacherAccount;

public class ClassromServiceTest extends VirtuaulaApplicationTests {

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
        int expected = 100;
        createOneClassroom();
        List<ClassroomDTO> result = guestClassroomService.getAll();
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

}

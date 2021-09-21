package ar.edu.unq.virtuaula.service;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}

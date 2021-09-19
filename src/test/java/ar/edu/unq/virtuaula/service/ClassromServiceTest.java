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
        createClassroomWithName("Matematicas");

        List<ClassroomDTO> result = guestClassroomService.getAll();
        assertEquals(expected, result.size());
    }

    @Test
    public void getAllWithClassroomReturnClassroomWithId() {
        Classroom classroom = createClassroomWithName("Matematicas");

        List<ClassroomDTO> result = guestClassroomService.getAll();
        assertNotNull(result.get(0).getId());
        assertEquals(classroom.getId(), result.get(0).getId());
    }

    @Test
    public void getAllWithClassroomReturnMathClassroomName() {
        String name = "Matematicas";
        createClassroomWithName(name);

        List<ClassroomDTO> result = guestClassroomService.getAll();
        assertEquals(name, result.get(0).getName());
    }

    @Test
    public void getAllWithoutClassroomsReturnEmptyList() {
        int expected = 0;
        List<ClassroomDTO> result = guestClassroomService.getAll();
        assertEquals(expected, result.size());
    }

    @Test
    public void findByIdWithClassroomReturnClassroom() {
        String name = "Matematicas";
        Classroom classroomPersisted = createClassroomWithName(name);

        Classroom classroomReturn = guestClassroomService.findById(classroomPersisted.getId());

        assertEquals(classroomPersisted.getId(), classroomReturn.getId());
        assertEquals(name, classroomReturn.getName());
    }
}

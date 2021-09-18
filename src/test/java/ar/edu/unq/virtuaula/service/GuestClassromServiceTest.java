package ar.edu.unq.virtuaula.service;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.repository.GuestClassromRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class GuestClassromServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private GuestClassromService guestClassromService;
    @Autowired
    private GuestClassromRepository guestClassroomRepository;

    @Test
    public void getAllWithClassroomReturnNotEmptyList() {
        int expected = 1;
        Classroom classroom = new Classroom("Matematicas");
        guestClassroomRepository.save(classroom);

        List<ClassroomDTO> result = guestClassromService.getAll();
        assertEquals(expected, result.size());
    }

    @Test
    public void getAllWithClassroomReturnClassroomWithId() {
        Classroom classroom = new Classroom("Matematicas");
        guestClassroomRepository.save(classroom);

        List<ClassroomDTO> result = guestClassromService.getAll();
        assertNotNull(result.get(0).getId());
    }

    @Test
    public void getAllWithClassroomReturnMathClassroomName() {
        String name = "Matematicas";
        Classroom classroom = new Classroom(name);
        guestClassroomRepository.save(classroom);

        List<ClassroomDTO> result = guestClassromService.getAll();
        assertEquals(name, result.get(0).getName());
    }

    @Test
    public void getAllWithoutClassroomsReturnEmptyList() {
        int expected = 0;
        List<ClassroomDTO> result = guestClassromService.getAll();
        assertEquals(expected, result.size());
    }
}

package ar.edu.unq.virtuaula.service;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GuestLessonServiceTest extends VirtuaulaApplicationTests {
    
    @Autowired
    private GuestLessonService guestLessonService;
    
    @Test
    public void getAllByClassroomWithExistingClassroomReturnLesson() {
        int expected = 1;
        Classroom classroom = createClassroomWithName("Matematicas");
        createLessonWithName("Ecuaciones", classroom);
        
        List<LessonDTO> result = guestLessonService.getAllByClassroom(classroom);
        
        assertEquals(expected, result.size());
    }
    
    @Test
    public void getAllByClassroomWithExistingClassroomReturnLessonWithId() {
        Classroom classroom = createClassroomWithName("Matematicas");
        createLessonWithName("Ecuaciones", classroom);
        
        List<LessonDTO> result = guestLessonService.getAllByClassroom(classroom);
        
        assertNotNull(result.get(0).getId());
    }
    
    @Test
    public void getAllByClassroomWithExistingClassroomReturnLessonWithName() {
        String name = "Ecuaciones";
        Classroom classroom = createClassroomWithName("Matematicas");
        createLessonWithName(name, classroom);
        
        List<LessonDTO> result = guestLessonService.getAllByClassroom(classroom);
        
        assertEquals(name, result.get(0).getName());
    }
    
    @Test
    public void getAllByClassroomWithoutLessonsReturnEmptyList() {
        int expected = 0;
        Classroom classroom = createClassroomWithName("Matematicas");
        List<LessonDTO> result = guestLessonService.getAllByClassroom(classroom);
        
        assertEquals(expected, result.size());
    }
}

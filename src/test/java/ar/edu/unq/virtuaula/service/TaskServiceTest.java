package ar.edu.unq.virtuaula.service;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskServiceTest extends VirtuaulaApplicationTests {
    
    @Autowired
    private TaskService guestTaskService;
    
    @Test
    public void getAllTaskByLessonWithoutLessonsReturnEmptyList() {
        int expected = 0;
        String name = "Ecuaciones";
        Classroom classroom = createClassroomWithName("Matematicas");
        Lesson lesson = createLessonWithName(name, classroom);
        List<TaskDTO> result = guestTaskService.getAllTaskByLesson(lesson);
        assertEquals(expected, result.size());
    }

    @Test
    public void getAllTaskByLessonWithTaskReturnListWithTaskWithStatement() {
        String statement = "Cual es el valor de x para la ecuacion x = x * 2 + 1";
        Classroom classroom = createClassroomWithName("Matematicas");
        createLessonWithName("Ecuaciones", classroom);
        Lesson lesson = createTask(classroom, statement, 1l);

        List<TaskDTO> result = guestTaskService.getAllTaskByLesson(lesson);
        assertEquals(statement, result.get(0).getStatement());
    }

    @Test
    public void getAllTaskByLessonWithTaskReturnListWithTaskWithId() {
        Classroom classroom = createClassroomWithName("Matematicas");
        createLessonWithName("Ecuaciones", classroom);
        Lesson lesson = createTask(classroom, "Cual es el valor de x para la ecuacion x = x * 2 + 1", 1l);

        List<TaskDTO> result = guestTaskService.getAllTaskByLesson(lesson);

        assertNotNull(result.get(0).getId());
        assertEquals(lesson.getTasks().get(0).getId(), result.get(0).getId());
    }
}

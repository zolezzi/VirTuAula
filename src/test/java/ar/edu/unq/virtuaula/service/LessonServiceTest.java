package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.vo.LessonVO;
import ar.edu.unq.virtuaula.repository.LessonRepository;

public class LessonServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private LessonService guestLessonService;
    @Autowired
    private LessonRepository guestLessonRepository;

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

    @Test
    public void findByIdWithLessonReturnLesson() {
        String name = "Ecuaciones";
        Classroom classroom = createClassroomWithName("Matematicas");
        Lesson lesson = createLessonWithName(name, classroom);
        Lesson lessonReturn = guestLessonService.findById(lesson.getId());
        assertEquals(lesson.getId(), lessonReturn.getId());
        assertEquals(name, lessonReturn.getName());
    }

    @Test
    public void completeTaskWithTaskProgressComplete() {
        int expected = 100;
        Classroom classroom = createClassroomWithName("Matematicas");
        createLessonWithName("Ecuaciones", classroom);
        Lesson lesson = createTask(classroom, "Cual es el valor de x para la ecuacion x = x * 2 + 1", 1l);
        LessonVO lessonVo = guestLessonService.completeTasks(classroom, lesson, lesson.getTasks());

        assertEquals(expected, lessonVo.getProgress());
    }
    
}

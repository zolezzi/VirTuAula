package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.LessonVO;
import ar.edu.unq.virtuaula.vo.TaskVO;
import java.util.Arrays;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LessonServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private LessonService guestLessonService;

    @Autowired
    private MapperUtil mapperUtil;

    @Test
    public void getAllByClassroomWithExistingClassroomReturnLesson() {
        int expected = 1;
        Classroom classroom = createOneClassroom();
        List<LessonVO> result = guestLessonService.getAllByClassroom(classroom);
        assertEquals(expected, result.size());
    }

    @Test
    public void getAllByClassroomWithExistingClassroomReturnLessonWithId() {
        Classroom classroom = createOneClassroom();
        List<LessonVO> result = guestLessonService.getAllByClassroom(classroom);
        assertNotNull(result.get(0).getId());
        assertEquals(classroom.getLessons().get(0).getId(), result.get(0).getId());
    }

    @Test
    public void getAllByClassroomWithExistingClassroomReturnLessonWithName() {
        Classroom classroom = createOneClassroom();
        List<LessonVO> result = guestLessonService.getAllByClassroom(classroom);
        assertEquals(classroom.getLessons().get(0).getName(), result.get(0).getName());
    }

    @Test
    public void getAllByClassroomWithExistingClassroomReturnLessonWithoutNote() {
        Classroom classroom = createOneClassroomWithTwoTasks();
        List<LessonVO> result = guestLessonService.getAllByClassroom(classroom);
        assertEquals(null, result.get(0).getNote());
    }

    @Test
    public void findByIdWithLessonReturnLesson() {
        Classroom classroom = createOneClassroom();
        Lesson lessonReturn = guestLessonService.findById(classroom.getLessons().get(0).getId());
        assertEquals(classroom.getLessons().get(0).getId(), lessonReturn.getId());
        assertEquals(classroom.getLessons().get(0).getName(), lessonReturn.getName());
    }

    @Test
    public void completeTaskWithTaskProgressComplete() {
        int expected = 100;
        Classroom classroom = createOneClassroom();
        List<TaskVO> tasks = createTaskVO(classroom.getLessons().get(0).getTasks());
        LessonVO lessonVo = guestLessonService.completeTasks(classroom, classroom.getLessons().get(0).getId(), tasks);
        assertEquals(expected, lessonVo.getProgress());
    }

    @Test
    public void completeTaskWithTaskProgressCompleteSetNote() {
        Classroom classroom = createOneClassroom();
        List<TaskVO> tasks = createTaskVO(classroom.getLessons().get(0).getTasks());
        LessonVO lessonVo = guestLessonService.completeTasks(classroom, classroom.getLessons().get(0).getId(), tasks);
        assertNotNull(lessonVo.getNote());
    }

    @Test
    public void completeTaskWithTaskProgressUncompleteDontAddNote() {
        Classroom classroom = createOneClassroomWithTwoTasks();
        TaskVO task = new TaskVO();
        task.setId(1l);
        task.setAnswerId(1l);
        List<TaskVO> tasks = Arrays.asList(task);
        LessonVO lessonVo = guestLessonService.completeTasks(classroom, classroom.getLessons().get(0).getId(), tasks);
        assertEquals(null, lessonVo.getNote());
    }

    @Test
    public void completeTaskWithTaskNonExistentThrowsException() {
        Classroom classroom = createOneClassroomWithTwoTasks();
        TaskVO task = new TaskVO();
        task.setId(-1l);
        task.setAnswerId(1l);
        List<TaskVO> tasks = Arrays.asList(task);
        NoSuchElementException assertThrows = assertThrows(NoSuchElementException.class, () -> {
            guestLessonService.completeTasks(classroom, classroom.getLessons().get(0).getId(), tasks);
        });

        assertTrue(assertThrows.getMessage().contains("Error not found Tasks with id: " + task.getId()));
    }

    private List<TaskVO> createTaskVO(List<Task> tasks) {
        return tasks.stream().map(task -> {
            TaskVO taskVO = mapperUtil.getMapper().map(task, TaskVO.class);
            return taskVO;
        }).collect(toList());
    }

}

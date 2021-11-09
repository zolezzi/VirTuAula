package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.dto.OptionTaskDTO;
import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.exception.ClassroomNotFoundException;
import ar.edu.unq.virtuaula.exception.LessonDateExpiredException;
import ar.edu.unq.virtuaula.exception.LessonNotFoundException;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.LessonVO;
import ar.edu.unq.virtuaula.vo.TaskVO;

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
    public void completeTaskWithTaskProgressComplete() throws Exception {
        int expected = 100;
        Classroom classroom = createOneClassroom();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
        StudentAccount account = (StudentAccount) createOneStudentTasktWithLessonAndTaskAndStudentAccount(lesson, task, studentAccount);
        List<TaskVO> tasks = createTaskVO(lesson.getTasks());
        LessonVO lessonVo = guestLessonService.completeTasks(classroom, lesson.getId(), account, tasks);
        assertEquals(expected, lessonVo.getProgress());
    }

	@Test
    public void completeTaskWithTaskProgressCompleteSetNote() throws Exception {
        Classroom classroom = createOneClassroom();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
        studentAccount = (StudentAccount) createOneStudentTasktWithLessonAndTaskAndStudentAccount(lesson, task, studentAccount);
        List<TaskVO> tasks = createTaskVO(lesson.getTasks());
        LessonVO lessonVo = guestLessonService.completeTasks(classroom, lesson.getId(), studentAccount, tasks);
        assertNotNull(lessonVo.getNote());
    }

    @Test
    public void completeTaskWithTaskProgressUncompleteDontAddNote() throws Exception {
        Classroom classroom = createOneClassroomWithTwoTasks();
        TaskVO taskVO = new TaskVO();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        Task task2 = lesson.getTasks().get(1);
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
        createOneStudentTaskUncompletedtWithLessonAndTaskAndStudentAccount(lesson, task, studentAccount);
        createOneStudentTaskUncompletedtWithLessonAndTaskAndStudentAccount(lesson, task2, studentAccount);
        taskVO.setId(1l);
        taskVO.setAnswerId(1l);
        List<TaskVO> tasks = Arrays.asList(taskVO);
        LessonVO lessonVo = guestLessonService.completeTasks(classroom, lesson.getId(), studentAccount, tasks);
        assertEquals(null, lessonVo.getNote());
    }

    @Test
    public void completeTaskWithTaskNonExistentThrowsException() {
        Classroom classroom = createOneClassroomWithTwoTasks();
        Lesson lesson = getFirstLesson(classroom);
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
        TaskVO taskVO = new TaskVO();
        taskVO.setId(-1l);
        taskVO.setAnswerId(1l);
        List<TaskVO> tasks = Arrays.asList(taskVO);
        NoSuchElementException assertThrows = assertThrows(NoSuchElementException.class, () -> {
        	guestLessonService.completeTasks(classroom, lesson.getId(), studentAccount, tasks);
        });

        assertTrue(assertThrows.getMessage().contains("Error not found Tasks with id: " + taskVO.getId()));
    }

    @Test
    public void whenAccountCreateLessonInClassroomWithAccountClassroomNotFoundClassroomReturnException() {
        Classroom classroom = createOneClassroom();
        Classroom classroom2 = createOneClassroom();
        LessonDTO lesson = new LessonDTO();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1l);
        taskDTO.setTaskTypeId(1l);
        List<TaskDTO> tasks = Arrays.asList(taskDTO);
        lesson.setTasks(tasks);
        TeacherAccount account = (TeacherAccount) createOneTeacherAccountWithClassroom(classroom);

        Exception exception = assertThrows(ClassroomNotFoundException.class, () -> {
            guestLessonService.create(classroom2, account, lesson);
        });

        String expectedMessage = "Error not found classroom with id: 2";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenAccountCreateLessonInClassroomWithoutClassroomNotFoundClassroomReturnException() {
        Classroom classroom = createOneClassroom();
        LessonDTO lesson = new LessonDTO();
        TeacherAccount account = (TeacherAccount) createOneTeacherAccount();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1l);
        taskDTO.setTaskTypeId(1l);
        List<TaskDTO> tasks = Arrays.asList(taskDTO);
        lesson.setTasks(tasks);
        Exception exception = assertThrows(ClassroomNotFoundException.class, () -> {
            guestLessonService.create(classroom, account, lesson);
        });

        String expectedMessage = "Error not found classroom with id: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void whenCompleteTaskOfLessonNotFoundThenThrowsLessonNotFoundException() {
        Classroom classroom = createOneClassroom();
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
        TaskVO taskVO = new TaskVO();
        taskVO.setId(-1l);
        taskVO.setAnswerId(1l);
        List<TaskVO> tasks = Arrays.asList(taskVO);
    	Exception exception = assertThrows(LessonNotFoundException.class, () -> {
    		guestLessonService.completeTasks(classroom, 10L, studentAccount, tasks);
        });

        String expectedMessage = "Error not found lesson with id: 10";
        String actualMessage = exception.getMessage();
       
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    public void whenCompleteTaskOfLessonNotContainsInClassroomThenThrowsLessonNotFoundException() {
        Classroom classroom = createOneClassroom();
        Classroom classroom2 = createOneClassroom();
        Lesson lesson = getFirstLesson(classroom2);
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
        TaskVO taskVO = new TaskVO();
        taskVO.setId(-1l);
        taskVO.setAnswerId(1l);
        List<TaskVO> tasks = Arrays.asList(taskVO);
    	Exception exception = assertThrows(LessonNotFoundException.class, () -> {
    		guestLessonService.completeTasks(classroom, lesson.getId(), studentAccount, tasks);
        });

        String expectedMessage = "Error not found lesson id: 2 for classroom id: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void whenFindClassroomByStudentThenReturnListNotEmptyTheLessonVo() {
    	int expected = 1;
        Classroom classroom = createOneClassroom();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
        createOneStudentTasktWithLessonAndTaskAndStudentAccount(lesson, task, studentAccount);
        List<LessonVO> result = guestLessonService.getAllByClassroomAndStudent(classroom, studentAccount);
        assertEquals(expected, result.size());
    }
    
    @Test
    public void whenFindClassroomByStudenkWithTaskProgressUncompleteDontAddNote() {
        Classroom classroom = createOneClassroomWithTwoTasks();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        Task task2 = lesson.getTasks().get(1);
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
        createOneStudentTasktWithLessonAndTaskAndStudentAccount(lesson, task, studentAccount);
        createOneStudentTaskUncompletedtWithLessonAndTaskAndStudentAccount(lesson, task2, studentAccount);
        List<LessonVO> result = guestLessonService.getAllByClassroomAndStudent(classroom, studentAccount);
        assertNull(result.get(0).getNote());
    }
    
    @Test
    public void whenCreateNewLessonByTeacherThenReturnLessonDTOWithId() throws Exception {
    	Classroom classroom = createOneClassroom();
    	createOneTaskType();
    	LessonDTO lesson = new LessonDTO();
    	TaskDTO task = new TaskDTO();
    	task.setCorrectAnswer(1l);
    	task.setScore(5d);
    	task.setStatement("test 1");
    	task.setOptions(createOptions());
    	task.setTaskTypeId(1l);
    	lesson.setMaxNote(10);
    	lesson.setName("Test");
    	List<TaskDTO> tasks = Arrays.asList(task);
    	lesson.setTasks(tasks);
    	TeacherAccount account = (TeacherAccount) createOneTeacherAccountWithClassroom(classroom);
    	LessonDTO result =  guestLessonService.create(classroom, account, lesson);
    	assertNotNull(result);
    }
    
    @Test
    public void whenCreateNewLessonByTeacherWithStudentThenReturnLessonDTOWithId() throws Exception {
    	Classroom classroom = createOneClassroom();
    	createOneTaskType();
    	LessonDTO lesson = new LessonDTO();
    	TaskDTO task = new TaskDTO();
    	task.setCorrectAnswer(1l);
    	task.setScore(5d);
    	task.setStatement("test 1");
    	task.setOptions(createOptions());
    	task.setTaskTypeId(1l);
    	lesson.setMaxNote(10);
    	lesson.setName("Test");
    	List<TaskDTO> tasks = Arrays.asList(task);
    	lesson.setTasks(tasks);
    	StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
    	TeacherAccount account = (TeacherAccount) createOneTeacherAccountWithClassroomAndStudent(classroom, studentAccount);
    	LessonDTO result =  guestLessonService.create(classroom, account, lesson);
    	assertNotNull(result);
    }
    
    @Test
    public void completeTaskWithTaskProgressCompleteAndIncrementLevel() throws Exception {
        int expected = 100;
        Classroom classroom = createOneClassroom();
        createLevelProfesional();
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
        StudentAccount account = (StudentAccount) createOneStudentTasktWithLessonAndTaskAndStudentAccount(lesson, task, studentAccount);
        List<TaskVO> tasks = createTaskVO(lesson.getTasks());
        LessonVO lessonVo = guestLessonService.completeTasks(classroom, lesson.getId(), account, tasks);
        assertEquals(expected, lessonVo.getProgress());
    }
    
    @Test
    public void completeTaskWithLessonWithDateExpiredThenReturnExceptionDateExpired() throws ParseException {
    	String dateString = "2021-11-08 23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(dateString + " UTC");
        Classroom classroom = createOneClassroomWithoutDateExpired(date);
        Lesson lesson = getFirstLesson(classroom);
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
        TaskVO taskVO = new TaskVO();
        taskVO.setId(1l);
        taskVO.setAnswerId(1l);
        List<TaskVO> tasks = Arrays.asList(taskVO);
        LessonDateExpiredException assertThrows = assertThrows(LessonDateExpiredException.class, () -> {
        	guestLessonService.completeTasks(classroom, lesson.getId(), studentAccount, tasks);
        });
        String expectedMessage = "Expired that lesson id: 1 for classroom id: 1";
        
        assertEquals(expectedMessage, assertThrows.getMessage());
    }
    
	@Test
    public void completeAllTaskThenReturnLessonVOWithLevelUp() throws Exception {
        Classroom classroom = createOneClassroomWithLessonScore(3000d);
        Lesson lesson = getFirstLesson(classroom);
        Task task = getFirstTask(lesson);
        StudentAccount studentAccount = (StudentAccount) createOneStudentAccount();
        studentAccount = (StudentAccount) createOneStudentTasktWithLessonAndTaskAndStudentAccount(lesson, task, studentAccount);
        List<TaskVO> tasks = createTaskVO(lesson.getTasks());
        LessonVO lessonVo = guestLessonService.completeTasks(classroom, lesson.getId(), studentAccount, tasks);
        assertNotNull(lessonVo.getNote());
    }
    
    private List<OptionTaskDTO> createOptions(){
    	OptionTaskDTO taskOption1 = new OptionTaskDTO();
    	OptionTaskDTO taskOption2 = new OptionTaskDTO();
    	taskOption1.setIsCorrect(true);
    	taskOption1.setResponseValue("test 1");
    	taskOption2.setIsCorrect(false);
    	taskOption2.setResponseValue("test 2");
    	return Arrays.asList(taskOption1,taskOption2);
    }
    
    private List<TaskVO> createTaskVO(List<Task> tasks) {
        return tasks.stream().map(task -> {
            TaskVO taskVO = mapperUtil.getMapper().map(task, TaskVO.class);
            return taskVO;
        }).collect(toList());
    }
    
    private Lesson getFirstLesson(Classroom classroom) {
		return classroom.getLessons().get(0);
	}


	private Task getFirstTask(Lesson lesson) {
		return lesson.getTasks().get(0);
	}
}

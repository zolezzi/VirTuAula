package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.exception.TeacherNotFoundException;
import ar.edu.unq.virtuaula.service.AccountService;
import ar.edu.unq.virtuaula.service.LessonService;
import ar.edu.unq.virtuaula.service.TaskService;
import ar.edu.unq.virtuaula.vo.TaskStudentVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;
    private final LessonService lessonService;
    private final AccountService accountService;

    @GetMapping("/tasks/{lessonId}")
    @ApiResponse(code = 200, message = "Success" , response = TaskStudentVO.class, responseContainer = "List")
    @ApiOperation(value = "Get all task students by lesson id", notes = "Get all tasks of a lesson")
    public List<TaskStudentVO> getByClassroomId(@PathVariable("lessonId") Long lessonId) {
        return taskService.getAllTaskByLessonForStudent(lessonService.findById(lessonId));
    }
    
    @GetMapping("/tasks/{lessonId}/{accountId}")
    @ApiResponse(code = 200, message = "Success" , response = TaskDTO.class, responseContainer = "List")
    @ApiOperation(value = "Get all task teacher by lesson id and account id", notes = "Get all tasks of a lesson and teacher")
    public List<TaskDTO> getByClassroomId(@PathVariable("lessonId") Long lessonId, @PathVariable("accountId") Long accountId ) throws TeacherNotFoundException {
        return taskService.getAllTaskByLesson(lessonService.findById(lessonId), accountService.findTeacherById(accountId));
    }
}

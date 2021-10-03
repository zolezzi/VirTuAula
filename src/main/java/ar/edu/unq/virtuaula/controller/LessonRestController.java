package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.service.AccountService;
import ar.edu.unq.virtuaula.service.ClassroomService;
import ar.edu.unq.virtuaula.service.LessonService;
import ar.edu.unq.virtuaula.vo.LessonVO;
import ar.edu.unq.virtuaula.vo.TaskVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LessonRestController {

    private final ClassroomService classroomService;
    private final LessonService lessonService;
    private final AccountService accountService;

    @GetMapping("/lessons/{classroomId}")
    public List<LessonVO> getByClassroomId(@PathVariable("classroomId") Long classroomId) {
        return lessonService.getAllByClassroom(classroomService.findById(classroomId));
    }

    @PostMapping("/lessons/{classroomId}/{lessonId}")
    public LessonVO completeTasks(@PathVariable("classroomId") Long classroomId, @PathVariable("lessonId") Long lessonId, @RequestBody List<TaskVO> tasks) {
        Classroom classroom = classroomService.findById(classroomId);
        return lessonService.completeTasks(classroom, lessonId, tasks);
    }
    
    @PostMapping("/lessons/create/{classroomId}/{accountId}")
    public LessonDTO create(@PathVariable("classroomId") Long classroomId, @PathVariable("accountId") Long accountId, @RequestBody LessonDTO lesson) throws Exception {
        return lessonService.create(classroomService.findById(classroomId), accountService.findTeacherById(accountId), lesson);
    }
}
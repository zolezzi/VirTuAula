package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.service.ClassroomService;
import ar.edu.unq.virtuaula.service.LessonService;
import ar.edu.unq.virtuaula.service.TaskService;
import ar.edu.unq.virtuaula.vo.LessonVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LessonRestController {

    private final ClassroomService classroomService;
    private final LessonService lessonService;
    private final TaskService taskService;

    @GetMapping("/lessons/{classroomId}")
    public List<LessonDTO> getByClassroomId(@PathVariable("classroomId") Long classroomId) {
        return lessonService.getAllByClassroom(classroomService.findById(classroomId));
    }

    @GetMapping("/lessons/{classroomId}/{lessonId}")
    public LessonVO completeTasks(@PathVariable("classroomId") Long classroomId, @PathVariable("lessonId") Long lessonId, @RequestBody List<TaskDTO> tasks) {
        return lessonService.completeTasks(classroomService.findById(classroomId), lessonService.findById(lessonId), taskService.mapTask(tasks));
    }
}

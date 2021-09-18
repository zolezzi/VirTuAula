package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.service.GuestLessonService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskRestController {
    private final GuestLessonService guestLessonService;
    
    @GetMapping("/tasks/{lessonId}")
    public List<TaskDTO> getByClassroomId(@PathVariable("lessonId") Long lessonId) {
        return guestLessonService.getAllTaskByLesson(guestLessonService.findById(lessonId));
    }
}

package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.service.GuestClassroomService;
import ar.edu.unq.virtuaula.service.GuestLessonService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GuestLessonRestController {
    
    private final GuestClassroomService guestClassroomService;
    private final GuestLessonService guestLessonService;
    
    @GetMapping("/lessons/{classroomId}")
    public List<LessonDTO> getByClassroomId(@PathVariable("classroomId") Long classroomId) {
        return guestLessonService.getAllByClassroom(guestClassroomService.findById(classroomId));
    }
    
    @GetMapping("/lessons/{classroomId}/{lessonId}")
    public List<LessonDTO> completeTask(@PathVariable("classroomId") Long classroomId, @PathVariable("lessonId") Long lessonId, @RequestBody TaskDTO task) {
        return guestLessonService.getAllByClassroom(guestClassroomService.findById(classroomId));
    }
}

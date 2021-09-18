package ar.edu.unq.virtuaula.controller;

import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.service.GuestClassroomService;
import ar.edu.unq.virtuaula.service.GuestLessonService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

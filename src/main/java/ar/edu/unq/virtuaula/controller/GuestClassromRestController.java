package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.service.GuestClassroomService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GuestClassromRestController {

    private final GuestClassroomService guestClassromService;

    @GetMapping("/classrooms")
    public List<ClassroomDTO> getAll() {
        return guestClassromService.getAll();
    }
}

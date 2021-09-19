package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.service.ClassroomService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClassromRestController {

    private final ClassroomService classromService;

    @GetMapping("/classrooms")
    public List<ClassroomDTO> getAll() {
        return classromService.getAll();
    }
}

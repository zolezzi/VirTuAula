package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.service.AccountService;
import ar.edu.unq.virtuaula.service.ClassroomService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClassroomRestController {

    private final ClassroomService classromService;
    private final AccountService accountService;

    @GetMapping("/classrooms/{accountId}")
    public List<ClassroomDTO> findByAccountId(@PathVariable("accountId") Long accountId) throws AccountNotFoundException {
        return classromService.findByAccount(accountService.findById(accountId));
    }
}

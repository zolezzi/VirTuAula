package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.service.AccountService;
import ar.edu.unq.virtuaula.service.ClassroomService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClassroomRestController {

    private final ClassroomService classromService;
    private final AccountService accountService;

    @GetMapping("/classrooms/{accountId}")
    @ApiResponse(code = 200, message = "Success", response = ClassroomDTO.class, responseContainer = "List")
    @ApiOperation(value = "Get all classrooms by account id", notes = "Get all classrooms of a account")
    public List<ClassroomDTO> findByAccountId(@PathVariable("accountId") Long accountId) throws AccountNotFoundException {
        return classromService.findByAccount(accountService.findById(accountId));
    }

    @PostMapping("/classrooms/create/{accountId}")
    @ApiResponse(code = 200, message = "Successfully create classroom", response = LessonDTO.class)
    @ApiOperation(value = "Post create classroom for teacher by account id", notes = "Post create classroom for a teacher")
    public ClassroomDTO create(@PathVariable("accountId") Long accountId, @RequestBody ClassroomDTO classroom, @RequestBody List<AccountDTO> accounts) throws Exception {
        return classromService.create(accountService.findTeacherById(accountId), classroom, accounts);
    }
}

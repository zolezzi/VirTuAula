package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.TaskTypeDTO;
import ar.edu.unq.virtuaula.service.TaskTypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskTypeRestController {
	
	private final TaskTypeService taskTypeService;

    @GetMapping("/task-types")
    @ApiResponse(code = 200, message = "Success", response = TaskTypeDTO.class)
    @ApiOperation(value = "Get all task types", notes = "Get all task types")
    public List<TaskTypeDTO> getAll() {
        return taskTypeService.findAll();
    }
}

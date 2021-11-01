package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.TaskTypeDTO;

public class TaskTypeServiceTest extends VirtuaulaApplicationTests{

    @Autowired
    private TaskTypeService taskTypeService;
    
    @Test
    public void getAllWithTaskTypeReturnNotEmptyList() {
        int expected = 1;
        createOneTaskType();
        List<TaskTypeDTO> result = taskTypeService.findAll();
        assertEquals(expected, result.size());
    }
}

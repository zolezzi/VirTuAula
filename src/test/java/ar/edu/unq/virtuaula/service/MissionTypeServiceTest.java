package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.MissionTypeDTO;

public class MissionTypeServiceTest extends VirtuaulaApplicationTests{

    @Autowired
    private MissionTypeService missionTypeService;
    
    @Test
    public void getAllWithMissionTypeReturnNotEmptyList() {
        int expected = 1;
        createOneMissionType();
        List<MissionTypeDTO> result = missionTypeService.findAll();
        assertEquals(expected, result.size());
    }
}

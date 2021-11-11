package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.MissionTypeDTO;
import ar.edu.unq.virtuaula.service.MissionTypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MissionTypeRestController {
	
	private final MissionTypeService missionTypeService;

    @GetMapping("/mission-types")
    @ApiResponse(code = 200, message = "Success", response = MissionTypeDTO.class)
    @ApiOperation(value = "Get all mission types", notes = "Get all mission types")
    public List<MissionTypeDTO> getAll() {
        return missionTypeService.findAll();
    }
}

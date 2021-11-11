package ar.edu.unq.virtuaula.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.MissionTypeDTO;
import ar.edu.unq.virtuaula.model.MissionType;
import ar.edu.unq.virtuaula.repository.MissionTypeRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionTypeService {

    private final MissionTypeRepository missionTypeRepository;
    private final MapperUtil mapperUtil;
	
    public List<MissionTypeDTO> findAll() {
        List<MissionType> missionTypes = missionTypeRepository.findAll();
        return Arrays.asList(mapperUtil.getMapper().map(missionTypes, MissionTypeDTO[].class));
	}
}

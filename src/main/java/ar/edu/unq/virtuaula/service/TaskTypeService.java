package ar.edu.unq.virtuaula.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.TaskTypeDTO;
import ar.edu.unq.virtuaula.model.TaskType;
import ar.edu.unq.virtuaula.repository.TaskTypeRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskTypeService {

    private final TaskTypeRepository taskTypeRepository;
    private final MapperUtil mapperUtil;
	
    public List<TaskTypeDTO> findAll() {
        List<TaskType> taskTypes = taskTypeRepository.findAll();
        return Arrays.asList(mapperUtil.getMapper().map(taskTypes, TaskTypeDTO[].class));
	}
}

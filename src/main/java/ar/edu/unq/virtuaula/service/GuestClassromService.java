package ar.edu.unq.virtuaula.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.repository.GuestClassromRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;

@Service
public class GuestClassromService {

	@Autowired
	private GuestClassromRepository repository;
	
    @Autowired
    private MapperUtil mapperUtil;
	
	public List<ClassroomDTO> getAll() {
        List<Classroom> classrooms =  repository.findAll();
        return Arrays.asList(mapperUtil.getMapper().map(classrooms, ClassroomDTO.class));
	}
}

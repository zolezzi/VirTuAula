package ar.edu.unq.virtuaula.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.model.Classroom;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;

    public MapperUtil() {
        this.modelMapper = new ModelMapper();
        this.configure();
    }
    
    public ModelMapper getMapper() {
        return this.modelMapper;
    }

    private void configure() {
        // ClassroomDTO config
        this.modelMapper.typeMap(Classroom.class, ClassroomDTO.class).addMappings(mapper -> {
        	mapper.map(Classroom::getId, ClassroomDTO::setId);
        	mapper.map(Classroom::getName, ClassroomDTO::setName);
        });
    }
}

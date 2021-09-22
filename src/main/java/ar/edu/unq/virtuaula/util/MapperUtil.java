package ar.edu.unq.virtuaula.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.vo.TaskVO;

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
        // TaskDTO config
        this.modelMapper.typeMap(Task.class, TaskDTO.class).addMappings(mapper -> {
            mapper.map(Task::getId, TaskDTO::setId);
            mapper.map(Task::getStatement, TaskDTO::setStatement);
            mapper.map(Task::getAnswer, TaskDTO::setAnswer);
        });
        
        // TaskVO config
        this.modelMapper.typeMap(Task.class, TaskVO.class).addMappings(mapper -> {
            mapper.map(Task::getId, TaskVO::setId);
            mapper.map(Task::getAnswer, TaskVO::setAnswerId);
        });
    }
}

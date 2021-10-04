package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.repository.ClassroomRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classromRepository;
    private final MapperUtil mapperUtil;

    public List<ClassroomDTO> getAll() {
        List<Classroom> classrooms = classromRepository.findAll();
        return transformToDTO(classrooms);
    }
    
    public List<ClassroomDTO> findByAccount(Account account) {
        List<Classroom> classrooms = account.getClassrooms();
        return transformToDTO(classrooms);
    }

    public Classroom findById(Long id) {
        return classromRepository.findById(id).get();
    }

    private List<ClassroomDTO> transformToDTO(List<Classroom> classrooms) {
        return classrooms.stream().map(classroom -> {
            ClassroomDTO classroomDTO = mapperUtil.getMapper().map(classroom, ClassroomDTO.class);
            classroomDTO.setProgress(classroom.progress());
            return classroomDTO;
        }).collect(toList());
    }
}

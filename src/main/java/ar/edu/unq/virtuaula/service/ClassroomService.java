package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.repository.ClassroomRepository;
import ar.edu.unq.virtuaula.repository.StudentTaskRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classromRepository;
    private final StudentTaskRepository studentTaskRepository;
    private final MapperUtil mapperUtil;

    public List<ClassroomDTO> getAll() {
        List<Classroom> classrooms = classromRepository.findAll();
        return transformToClassroomDTO(classrooms);
    }
    
    private List<ClassroomDTO> transformToClassroomDTO(List<Classroom> classrooms) {
        return classrooms.stream().map(classroom -> {
            ClassroomDTO classroomDTO = mapperUtil.getMapper().map(classroom, ClassroomDTO.class);
            return classroomDTO;
        }).collect(toList());
	}

	public List<ClassroomDTO> findByAccount(Account account) {
        List<Classroom> classrooms = account.getClassrooms();
        return transformToDTO(classrooms, account.getId());
    }

    public Classroom findById(Long id) {
        return classromRepository.findById(id).get();
    }

    private List<ClassroomDTO> transformToDTO(List<Classroom> classrooms, Long accountId) {
        return classrooms.stream().map(classroom -> {
            ClassroomDTO classroomDTO = mapperUtil.getMapper().map(classroom, ClassroomDTO.class);
            classroomDTO.setProgress(calculateProgress(classroom, accountId));
            return classroomDTO;
        }).collect(toList());
    }

	public ClassroomDTO create(TeacherAccount teacherAccount, ClassroomDTO classroomDTO) {
		Classroom newClassroom = mapperUtil.getMapper().map(classroomDTO, Classroom.class);
		teacherAccount.getClassrooms().add(newClassroom);
		newClassroom = classromRepository.save(newClassroom);
		return mapperUtil.getMapper().map(newClassroom, ClassroomDTO.class);
	}
	
	private int calculateProgress(Classroom classroom, Long accountId) {
        int completed = classroom.getLessons().stream()
        		.mapToInt(lesson -> lesson.progress(studentTaskRepository.findByLessonAndStudent(lesson.getId(), accountId)))
        		.sum();
        return classroom.getLessons().isEmpty() ? 0 : completed / classroom.getLessons().size();
	}

}

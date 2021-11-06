package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.repository.ClassroomRepository;
import ar.edu.unq.virtuaula.util.CalculatedProgressUtil;
import ar.edu.unq.virtuaula.util.MapperUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classromRepository;
    private final AccountService accountService;
    private final TeamService teamService;
    private final MapperUtil mapperUtil;
    private final CalculatedProgressUtil progressUtil;

    public List<ClassroomDTO> getAll() {
        List<Classroom> classrooms = classromRepository.findAll();
        return transformToClassroomDTO(classrooms);
    }

	public List<ClassroomDTO> findByAccount(Account account) {
        List<Classroom> classrooms = account.getClassrooms();
        return transformToDTO(classrooms, account.getId());
    }

    public Classroom findById(Long id) {
        return classromRepository.findById(id).get();
    }

	public ClassroomDTO create(TeacherAccount teacherAccount, ClassroomDTO classroomDTO, List<AccountDTO> studentsDtos) {
		final Classroom newClassroom = mapperUtil.getMapper().map(classroomDTO, Classroom.class);
		List<StudentAccount> students = accountService.findAllStudentByIds(studentsDtos.stream().map(student -> student.getAccountId()).collect(Collectors.toList()));
		Classroom classroomBD = classromRepository.save(newClassroom);
		teacherAccount.getClassrooms().add(classroomBD);
		students.stream().forEach(student -> student.getClassrooms().add(classroomBD));
		teamService.createTeam(classroomBD, teacherAccount, students);
        return mapperUtil.getMapper().map(classroomBD, ClassroomDTO.class);
	}
	
    private List<ClassroomDTO> transformToClassroomDTO(List<Classroom> classrooms) {
        return classrooms.stream().map(classroom -> {
            ClassroomDTO classroomDTO = mapperUtil.getMapper().map(classroom, ClassroomDTO.class);
            return classroomDTO;
        }).collect(toList());
	}

    private List<ClassroomDTO> transformToDTO(List<Classroom> classrooms, Long accountId) {
        return classrooms.stream().map(classroom -> {
            ClassroomDTO classroomDTO = mapperUtil.getMapper().map(classroom, ClassroomDTO.class);
            classroomDTO.setProgress(progressUtil.getProgress(classroom, accountId));
            return classroomDTO;
        }).collect(toList());
    }
}

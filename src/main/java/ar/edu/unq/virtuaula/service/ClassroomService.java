package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.exception.ClassroomNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.StudentTask;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.repository.ClassroomRepository;
import ar.edu.unq.virtuaula.repository.StudentTaskRepository;
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
    private final StudentTaskRepository studentTaskRepository;
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

	public ClassroomDTO create(TeacherAccount teacherAccount, ClassroomDTO classroomDTO, List<Long> ids) {
		final Classroom newClassroom = mapperUtil.getMapper().map(classroomDTO, Classroom.class);
		List<StudentAccount> students = accountService.findAllStudentByIds(ids);
		//Agregar validacion de student valido para un teacher
		Classroom classroomBD = classromRepository.save(newClassroom);
		teacherAccount.getClassrooms().add(classroomBD);
		students.stream().forEach(student -> student.getClassrooms().add(classroomBD));
		teamService.createTeam(classroomBD, teacherAccount, students);
        return mapperUtil.getMapper().map(classroomBD, ClassroomDTO.class);
	}
	

	public ResponseMessage assign(TeacherAccount findTeacherById, Long classroomId, List<Long> ids) throws ClassroomNotFoundException {
		Classroom classroomBD = classromRepository.findById(classroomId)
				.orElseThrow(() -> new ClassroomNotFoundException("Error not found classroom id: " + classroomId));
		List<StudentAccount> students = accountService.findAllStudentByIds(ids);
		return createAllStudentTask(classroomBD, students);
	}
	
    private ResponseMessage createAllStudentTask(Classroom classroomBD, List<StudentAccount> students) {
    	classroomBD.getLessons().stream()
		.forEach(lesson -> createStudentTaskAllByStudent(lesson, students));
		return new ResponseMessage("the assignment to the classroom was successful");
	}

	private void createStudentTaskAllByStudent(Lesson lesson, List<StudentAccount> students) {
		students.stream()
		.forEach(student -> createStudentTaskByLessonAndStudent(lesson, student));
	}
	
	private void createStudentTaskByLessonAndStudent(Lesson newLesson, StudentAccount student) {
		List<StudentTask> listStudentTasks = new ArrayList<>();
		for(Task task : newLesson.getTasks()) {
			StudentTask studentTask = new StudentTask();
			studentTask.uncomplete();
			studentTask.setTask(task);
			studentTask.setLesson(newLesson);
			studentTask.setStudentAccount(student);
			listStudentTasks.add(studentTask);
		}
		studentTaskRepository.saveAll(listStudentTasks);
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

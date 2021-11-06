package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.exception.ClassroomNotFoundException;
import ar.edu.unq.virtuaula.exception.LessonNotFoundException;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.StudentTask;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.repository.LessonRepository;
import ar.edu.unq.virtuaula.repository.StudentTaskRepository;
import ar.edu.unq.virtuaula.repository.TaskTypeRepository;
import ar.edu.unq.virtuaula.util.ExperienceUtil;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.LessonVO;
import ar.edu.unq.virtuaula.vo.TaskVO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final StudentTaskRepository studentTaskRepository;
    private final TaskTypeRepository taskTypeRepository;
    private final MapperUtil mapperUtil;
    private final LevelService levelService;
    private final ManagementBufferService bufferService; 
    private final static int FULL_PROGRESS = 100;

    public List<LessonVO> getAllByClassroom(Classroom classroom) {
        List<Lesson> lessons = classroom.getLessons();
        return transformToLessonVO(lessons, classroom.getId());
    }
    
	public List<LessonVO> getAllByClassroomAndStudent(Classroom classroom, StudentAccount studentAccount) {
		List<Lesson> lessons = classroom.getLessons();
		return transformToVO(lessons, classroom.getId(), studentAccount);
	}

    public Lesson findById(Long lessonId) {
        return lessonRepository.findById(lessonId).get();
    }

    public LessonVO completeTasks(Classroom classroom, Long lessonId, StudentAccount studentAccount, List<TaskVO> tasks) throws LessonNotFoundException {
       	Date date = new Date();
    	Lesson lessonBD = lessonRepository.findById(lessonId)
    			.orElseThrow(() -> new LessonNotFoundException("Error not found lesson with id: " + lessonId));
    	
    	if(!classroom.containsLesson(lessonBD.getId())) {
    		throw new LessonNotFoundException("Error not found lesson id: " + lessonId + " for classroom id: " + classroom.getId());
    	}
    	System.out.print("date: " + lessonBD.getDeliveryDate());
    	if(!date.before(lessonBD.getDeliveryDate())){
    		throw new LessonNotFoundException("Expired that lesson id: " + lessonId + " for classroom id: " + classroom.getId());
    	}
        completeState(tasks, studentAccount.getId());
        LessonVO lessonVO = createLessonVO(lessonBD, studentAccount.getId());
        bufferService.ApplyBufferInStudentAccount(studentAccount.getLevel(), studentAccount, lessonVO.getNote());
        if(ExperienceUtil.isChangeLevel(studentAccount.getLevel().getMaxValue(), studentAccount.getExperience())) {
        	studentAccount.setLevel(levelService.getNextLevel(studentAccount.getLevel()));
        }
        return lessonVO;
    }

    public LessonDTO create(Classroom classroom, TeacherAccount teacherUser, LessonDTO lesson) throws Exception {
        Lesson newLesson = mapperLesson(lesson);
        if (!teacherUser.containsClassroom(classroom)) {
            throw new ClassroomNotFoundException("Error not found classroom with id: " + classroom.getId());
        }
        newLesson = lessonRepository.save(newLesson);
        classroom.addLesson(newLesson);
        newLesson.getTasks().forEach(task -> task.setCorrectAnswer(task.findCorrectAnswer()));
        createStudentTaskForAllStudent(newLesson, teacherUser);
        newLesson = lessonRepository.save(newLesson);
        return mapperUtil.getMapper().map(newLesson, LessonDTO.class);
    }

    private Lesson mapperLesson(LessonDTO lessonDto) {
    	List<Task> listTasks = convertTask(lessonDto.getTasks());
    	Lesson lesson = mapperUtil.getMapper().map(lessonDto, Lesson.class);
    	lesson.setTasks(listTasks);
    	return lesson;
	}

	private List<Task> convertTask(List<TaskDTO> listTaskDTOs) {
		return listTaskDTOs.stream().map(taskDTO -> {
			 Task task = mapperUtil.getMapper().map(taskDTO, Task.class);
			 task.setTaskType(taskTypeRepository.findById(taskDTO.getTaskTypeId()).get());
			 return task;
		}).collect(Collectors.toList());
	}

	private void createStudentTaskForAllStudent(Lesson newLesson, TeacherAccount teacher) {
		teacher.getStudents().forEach(student -> createStudentTaskForAllByLesson(newLesson, student));
	}

	private void createStudentTaskForAllByLesson(Lesson newLesson, StudentAccount student) {
		List<StudentTask> listStudentTasks = newLesson.getTasks().stream().map(task -> {
			StudentTask studentTask = new StudentTask();
			studentTask.uncomplete();
			studentTask.setTask(task);
			studentTask.setLesson(newLesson);
			studentTask.setStudentAccount(student);
			return studentTask;
		}).collect(toList());
		student.getResultsTasks().addAll(listStudentTasks);
	}
	
	private List<LessonVO> transformToLessonVO(List<Lesson> lessons, Long classroomId) {
        return lessons.stream().map(lesson -> {
            LessonVO lessonVO = mapperUtil.getMapper().map(lesson, LessonVO.class);
            lessonVO.setNote(null);
            lessonVO.setClassroomId(classroomId);
            return lessonVO;
        }).collect(toList());
    }

	private List<LessonVO> transformToVO(List<Lesson> lessons, Long classroomId, StudentAccount studentAccount) {
        return lessons.stream().map(lesson -> {
            LessonVO lessonVO = mapperUtil.getMapper().map(lesson, LessonVO.class);
            List<StudentTask> tasksBD = studentTaskRepository.findByLessonAndStudent(lesson.getId(), studentAccount.getId());
            lessonVO.setNote(null);
            int progress = lesson.progress(tasksBD);
            lessonVO.setProgress(progress);
            lessonVO.setClassroomId(classroomId);
            if (progress == FULL_PROGRESS) {
                lessonVO.setNote(lesson.qualification(tasksBD));
            }
            return lessonVO;
        }).collect(toList());
    }

    private LessonVO createLessonVO(Lesson lesson, Long studentId) {
        LessonVO lessonVO = mapperUtil.getMapper().map(lesson, LessonVO.class);
        List<StudentTask> tasksBD = studentTaskRepository.findByLessonAndStudent(lesson.getId(), studentId);
        lessonVO.setNote(null);
        int progress = lesson.progress(tasksBD);
        lessonVO.setProgress(progress);
        if (progress == FULL_PROGRESS) {
            lessonVO.setNote(lesson.qualification(tasksBD));
        }
        return lessonVO;
    }

    private void completeState(List<TaskVO> tasks, Long studentId) {
        tasks.stream().forEach(task -> {
            StudentTask studentTaskSolvedBD = studentTaskRepository.findByTaskIdAndStudentId(task.getId(), studentId)
            		.orElseThrow(() -> new NoSuchElementException("Error not found Tasks with id: " + task.getId()));
            studentTaskSolvedBD.setAnswer(task.getAnswerId());
            studentTaskSolvedBD.complete();
            studentTaskRepository.save(studentTaskSolvedBD);
        });
    }

}

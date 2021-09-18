package ar.edu.unq.virtuaula.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.repository.GuestLessonRepository;
import ar.edu.unq.virtuaula.repository.TaskRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.LessonVO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestLessonService {
    
    private final GuestLessonRepository guestLessonRepository;
    private final TaskRepository taskRepository;
    private final MapperUtil mapperUtil;

    public List<LessonDTO> getAllByClassroom(Classroom classroom) {
        List<Lesson> lessons = guestLessonRepository.findByClassroom(classroom);
        return Arrays.asList(mapperUtil.getMapper().map(lessons, LessonDTO[].class));
    }

	public LessonVO completeTask(Classroom classroom, Lesson lesson, Task task) {
		
		return null;
	}

	public List<TaskDTO> getAllTaskByLesson(Lesson lesson) {
		List<Task> tasks = taskRepository.findByLesson(lesson);
		return Arrays.asList(mapperUtil.getMapper().map(tasks, TaskDTO[].class));
	}

	public Lesson findById(Long lessonId) {
		return guestLessonRepository.findById(lessonId).get();
	}

}

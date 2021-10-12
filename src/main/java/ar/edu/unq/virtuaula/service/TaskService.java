package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.exception.LessonNotFoundException;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.OptionTask;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.repository.TaskRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.OptionTaskVO;
import ar.edu.unq.virtuaula.vo.TaskStudentVO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final MapperUtil mapperUtil;

    public List<TaskDTO> getAllTaskByLesson(Lesson lesson, TeacherAccount teacherAccount) throws LessonNotFoundException {
    	if(!teacherAccount.containsLesson(lesson)) {
    		throw new LessonNotFoundException("Not found lesson id: " + lesson.getId() + " for teacher account id: " + teacherAccount.getId());
    	}
        List<Task> tasks = taskRepository.findByLesson(lesson);
        return Arrays.asList(mapperUtil.getMapper().map(tasks, TaskDTO[].class));
    }

	public List<TaskStudentVO> getAllTaskByLessonForStudent(Lesson lesson) {
		List<Task> tasks = taskRepository.findByLesson(lesson);
		return transformToVO(tasks);
	}
	
    private List<TaskStudentVO> transformToVO(List<Task> tasks) {
        return tasks.stream().map(task -> {
        	TaskStudentVO taskVO = new TaskStudentVO();
        	taskVO.setId(task.getId());
        	taskVO.setStatement(task.getStatement());
        	taskVO.setScore(task.getScore());
        	taskVO.setAnswer(task.getAnswer());
        	taskVO.setOptions(transformOptionTaskToVO(task.getOptions()));
            return taskVO;
        }).collect(toList());
    }

	private List<OptionTaskVO> transformOptionTaskToVO(List<OptionTask> options) {
		return options.stream().map(option -> {
			OptionTaskVO optionVO = new OptionTaskVO();
			optionVO.setId(option.getId());
			optionVO.setResponseValue(option.getResponseValue());
            return optionVO;
        }).collect(toList());
	}
}

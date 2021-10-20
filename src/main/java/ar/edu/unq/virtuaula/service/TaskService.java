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
import ar.edu.unq.virtuaula.model.StudentTask;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.repository.StudentTaskRepository;
import ar.edu.unq.virtuaula.repository.TaskRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.OptionTaskVO;
import ar.edu.unq.virtuaula.vo.TaskStudentVO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final StudentTaskRepository studentTaskRepository;
    private final MapperUtil mapperUtil;

    public List<TaskDTO> getAllTaskByLesson(Lesson lesson, TeacherAccount teacherAccount) throws LessonNotFoundException {
        if (!teacherAccount.containsLesson(lesson)) {
            throw new LessonNotFoundException("Not found lesson id: " + lesson.getId() + " for teacher account id: " + teacherAccount.getId());
        }
        List<Task> tasks = taskRepository.findByLesson(lesson);
        return Arrays.asList(mapperUtil.getMapper().map(tasks, TaskDTO[].class));
    }

    public List<TaskStudentVO> getAllTaskByLessonForStudent(Lesson lesson, Long studentId) {
        List<Task> tasks = taskRepository.findByLesson(lesson);
        return transformToVO(tasks, studentId);
    }

    private List<TaskStudentVO> transformToVO(List<Task> tasks, Long studentId) {
        return tasks.stream().map(task -> {
            Optional<StudentTask> studentTask = studentTaskRepository.findByTaskIdAndStudentId(task.getId(), studentId);
            TaskStudentVO taskVO = new TaskStudentVO();
            taskVO.setId(task.getId());
            taskVO.setStatement(task.getStatement());
            taskVO.setScore(task.getScore());
            if (studentTask.isPresent()) {
                taskVO.setAnswer(studentTask.get().getAnswer());
            } else {
                taskVO.setAnswer(task.getAnswer());
            }
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

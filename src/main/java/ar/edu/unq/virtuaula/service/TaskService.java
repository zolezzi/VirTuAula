package ar.edu.unq.virtuaula.service;

import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.repository.TaskRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final MapperUtil mapperUtil;
    
    public List<TaskDTO> getAllTaskByLesson(Lesson lesson) {
        List<Task> tasks = taskRepository.findByLesson(lesson);
        return Arrays.asList(mapperUtil.getMapper().map(tasks, TaskDTO[].class));
    }
}

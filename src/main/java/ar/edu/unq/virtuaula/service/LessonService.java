package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.repository.LessonRepository;
import ar.edu.unq.virtuaula.repository.TaskRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.LessonVO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final TaskRepository taskRepository;
    private final MapperUtil mapperUtil;

    public List<LessonDTO> getAllByClassroom(Classroom classroom) {
        List<Lesson> lessons = lessonRepository.findByClassroom(classroom);
        return transformToDTO(lessons, classroom.getId());
    }

    public Lesson findById(Long lessonId) {
        return lessonRepository.findById(lessonId).get();
    }

    public LessonVO completeTasks(Classroom classroom, Lesson lesson, List<Task> tasks) {
        completeState(tasks);
        return createLessonVO(lesson);
    }
    
    private List<LessonDTO> transformToDTO(List<Lesson> lessons, Long classroomId) {
        return lessons.stream().map(lesson -> {
           LessonDTO lessonDTO = mapperUtil.getMapper().map(lesson, LessonDTO.class);
           lessonDTO.setProgress(lesson.progress());
           lessonDTO.setClassroomId(classroomId);
           return lessonDTO;
        }).collect(toList());
    }

    private LessonVO createLessonVO(Lesson lesson) {
        Lesson lessonActual = lessonRepository.getById(lesson.getId());
        LessonVO lessonVO = mapperUtil.getMapper().map(lesson, LessonVO.class);
        lessonVO.setProgress(lessonActual.progress());
        return lessonVO;
    }

    private void completeState(List<Task> tasks) {
        tasks.stream().forEach(task -> {
        	task.complete();
            taskRepository.save(task);
        });
    }

}

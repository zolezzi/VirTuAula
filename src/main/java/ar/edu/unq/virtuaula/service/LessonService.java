package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.repository.LessonRepository;
import ar.edu.unq.virtuaula.repository.TaskRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.LessonVO;
import ar.edu.unq.virtuaula.vo.TaskVO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final TaskRepository taskRepository;
    private final MapperUtil mapperUtil;

    public List<LessonVO> getAllByClassroom(Classroom classroom) {
        List<Lesson> lessons = lessonRepository.findByClassroom(classroom);
        return transformToVO(lessons, classroom.getId());
    }

    public Lesson findById(Long lessonId) {
        return lessonRepository.findById(lessonId).get();
    }

    public LessonVO completeTasks(Classroom classroom, Lesson lesson, List<TaskVO> tasks) {
        completeState(tasks);
        return createLessonVO(lesson);
    }

    private List<LessonVO> transformToVO(List<Lesson> lessons, Long classroomId) {
        return lessons.stream().map(lesson -> {
        	LessonVO lessonVO = mapperUtil.getMapper().map(lesson, LessonVO.class);
        	lessonVO.setProgress(lesson.progress());
        	lessonVO.setClassroomId(classroomId);
        	if(lesson.progress() == 100) {
        		lessonVO.setNote(lesson.qualification());
        	}
            return lessonVO;
        }).collect(toList());
    }

    private LessonVO createLessonVO(Lesson lesson) {
        Lesson lessonActual = lessonRepository.getById(lesson.getId());
        LessonVO lessonVO = mapperUtil.getMapper().map(lesson, LessonVO.class);
        lessonVO.setProgress(lessonActual.progress());
    	if(lessonActual.progress() == 100) {
    		lessonVO.setNote(lessonActual.qualification());
    	}
        return lessonVO;
    }

    private void completeState(List<TaskVO> tasks) {
        tasks.stream().forEach(task -> {
            Task tasksBD;
            try {
                tasksBD = taskRepository.findById(task.getId()).orElseThrow(() -> new Exception("Error not found Tasks with id: " + task.getId()));
                tasksBD.setAnswer(task.getAnswerId());
                tasksBD.complete();
                taskRepository.save(tasksBD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}

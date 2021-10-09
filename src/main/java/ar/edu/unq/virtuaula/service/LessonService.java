package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.exception.ClassroomNotFoundException;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.model.TeacherAccount;
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
    private final static int FULL_PROGRESS = 100;

    public List<LessonVO> getAllByClassroom(Classroom classroom) {
        List<Lesson> lessons = classroom.getLessons();
        return transformToVO(lessons, classroom.getId());
    }

    public Lesson findById(Long lessonId) {
        return lessonRepository.findById(lessonId).get();
    }

    public LessonVO completeTasks(Classroom classroom, Long lessonId, List<TaskVO> tasks) {
        Lesson lesson = classroom.getLessons().stream().filter(les -> les.getId().equals(lessonId)).findFirst().get();
        completeState(tasks);
        return createLessonVO(lesson);
    }

    public LessonDTO create(Classroom classroom, TeacherAccount teacherUser, LessonDTO lesson) throws Exception {
        Lesson newLesson = mapperUtil.getMapper().map(lesson, Lesson.class);
        if (!teacherUser.containsClassroom(classroom)) {
            throw new ClassroomNotFoundException("Error not found classroom with id: " + classroom.getId());
        }
        newLesson.getTasks().forEach(task -> {
            task.uncomplete();
        });
        newLesson = lessonRepository.save(newLesson);
        newLesson.getTasks().forEach(task -> task.setCorrectAnswer(task.findCorrectAnswer()));
        newLesson = lessonRepository.save(newLesson);
        return mapperUtil.getMapper().map(newLesson, LessonDTO.class);
    }

    private List<LessonVO> transformToVO(List<Lesson> lessons, Long classroomId) {
        return lessons.stream().map(lesson -> {
            LessonVO lessonVO = mapperUtil.getMapper().map(lesson, LessonVO.class);
            lessonVO.setNote(null);
            lessonVO.setProgress(lesson.progress());
            lessonVO.setClassroomId(classroomId);
            if (lesson.progress() == FULL_PROGRESS) {
                lessonVO.setNote(lesson.qualification());
            }
            return lessonVO;
        }).collect(toList());
    }

    private LessonVO createLessonVO(Lesson lesson) {
        LessonVO lessonVO = mapperUtil.getMapper().map(lesson, LessonVO.class);
        lessonVO.setNote(null);
        lessonVO.setProgress(lesson.progress());
        if (lesson.progress() == FULL_PROGRESS) {
            lessonVO.setNote(lesson.qualification());
        }
        return lessonVO;
    }

    private void completeState(List<TaskVO> tasks) {
        tasks.stream().forEach(task -> {
            Task tasksBD = taskRepository.findById(task.getId()).orElseThrow(() -> new NoSuchElementException("Error not found Tasks with id: " + task.getId()));
            tasksBD.setAnswer(task.getAnswerId());
            tasksBD.complete();
            taskRepository.save(tasksBD);
        });
    }

}

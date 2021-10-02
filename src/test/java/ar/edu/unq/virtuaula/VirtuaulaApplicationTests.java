package ar.edu.unq.virtuaula;

import ar.edu.unq.virtuaula.builder.ClassroomBuilder;
import ar.edu.unq.virtuaula.builder.LessonBuilder;
import ar.edu.unq.virtuaula.builder.TaskBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.repository.ClassroomRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VirtuaulaApplicationTests {

    @Autowired
    protected ClassroomRepository classroomRepository;

    protected Classroom createOneClassroom() {
        Task task = TaskBuilder.taskWithStatement("Cuanto vale x para x = x * 2 + 1").completed().withCorrectAnswer(1l).withAnswer(1l).build();
        Lesson lesson = LessonBuilder.lessonWithName("Ecuaciones").withTask(task).build();
        Classroom classroom = ClassroomBuilder.classroomWithName("Matematicas").withLesson(lesson).build();
        return createClassroom(classroom);
    }
    
    protected Classroom createOneClassroomWithTwoTasks() {
        Task task1 = TaskBuilder.taskWithStatement("Cuanto vale x para x = x * 2 + 1").uncompleted().withCorrectAnswer(1l).build();
        Task task2 = TaskBuilder.taskWithStatement("Cuanto vale x para x = x * 2 + 1").uncompleted().withCorrectAnswer(1l).build();
        Lesson lesson = LessonBuilder.lessonWithName("Ecuaciones").withTask(task1).withTask(task2).build();
        Classroom classroom = ClassroomBuilder.classroomWithName("Matematicas").withLesson(lesson).build();
        return createClassroom(classroom);
    }

    protected Classroom createClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }
}

package ar.edu.unq.virtuaula;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ar.edu.unq.virtuaula.builder.AccountTypeBuilder;
import ar.edu.unq.virtuaula.builder.ClassroomBuilder;
import ar.edu.unq.virtuaula.builder.LessonBuilder;
import ar.edu.unq.virtuaula.builder.TaskBuilder;
import ar.edu.unq.virtuaula.builder.TeacherAccountBuilder;
import ar.edu.unq.virtuaula.builder.UserBuilder;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.AccountType;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.repository.AccountRepository;
import ar.edu.unq.virtuaula.repository.ClassroomRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VirtuaulaApplicationTests {

    @Autowired
    protected ClassroomRepository classroomRepository;
    
    @Autowired
    protected AccountRepository accountRepository;
    
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
    
    protected TeacherAccount createTeacherAccount(TeacherAccount user) {
        return accountRepository.save(user);
    }
    
    protected User createOneUserWithTeacherAccount() {
        return createOneTeacherAccount().getUser();
    }
    
    protected Account createOneTeacherAccount() {
    	AccountType accountType= AccountTypeBuilder.accountTypeWithUsername("TEACHER").build();
    	User user = UserBuilder.userWithUsernameAndPassword("charlie", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4").build();
    	TeacherAccount account = TeacherAccountBuilder.accountWithUsername("charlie")
        		.accountWithFisrtName("Charlie")
        		.accountWithLastName("Cardozo")
        		.accountWithEmail("charlie@gmail.com")
        		.withAccountType(accountType)
        		.withUser(user)
        		.build();
        account = createTeacherAccount(account);
        return account;
    }
    
    protected Account createOneTeacherAccountWithClassroom(Classroom classroom) {
    	List<Classroom> classrooms = new ArrayList<>();
    	classrooms.add(classroom);
    	AccountType accountType= AccountTypeBuilder.accountTypeWithUsername("TEACHER").build();
    	User user = UserBuilder.userWithUsernameAndPassword("charlie", "1234").build();
    	TeacherAccount account = TeacherAccountBuilder.accountWithUsername("charlie")
        		.accountWithFisrtName("Charlie")
        		.accountWithLastName("Cardozo")
        		.accountWithEmail("charlie@gmail.com")
        		.withAccountType(accountType)
        		.withUser(user)
        		.withClassroom(classrooms)
        		.build();
        account = createTeacherAccount(account);
        return account;
    }
}

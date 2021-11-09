package ar.edu.unq.virtuaula;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ar.edu.unq.virtuaula.builder.AccountTypeBuilder;
import ar.edu.unq.virtuaula.builder.BufferExperienceBuilder;
import ar.edu.unq.virtuaula.builder.BufferLifeBuilder;
import ar.edu.unq.virtuaula.builder.ClassroomBuilder;
import ar.edu.unq.virtuaula.builder.LessonBuilder;
import ar.edu.unq.virtuaula.builder.LevelBuilder;
import ar.edu.unq.virtuaula.builder.OptionTaskBuilder;
import ar.edu.unq.virtuaula.builder.StudentAccountBuilder;
import ar.edu.unq.virtuaula.builder.StudentTaskBuilder;
import ar.edu.unq.virtuaula.builder.TaskBuilder;
import ar.edu.unq.virtuaula.builder.TaskTypeBuilder;
import ar.edu.unq.virtuaula.builder.TeacherAccountBuilder;
import ar.edu.unq.virtuaula.builder.TeamBuilder;
import ar.edu.unq.virtuaula.builder.UserBuilder;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.AccountType;
import ar.edu.unq.virtuaula.model.Buffer;
import ar.edu.unq.virtuaula.model.BufferExperience;
import ar.edu.unq.virtuaula.model.BufferLife;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Level;
import ar.edu.unq.virtuaula.model.OptionTask;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.StudentTask;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.model.TaskType;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.Team;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.repository.AccountRepository;
import ar.edu.unq.virtuaula.repository.ClassroomRepository;
import ar.edu.unq.virtuaula.repository.LevelRepository;
import ar.edu.unq.virtuaula.repository.StudentTaskRepository;
import ar.edu.unq.virtuaula.repository.TaskTypeRepository;
import ar.edu.unq.virtuaula.repository.TeamRepository;
import ar.edu.unq.virtuaula.repository.UserRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VirtuaulaApplicationTests {

    @Autowired
    protected ClassroomRepository classroomRepository;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected StudentTaskRepository studentTaskRepository;
    
    @Autowired
    protected TaskTypeRepository taskTypeRepository;
    
    @Autowired
    protected UserRepository userRepository;
    
    @Autowired
    protected LevelRepository levelRepository;
    
    @Autowired
    protected TeamRepository teamRepository;

    protected Classroom createOneClassroom() {
    	TaskType taskType = TaskTypeBuilder.taskTypeWithName("Multiple choise").build();
        Task task = TaskBuilder.taskWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).withAnswer(1l).withScore(100d)
        		.withTaskType(taskType)
        		.build();
		Date date = getDate();
		Lesson lesson = LessonBuilder.lessonWithName("Ecuaciones").withTask(task).withMaxNote(1000).withDeliveryDate(date).build();
		Classroom classroom = ClassroomBuilder.classroomWithName("Matematicas").withLesson(lesson).build();
		return createClassroom(classroom);
    }
    
    protected TaskType  createOneTaskType() {
    	TaskType taskType = TaskTypeBuilder.taskTypeWithName("Multiple choise").build();
        return createTaskType(taskType);
    }
    
    protected Classroom createOneClassroomWithoutLesson() {
        Classroom classroom = ClassroomBuilder.classroomWithName("Matematicas").build();
        return createClassroom(classroom);
    }

    protected Classroom createOneClassroomWithTwoTasks() {
        Task task1 = TaskBuilder.taskWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).build();
        Task task2 = TaskBuilder.taskWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).build();
        Date date = getDate();
        Lesson lesson = LessonBuilder.lessonWithName("Ecuaciones").withTask(task1).withTask(task2).withDeliveryDate(date).build();
        Classroom classroom = ClassroomBuilder.classroomWithName("Matematicas").withLesson(lesson).build();
        return createClassroom(classroom);
    }

    protected Classroom createOneClassroomWithTwoTasksAndTwoOptionTasks() {
    	OptionTask option1 = OptionTaskBuilder.taskWithReponseValue("2").withIsCorrect(true).build();
    	OptionTask option2 = OptionTaskBuilder.taskWithReponseValue("1").withIsCorrect(false).build();
    	OptionTask option3 = OptionTaskBuilder.taskWithReponseValue("2").withIsCorrect(true).build();
    	OptionTask option4 = OptionTaskBuilder.taskWithReponseValue("1").withIsCorrect(false).build();
        Task task1 = TaskBuilder.taskWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).withOptionTask(option1).withOptionTask(option2).build();
        Task task2 = TaskBuilder.taskWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).withOptionTask(option3).withOptionTask(option4).build();
        Lesson lesson = LessonBuilder.lessonWithName("Ecuaciones").withTask(task1).withTask(task2).build();
        Classroom classroom = ClassroomBuilder.classroomWithName("Matematicas").withLesson(lesson).build();
        return createClassroom(classroom);
    }

    protected Classroom createClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    protected TeacherAccount createTeacherAccount(TeacherAccount account) {
        return accountRepository.save(account);
    }
    
    protected StudentAccount createStudentAccount(StudentAccount account) {
        return accountRepository.save(account);
    }
    
    protected User createOneUserWithTeacherAccount() {
        return createOneTeacherAccount().getUser();
    }

    protected User createUser(User user) {
        return userRepository.save(user);
    }

    protected TaskType createTaskType(TaskType taskType) {
        return taskTypeRepository.save(taskType);
    }
    
    protected Level createLevel(Level level) {
    	return levelRepository.save(level);
    }
    
    protected Team createTeam(Team team) {
    	return teamRepository.save(team);
    }
    
    protected Team createOneTeam(){
    	List<StudentAccount> students = new ArrayList<>();
    	Classroom classroom = createOneClassroom();
    	StudentAccount student = (StudentAccount) createOneStudentAccount();
    	TeacherAccount teacher = (TeacherAccount) createOneTeacherAccountWithClassroomAndStudent(classroom, student);
    	students.add(student);
    	Team team = TeamBuilder.teamWithName("Team de equipo de matemaica")
    			.withClassroom(classroom)
    			.withTeacher(teacher)
    			.withStudents(students)
    			.build();
    	return createTeam(team);
    }
    
    protected User createOneUser() {
        User user = UserBuilder.userWithUsernameAndPassword("charly2", "1234567n")
                .withEmail("charlie@gmail.com")
                .build();
        return createUser(user);
    }
    
	
	protected Level createLevelProfesional() {
		Level level = LevelBuilder.levelWithName("Profesional").withDescription("Nivel profesional").withImagePath("/images/image.png")
    			.withNameNextLevel(null)
    			.withNumberLevel(2)
    			.withMinValue(0d)
    			.withMaxValue(2000d)
    			.build();
		return createLevel(level);
	}
	
	protected Level createLevelBeginner() {
		Level level = LevelBuilder.levelWithName("Beginner").withDescription("Nivel principiante").withImagePath("/images/image.png")
    			.withNameNextLevel("Profesional")
    			.withNumberLevel(1)
    			.withMinValue(0d)
    			.withMaxValue(2000d)
    			.build();
		return createLevel(level);
	}
	
	protected Level createLevelWithTwoBuffer(){
		BufferExperience bufferExprience = BufferExperienceBuilder.bufferExperienceWithName("Buffer experience")
				.withDescription("Buffer suma experiencia")
				.withExperienceValue(10d)
				.withOperator("+")
				.build();
		
		BufferLife bufferlife = BufferLifeBuilder.bufferLifeWithName("Buffer life")
				.withDescription("Buffer suma life")
				.withLifeValue(1)
				.withOperator("+")
				.build();
		List<Buffer> buffers = new ArrayList<>();
		buffers.add(bufferExprience);
		buffers.add(bufferlife);
		
		Level level = LevelBuilder.levelWithName("Beginner").withDescription("Nivel principiante").withImagePath("/images/image.png")
    			.withNameNextLevel("Profesional")
    			.withNumberLevel(1)
    			.withMinValue(0d)
    			.withMaxValue(2000d)
    			.withBuffers(buffers)
    			.build();
		return createLevel(level);
	}

    protected Account createOneTeacherAccount() {
        AccountType accountType = AccountTypeBuilder.accountTypeWithUsername("TEACHER").build();
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
    
    protected Account createOneStudentAccount() {
    	AccountType accountType= AccountTypeBuilder.accountTypeWithUsername("STUDENT").build();
    	Level level = LevelBuilder.levelWithName("Principiante").withDescription("Nivel inicial").withImagePath("/images/image.png")
    			.withNameNextLevel("Aficionado")
    			.withNumberLevel(1)
    			.withMinValue(0d)
    			.withMaxValue(1000d)
    			.build();
    	User user = UserBuilder.userWithUsernameAndPassword("charlie2", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4").build();
    	StudentAccount account = StudentAccountBuilder.accountWithUsername("charlie2")
        		.accountWithFisrtName("Charlie2")
        		.accountWithLastName("Cardozo2")
        		.accountWithEmail("charlie2@gmail.com")
        		.withAccountType(accountType)
        		.withLevel(level)
        		.withUser(user)
        		.withLife(3)
        		.build();
        account = createStudentAccount(account);
        return account;
    }
    
    protected Account createOneStudentTasktWithLessonAndTaskAndStudentAccount(Lesson lesson, Task task, StudentAccount studentAccount) {
    	StudentTask studentTask = StudentTaskBuilder.createStudentTask()
    			.withLesson(lesson)
    			.withTask(task)
    			.withStudentAccount(studentAccount)
    			.completed()
    			.build();
    	studentTask = createStudentTask(studentTask);
        return studentTask.getStudentAccount();
    }
    
    protected Account createOneStudentTaskUncompletedtWithLessonAndTaskAndStudentAccount(Lesson lesson, Task task, StudentAccount studentAccount) {
    	StudentTask studentTask = StudentTaskBuilder.createStudentTask()
    			.withLesson(lesson)
    			.withTask(task)
    			.withStudentAccount(studentAccount)
    			.uncompleted()
    			.build();
    	studentTask = createStudentTask(studentTask);
        return studentTask.getStudentAccount();
    }
    
    private StudentTask createStudentTask(StudentTask studentTask) {
		return studentTaskRepository.save(studentTask);
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
	
	protected Account createOneStudentAccountWithClassroom(Classroom classroom) {
    	List<Classroom> classrooms = new ArrayList<>();
    	classrooms.add(classroom);
    	AccountType accountType= AccountTypeBuilder.accountTypeWithUsername("TEACHER").build();
    	User user = UserBuilder.userWithUsernameAndPassword("charlie", "1234").build();
    	StudentAccount account = StudentAccountBuilder.accountWithUsername("charlie")
        		.accountWithFisrtName("Charlie")
        		.accountWithLastName("Cardozo")
        		.accountWithEmail("charlie@gmail.com")
        		.withAccountType(accountType)
        		.withUser(user)
        		.withClassroom(classrooms)
        		.build();
        account = createStudentAccount(account);
        return account;
    }
	
	protected Account createOneTeacherAccountWithClassroomAndStudent(Classroom classroom, StudentAccount student) {
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
        		.addStudent(student)
        		.withClassroom(classrooms)
        		.build();
        account = createTeacherAccount(account);
        return account;
    }
	
	private Date getDate() {
		Date date = null;
		try {
			String dateString = "2021-11-13 23:59:59";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.parse(dateString + " UTC");
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}

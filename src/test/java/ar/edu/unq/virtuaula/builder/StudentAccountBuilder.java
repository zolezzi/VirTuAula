package ar.edu.unq.virtuaula.builder;

import java.util.List;

import ar.edu.unq.virtuaula.model.AccountType;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Level;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.StudentTask;
import ar.edu.unq.virtuaula.model.User;

public class StudentAccountBuilder {

	private final StudentAccount instance = new StudentAccount();
	
    public static StudentAccountBuilder accountWithUsername(String username) {
    	StudentAccountBuilder accountBuilder = new StudentAccountBuilder();
    	accountBuilder.instance.setUsername(username);
    	accountBuilder.instance.setExperience(0d);
        return accountBuilder;
    }

    public StudentAccountBuilder accountWithFisrtName(String fisrtName) {
    	this.instance.setFirstName(fisrtName);
        return this;
    }
    
    public StudentAccountBuilder accountWithLastName(String lastName) {
    	this.instance.setLastName(lastName);
        return this;
    }
    
    public StudentAccountBuilder accountWithEmail(String email) {
        this.instance.setEmail(email);
        return this;
    }
    
    public StudentAccountBuilder addStudentTask(StudentTask studentTask) {
        this.instance.getResultsTasks().add(studentTask);
        return this;
    }
    
    public StudentAccountBuilder withAccountType(AccountType accountType) {
        this.instance.setAccountType(accountType);
        return this;
    }
    
    public StudentAccountBuilder withUser(User user) {
        this.instance.setUser(user);
        return this;
    }
    
    public StudentAccountBuilder withLevel(Level level) {
        this.instance.setLevel(level);
        return this;
    }
    
    public StudentAccountBuilder withClassroom(List<Classroom> classrooms) {
        this.instance.setClassrooms(classrooms);
        return this;
    }

    public StudentAccount build() {
        return this.instance;
    }
}

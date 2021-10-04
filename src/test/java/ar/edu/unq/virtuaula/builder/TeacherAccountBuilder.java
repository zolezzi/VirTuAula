package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.AccountType;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.User;

public class TeacherAccountBuilder {

	private final TeacherAccount instance = new TeacherAccount();
	
    public static TeacherAccountBuilder accountWithUsername(String username) {
    	TeacherAccountBuilder accountBuilder = new TeacherAccountBuilder();
    	accountBuilder.instance.setUsername(username);
        return accountBuilder;
    }

    public TeacherAccountBuilder accountWithFisrtName(String fisrtName) {
    	this.instance.setFirstName(fisrtName);
        return this;
    }
    
    public TeacherAccountBuilder accountWithLastName(String lastName) {
    	this.instance.setLastName(lastName);
        return this;
    }
    public TeacherAccountBuilder accountWithEmail(String email) {
        this.instance.setEmail(email);
        return this;
    }
    
    public TeacherAccountBuilder withAccountType(AccountType accountType) {
        this.instance.setAccountType(accountType);
        return this;
    }
    
    public TeacherAccountBuilder withUser(User user) {
        this.instance.setUser(user);
        return this;
    }

    public TeacherAccount build() {
        return this.instance;
    }
}

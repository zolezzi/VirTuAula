package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.User;

public class UserBuilder {

	private final User instance = new User();
	
    public static UserBuilder userWithUsernameAndPassword(String username, String password) {
    	UserBuilder userBuilder = new UserBuilder();
    	userBuilder.instance.setUsername(username);
    	userBuilder.instance.setPassword(password);
        return userBuilder;
    }
    
    public UserBuilder withEmail(String email) {
        this.instance.setEmail(email);
        return this;
    }
    
    public UserBuilder withAccount(Account account) {
        this.instance.setAccount(account);
        return this;
    }

    public User build() {
        return this.instance;
    }
}

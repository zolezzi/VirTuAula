package ar.edu.unq.virtuaula.builder;

import java.util.List;

import ar.edu.unq.virtuaula.model.AccountType;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.model.User;

public class LeaderAccountBuilder {

	private final LeaderAccount instance = new LeaderAccount();
	
    public static LeaderAccountBuilder accountWithUsername(String username) {
    	LeaderAccountBuilder accountBuilder = new LeaderAccountBuilder();
    	accountBuilder.instance.setUsername(username);
        return accountBuilder;
    }

    public LeaderAccountBuilder accountWithFisrtName(String fisrtName) {
    	this.instance.setFirstName(fisrtName);
        return this;
    }
    
    public LeaderAccountBuilder accountWithLastName(String lastName) {
    	this.instance.setLastName(lastName);
        return this;
    }
    public LeaderAccountBuilder accountWithEmail(String email) {
        this.instance.setEmail(email);
        return this;
    }
    
    public LeaderAccountBuilder withAccountType(AccountType accountType) {
        this.instance.setAccountType(accountType);
        return this;
    }
    
    public LeaderAccountBuilder withUser(User user) {
        this.instance.setUser(user);
        return this;
    }
    
    public LeaderAccountBuilder addPlayer(PlayerAccount player) {
        this.instance.getPlayers().add(player);
        return this;
    }
    
    public LeaderAccountBuilder withNewGames(List<NewGame> newGames) {
        this.instance.setNewGames(newGames);
        return this;
    }

    public LeaderAccount build() {
        return this.instance;
    }
}
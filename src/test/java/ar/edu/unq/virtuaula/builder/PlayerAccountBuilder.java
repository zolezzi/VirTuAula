package ar.edu.unq.virtuaula.builder;

import java.util.List;

import ar.edu.unq.virtuaula.model.AccountType;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.Level;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.PlayerMission;
import ar.edu.unq.virtuaula.model.User;

public class PlayerAccountBuilder {

	private final PlayerAccount instance = new PlayerAccount();
	
    public static PlayerAccountBuilder accountWithUsername(String username) {
    	PlayerAccountBuilder accountBuilder = new PlayerAccountBuilder();
    	accountBuilder.instance.setUsername(username);
    	accountBuilder.instance.setExperience(0d);
        return accountBuilder;
    }

    public PlayerAccountBuilder accountWithFisrtName(String fisrtName) {
    	this.instance.setFirstName(fisrtName);
        return this;
    }
    
    public PlayerAccountBuilder accountWithLastName(String lastName) {
    	this.instance.setLastName(lastName);
        return this;
    }
    
    public PlayerAccountBuilder accountWithEmail(String email) {
        this.instance.setEmail(email);
        return this;
    }
    
    public PlayerAccountBuilder addPlayerMission(PlayerMission playerMission) {
        this.instance.getResultsMissions().add(playerMission);
        return this;
    }
    
    public PlayerAccountBuilder withAccountType(AccountType accountType) {
        this.instance.setAccountType(accountType);
        return this;
    }
    
    public PlayerAccountBuilder withUser(User user) {
        this.instance.setUser(user);
        return this;
    }
    
    public PlayerAccountBuilder withLevel(Level level) {
        this.instance.setLevel(level);
        return this;
    }
    public PlayerAccountBuilder withLife(Integer life) {
        this.instance.setLife(life);
        return this;
    }
    
    public PlayerAccountBuilder withNewGames(List<NewGame> newGames) {
        this.instance.setNewGames(newGames);
        return this;
    }

    public PlayerAccount build() {
        return this.instance;
    }
}

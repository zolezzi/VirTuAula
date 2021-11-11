package ar.edu.unq.virtuaula.builder;

import java.util.List;

import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.model.Team;

public class TeamBuilder {

	private final Team instance = new Team();
	
	public static TeamBuilder teamWithName(String name) {
		TeamBuilder teamBuilder = new TeamBuilder();
		teamBuilder.instance.setName(name);
		return teamBuilder;
	}
	
    public TeamBuilder withNewGame(NewGame newGame) {
        this.instance.setNewGame(newGame);
        return this;
    }
	
    public TeamBuilder withPlayers(List<PlayerAccount> players) {
        this.instance.setPlayers(players);
        return this;
    }
    
    public TeamBuilder withLeader(LeaderAccount leader) {
        this.instance.setLeader(leader);
        return this;
    }
    
    public Team build() {
        return this.instance;
    }
}

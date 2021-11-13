package ar.edu.unq.virtuaula.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue(value="PlayerAccount")
public class PlayerAccount extends Account {
	
	private static final long serialVersionUID = -6667231579400890583L;

    @ManyToMany(mappedBy = "players")
    private List<LeaderAccount> leaders = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "player_mission_id")
    private List<PlayerMission> resultsMissions = new ArrayList<>();
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "level_id", referencedColumnName = "id")
    private Level level;
    
    private Double experience;
    
    private Integer life;

	public Double calculateExperience(Double experienceActual, Double experienceCalculated) {
		return experienceActual == null || experienceCalculated == null 
				? this.experience : experienceActual + experienceCalculated;
	}

	public void addLeader(LeaderAccount leaderAccount) {
		if(!this.getLeaders().contains(leaderAccount)) {
			this.getLeaders().add(leaderAccount);
		}
	}

	public Integer lifeSum(Integer life, Integer calculatedValue) {
		return life + calculatedValue;
	}
        
        public void addNewGame(NewGame newGame) {
            this.newGames.add(newGame);
        }
}

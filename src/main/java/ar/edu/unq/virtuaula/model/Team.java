package ar.edu.unq.virtuaula.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Team implements Serializable{

	private static final long serialVersionUID = 855656491450122981L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToOne
    @JoinColumn(name = "leader_id", referencedColumnName = "id")
    LeaderAccount leader;
    
    @OneToOne
    @JoinColumn(name = "new_game_id", referencedColumnName = "id")
    NewGame newGame;
    
    @ManyToMany
    @JoinTable(
            name = "teams_players",
            joinColumns = @JoinColumn(
                    name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "player_id", referencedColumnName = "id"))
    private List<PlayerAccount> players;
}

package ar.edu.unq.virtuaula.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue(value = "LeaderAccount")
public class LeaderAccount extends Account {

    private static final long serialVersionUID = -3455382936475619272L;

    @ManyToMany
    @JoinTable(
            name = "leaders_players",
            joinColumns = @JoinColumn(
                    name = "leader_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "player_id", referencedColumnName = "id"))
    private List<PlayerAccount> players = new ArrayList<>();

    public boolean containsNewGame(NewGame newGame) {
        return this.getNewGames().isEmpty() ? false : this.getNewGames()
                .stream()
                .map(newGameI -> newGameI.getId())
                .collect(Collectors.toList()).contains(newGame.getId());
    }

    public boolean containsCampaign(Campaign campaign) {
        return this.getNewGames().isEmpty() ? false : this.getNewGames()
                .stream()
                .map(newGameI -> newGameI.getCampaigns())
                .flatMap(campaignI -> campaignI.stream())
                .collect(Collectors.toList()).contains(campaign);
    }

	public List<PlayerAccount> getPlayersByDNIs(List<Integer> dnis) {
		return this.getPlayers().stream().filter(player -> existsPlayer(player,dnis)).collect(Collectors.toList());
	}

	private boolean existsPlayer(PlayerAccount player, List<Integer> dnis) {
		return dnis.contains(player.getDni());
	}
}

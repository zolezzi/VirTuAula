package ar.edu.unq.virtuaula.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.repository.PlayerMissionRepository;

@Component
public class CalculatedProgressUtil {

	@Autowired
	private PlayerMissionRepository playerMissionRepository;
	
    public Integer getProgress(NewGame newGame, Long accountId) {
    	return calculateProgress(newGame, accountId);
    }

	private int calculateProgress(NewGame newGame, Long accountId) {
        int completed = newGame.getCampaigns().stream()
        		.mapToInt(campaign -> campaign.progress(playerMissionRepository.findByCampaignAndPlayer(campaign.getId(), accountId)))
        		.sum();
        return newGame.getCampaigns().isEmpty() ? 0 : completed / newGame.getCampaigns().size();
	}
}

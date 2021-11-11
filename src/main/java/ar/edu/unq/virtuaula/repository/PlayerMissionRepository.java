package ar.edu.unq.virtuaula.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.PlayerMission;

public interface PlayerMissionRepository extends JpaRepository<PlayerMission, Long>{

	public List<PlayerMission> findByCampaign(Campaign campaign);

	@Query("SELECT pm FROM PlayerMission pm WHERE pm.mission.id = ?1 and pm.playerAccount.id = ?2")
	public Optional<PlayerMission> findByMissionIdAndPlayerId(Long id, Long playerId);

	@Query("SELECT pm FROM PlayerMission pm WHERE pm.campaign.id = ?1 and pm.playerAccount.id = ?2")
	public List<PlayerMission> findByCampaignAndPlayer(Long campaignId, Long playerId);
}

package ar.edu.unq.virtuaula.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {

	public List<Mission> findByCampaign(Campaign campaign);
}

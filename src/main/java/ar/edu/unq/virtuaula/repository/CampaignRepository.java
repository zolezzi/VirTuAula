package ar.edu.unq.virtuaula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

}

package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.CampaignDTO;
import ar.edu.unq.virtuaula.dto.MissionDTO;
import ar.edu.unq.virtuaula.exception.CampaignDateExpiredException;
import ar.edu.unq.virtuaula.exception.CampaignNotFoundException;
import ar.edu.unq.virtuaula.exception.NewGameNotFoundException;
import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.model.Mission;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.PlayerMission;
import ar.edu.unq.virtuaula.repository.CampaignRepository;
import ar.edu.unq.virtuaula.repository.MissionTypeRepository;
import ar.edu.unq.virtuaula.repository.PlayerMissionRepository;
import ar.edu.unq.virtuaula.util.ExperienceUtil;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.CampaignVO;
import ar.edu.unq.virtuaula.vo.MissionVO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final PlayerMissionRepository playerMissionRepository;
    private final MissionTypeRepository missionTypeRepository;
    private final MapperUtil mapperUtil;
    private final LevelService levelService;
    private final ManagementBufferService bufferService; 
    private final static int FULL_PROGRESS = 100;
    private final static String TELL_A_STORY_NAME = "Tell a story";

    public List<CampaignVO> getAllByNewGame(NewGame newGame) {
        List<Campaign> campaigns = newGame.getCampaigns();
        return transformToCampaignVO(campaigns, newGame.getId());
    }
    
	public List<CampaignVO> getAllByNewGameAndPlayer(NewGame newGame, PlayerAccount playerAccount) {
		List<Campaign> campaigns = newGame.getCampaigns();
		return transformToVO(campaigns, newGame.getId(), playerAccount);
	}

    public Campaign findById(Long campaignId) {
        return campaignRepository.findById(campaignId).get();
    }

    public CampaignVO completeMissions(NewGame newGame, Long campaignId, PlayerAccount playerAccount, List<MissionVO> missions) throws Exception {
       	Date date = new Date();
    	Campaign campaignBD = campaignRepository.findById(campaignId)
    			.orElseThrow(() -> new CampaignNotFoundException("Error not found campaign with id: " + campaignId));
    	if(!newGame.containsCampaign(campaignBD.getId())) {
    		throw new CampaignNotFoundException("Error not found campaign id: " + campaignId + " for new game id: " + newGame.getId());
    	}
    	if(!date.before(campaignBD.getDeliveryDate())){
    		throw new CampaignDateExpiredException("Expired that campaign id: " + campaignId + " for new game id: " + newGame.getId());
    	}
        completeState(missions, playerAccount.getId());
        CampaignVO campaignVO = createCampaignVO(campaignBD, playerAccount.getId());
        bufferService.applyBufferInPlayerAccount(playerAccount.getLevel(), playerAccount, campaignVO.getNote());
        if(ExperienceUtil.isChangeLevel(playerAccount.getLevel().getMaxValue(), playerAccount.getExperience())) {
        	playerAccount.setLevel(levelService.getNextLevel(playerAccount.getLevel()));
        }
        return campaignVO;
    }

    public CampaignDTO create(NewGame newGame, LeaderAccount leaderUser, CampaignDTO campaign) throws Exception {
        Campaign newCampaign = mapperCampaign(campaign);
        if (!leaderUser.containsNewGame(newGame)) {
            throw new NewGameNotFoundException("Error not found new game with id: " + newGame.getId());
        }
        newCampaign = campaignRepository.save(newCampaign);
        newGame.addCampaign(newCampaign);
        newCampaign.getMissions().forEach(mission -> mission.setCorrectAnswer(mission.findCorrectAnswer()));
        createPlayerMissionForAllPlayer(newCampaign, leaderUser);
        newCampaign = campaignRepository.save(newCampaign);
        return mapperUtil.getMapper().map(newCampaign, CampaignDTO.class);
    }

    private Campaign mapperCampaign(CampaignDTO campaignDto) {
    	List<Mission> listMissions = convertMission(campaignDto.getMissions());
    	Campaign campaign = mapperUtil.getMapper().map(campaignDto, Campaign.class);
    	campaign.setMissions(listMissions);
    	return campaign;
	}

	private List<Mission> convertMission(List<MissionDTO> listMissionDTOs) {
		return listMissionDTOs.stream().map(missionDTO -> {
			 Mission mission = mapperUtil.getMapper().map(missionDTO, Mission.class);
			 mission.setMissionType(missionTypeRepository.findById(missionDTO.getMissionTypeId()).get());
			 return mission;
		}).collect(Collectors.toList());
	}

	private void createPlayerMissionForAllPlayer(Campaign newCampaign, LeaderAccount leader) {
		leader.getPlayers().forEach(player -> createPlayerMissionForAllByCampaign(newCampaign, player));
	}

	private void createPlayerMissionForAllByCampaign(Campaign newCampaign, PlayerAccount player) {
		List<PlayerMission> listPlayerMissions = newCampaign.getMissions().stream().map(mission -> {
			PlayerMission playerMission = new PlayerMission();
			playerMission.uncomplete();
			playerMission.setMission(mission);
			playerMission.setCampaign(newCampaign);
			playerMission.setPlayerAccount(player);
			return playerMission;
		}).collect(toList());
		player.getResultsMissions().addAll(listPlayerMissions);
	}
	
	private List<CampaignVO> transformToCampaignVO(List<Campaign> campaigns, Long newGameId) {
        return campaigns.stream().map(campaign -> {
            CampaignVO campaignVO = mapperUtil.getMapper().map(campaign, CampaignVO.class);
            campaignVO.setNote(null);
            campaignVO.setNewGameId(newGameId);
            return campaignVO;
        }).collect(toList());
    }

	private List<CampaignVO> transformToVO(List<Campaign> campaigns, Long newGameId, PlayerAccount player) {
        return campaigns.stream().map(campaign -> {
            CampaignVO campaignVO = mapperUtil.getMapper().map(campaign, CampaignVO.class);
            List<PlayerMission> missionsBD = playerMissionRepository.findByCampaignAndPlayer(campaign.getId(), player.getId());
            campaignVO.setNote(null);
            int progress = campaign.progress(missionsBD);
            campaignVO.setProgress(progress);
            campaignVO.setNewGameId(newGameId);
            if (progress == FULL_PROGRESS) {
            	campaignVO.setNote(campaign.qualification(missionsBD));
            }
            return campaignVO;
        }).collect(toList());
    }

    private CampaignVO createCampaignVO(Campaign campaign, Long playerId) {
        CampaignVO campaignVO = mapperUtil.getMapper().map(campaign, CampaignVO.class);
        List<PlayerMission> missionsBD = playerMissionRepository.findByCampaignAndPlayer(campaign.getId(), playerId);
        campaignVO.setNote(null);
        int progress = campaign.progress(missionsBD);
        campaignVO.setProgress(progress);
        if (progress == FULL_PROGRESS) {
        	campaignVO.setNote(campaign.qualification(missionsBD));
        }
        return campaignVO;
    }

    private void completeState(List<MissionVO> missions, Long playerId) {
    	missions.stream().forEach(mission -> {
            PlayerMission playerMissionBD = playerMissionRepository.findByMissionIdAndPlayerId(mission.getId(), playerId)
            		.orElseThrow(() -> new NoSuchElementException("Error not found mission with id: " + mission.getId()));
            playerMissionBD.setAnswer(mission.getAnswerId());
            if(TELL_A_STORY_NAME.equals(playerMissionBD.getMission().getMissionType().getName())) {
            	playerMissionBD.pending();
            }else {
            	playerMissionBD.complete();
            }
            playerMissionBD.setStory(mission.getStory());
            playerMissionRepository.save(playerMissionBD);
        });
    }

}

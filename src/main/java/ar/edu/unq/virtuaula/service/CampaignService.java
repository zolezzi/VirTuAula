package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
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
import ar.edu.unq.virtuaula.exception.PlayerMissionNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.model.Mission;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.PlayerMission;
import ar.edu.unq.virtuaula.model.State;
import ar.edu.unq.virtuaula.repository.CampaignRepository;
import ar.edu.unq.virtuaula.repository.MissionTypeRepository;
import ar.edu.unq.virtuaula.repository.PlayerMissionRepository;
import ar.edu.unq.virtuaula.util.ExperienceUtil;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.CampaignVO;
import ar.edu.unq.virtuaula.vo.MissionVO;
import ar.edu.unq.virtuaula.vo.PlayerMissionVO;
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
    private final static Double RETRY_EXPERIENCE = 100d;
    private final static int LIFE_VALUE_ONE = 1;

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
        applyBuffers(campaignVO, playerAccount);
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
    
	public ResponseMessage correctMission(Long campaignId, PlayerAccount playerAccount, PlayerMissionVO playerMissionVO) throws Exception {
		PlayerMission playerMission = playerMissionRepository.findById(playerMissionVO.getId())
		.orElseThrow(() -> new PlayerMissionNotFoundException("Error not found player mission with id: " + playerMissionVO.getId()));
		playerMission.setComment(playerMissionVO.getComment());
		playerMission.setState(State.valueOf(playerMissionVO.getState()));
		playerMissionRepository.save(playerMission);
    	Campaign campaignBD = campaignRepository.findById(campaignId)
    			.orElseThrow(() -> new CampaignNotFoundException("Error not found campaign with id: " + campaignId));
		if(State.COMPLETED.toString().equals(playerMissionVO.getState())) {
			CampaignVO campaignVO = createCampaignVO(campaignBD, playerAccount.getId());
	        applyBuffers(campaignVO, playerAccount);
		}
		return new ResponseMessage("the correct mission to the campaign was successful");
	}

	public List<String> getAllStates() {
		return Arrays.asList(State.REWORK, State.COMPLETED).stream().map(state -> state.toString()).collect(toList());
	}

	public ResponseMessage retry(Long campaignId, PlayerAccount playerAccount, List<MissionVO> missions) throws CampaignNotFoundException, PlayerMissionNotFoundException {
		int life = playerAccount.getLife();
    	Campaign campaignBD = campaignRepository.findById(campaignId)
    			.orElseThrow(() -> new CampaignNotFoundException("Error not found campaign with id: " + campaignId));
    	if(!campaignBD.containsMissions(missions.stream().map(mission -> mission.getId()).collect(Collectors.toList()))) {
    		throw new PlayerMissionNotFoundException("Error not found mission with campaign id: " + campaignId);
    	}
    	completeState(missions, playerAccount.getId());
        playerAccount.setExperience(playerAccount.getExperience() + RETRY_EXPERIENCE);
        if(ExperienceUtil.isChangeLevel(playerAccount.getLevel().getMaxValue(), playerAccount.getExperience())) {
        	playerAccount.setLevel(levelService.getNextLevel(playerAccount.getLevel()));
        }
        playerAccount.setLife(life - LIFE_VALUE_ONE);
		return new ResponseMessage("the retry mission to the campaign was successful");
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
            if(TELL_A_STORY_NAME.equals(playerMissionBD.getMission().getMissionType().getName()) 
            		&& State.UNCOMPLETED.equals(playerMissionBD.getState())) {
            	playerMissionBD.pending();
            }else {
            	playerMissionBD.complete();
            }
            playerMissionBD.setStory(mission.getStory());
            playerMissionRepository.save(playerMissionBD);
        });
    }

    private void applyBuffers(CampaignVO campaignVO, PlayerAccount playerAccount) {
        bufferService.applyBufferInPlayerAccount(playerAccount.getLevel(), playerAccount, campaignVO.getNote());
        if(ExperienceUtil.isChangeLevel(playerAccount.getLevel().getMaxValue(), playerAccount.getExperience())) {
        	playerAccount.setLevel(levelService.getNextLevel(playerAccount.getLevel()));
        }	
	}

}

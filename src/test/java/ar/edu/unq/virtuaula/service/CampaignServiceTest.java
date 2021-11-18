package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.CampaignDTO;
import ar.edu.unq.virtuaula.dto.OptionMissionDTO;
import ar.edu.unq.virtuaula.dto.MissionDTO;
import ar.edu.unq.virtuaula.exception.NewGameNotFoundException;
import ar.edu.unq.virtuaula.exception.CampaignDateExpiredException;
import ar.edu.unq.virtuaula.exception.CampaignNotFoundException;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.Mission;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.CampaignVO;
import ar.edu.unq.virtuaula.vo.MissionVO;

public class CampaignServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MapperUtil mapperUtil;

    @Test
    public void getAllByNewGameWithExistingNewGameReturnCampaign() {
        int expected = 1;
        NewGame newGame = createOneNewGame();
        List<CampaignVO> result = campaignService.getAllByNewGame(newGame);
        assertEquals(expected, result.size());
    }

    @Test
    public void getAllByNewGameWithExistingNewGameReturnCampaignWithId() {
        NewGame newGame = createOneNewGame();
        List<CampaignVO> result = campaignService.getAllByNewGame(newGame);
        assertNotNull(result.get(0).getId());
        assertEquals(newGame.getCampaigns().get(0).getId(), result.get(0).getId());
    }

    @Test
    public void getAllByNewGameWithExistingNewGameReturnCampaignWithName() {
        NewGame newGame = createOneNewGame();
        List<CampaignVO> result = campaignService.getAllByNewGame(newGame);
        assertEquals(newGame.getCampaigns().get(0).getName(), result.get(0).getName());
    }

    @Test
    public void getAllByNewGameWithExistingNewGameReturnCampaignWithoutNote() {
        NewGame newGame = createOneNewGameWithTwoMissions();
        List<CampaignVO> result = campaignService.getAllByNewGame(newGame);
        assertEquals(null, result.get(0).getNote());
    }

    @Test
    public void findByIdWithCampaignReturnCampaign() {
        NewGame newGame = createOneNewGame();
        Campaign campaignReturn = campaignService.findById(newGame.getCampaigns().get(0).getId());
        assertEquals(newGame.getCampaigns().get(0).getId(), campaignReturn.getId());
        assertEquals(newGame.getCampaigns().get(0).getName(), campaignReturn.getName());
    }

    @Test
    public void completeMissionWithMissionProgressComplete() throws Exception {
        int expected = 100;
        NewGame newGame = createOneNewGame();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        PlayerAccount account = (PlayerAccount) createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission, playerAccount);
        List<MissionVO> missions = createMissionVO(campaign.getMissions());
        CampaignVO campaignVo = campaignService.completeMissions(newGame, campaign.getId(), account, missions);
        assertEquals(expected, campaignVo.getProgress());
    }

	@Test
    public void completeMissionWithMissionProgressCompleteSetNote() throws Exception {
        NewGame newGame = createOneNewGame();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        playerAccount = (PlayerAccount) createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission, playerAccount);
        List<MissionVO> missions = createMissionVO(campaign.getMissions());
        CampaignVO campaignVo = campaignService.completeMissions(newGame, campaign.getId(), playerAccount, missions);
        assertNotNull(campaignVo.getNote());
    }

    @Test
    public void completeMissionWithMissionProgressUncompleteDontAddNote() throws Exception {
        NewGame newGame = createOneNewGameWithTwoMissions();
        MissionVO missionVO = new MissionVO();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        Mission mission2 = campaign.getMissions().get(1);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        createOnePlayerMissionUncompletedWithCampaignAndMissionAndPlayerAccount(campaign, mission, playerAccount);
        createOnePlayerMissionUncompletedWithCampaignAndMissionAndPlayerAccount(campaign, mission2, playerAccount);
        missionVO.setId(1l);
        missionVO.setAnswerId(1l);
        List<MissionVO> missions = Arrays.asList(missionVO);
        CampaignVO campaignVo = campaignService.completeMissions(newGame, campaign.getId(), playerAccount, missions);
        assertEquals(null, campaignVo.getNote());
    }

    @Test
    public void completeMissionWithMissionNotExistentThrowsException() {
        NewGame newGame = createOneNewGameWithTwoMissions();
        Campaign campaign = getFirstCampaign(newGame);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        MissionVO missionVO = new MissionVO();
        missionVO.setId(-1l);
        missionVO.setAnswerId(1l);
        List<MissionVO> missions = Arrays.asList(missionVO);
        NoSuchElementException assertThrows = assertThrows(NoSuchElementException.class, () -> {
        	campaignService.completeMissions(newGame, campaign.getId(), playerAccount, missions);
        });

        assertTrue(assertThrows.getMessage().contains("Error not found mission with id: " + missionVO.getId()));
    }

    @Test
    public void whenAccountCreateCampaignInNewGameWithAccountNewGameNotFoundNewGameReturnException() {
        NewGame newGame = createOneNewGame();
        NewGame newGame2 = createOneNewGame();
        CampaignDTO campaign = new CampaignDTO();
        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setId(1l);
        missionDTO.setMissionTypeId(1l);
        List<MissionDTO> missions = Arrays.asList(missionDTO);
        campaign.setMissions(missions);
        LeaderAccount account = (LeaderAccount) createOneLeaderAccountWithNewGame(newGame);

        Exception exception = assertThrows(NewGameNotFoundException.class, () -> {
            campaignService.create(newGame2, account, campaign);
        });

        String expectedMessage = "Error not found new game with id: 2";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenAccountCreateCampaignInNewGameWithoutNewGameNotFoundNewGameReturnException() {
        NewGame newGame = createOneNewGame();
        CampaignDTO campaign = new CampaignDTO();
        LeaderAccount account = (LeaderAccount) createOneLeaderAccount();
        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setId(1l);
        missionDTO.setMissionTypeId(1l);
        List<MissionDTO> missions = Arrays.asList(missionDTO);
        campaign.setMissions(missions);
        Exception exception = assertThrows(NewGameNotFoundException.class, () -> {
            campaignService.create(newGame, account, campaign);
        });

        String expectedMessage = "Error not found new game with id: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void whenCompleteMissionsOfCampaignNotFoundThenThrowsCampaignNotFoundException() {
        NewGame newGame = createOneNewGame();
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        MissionVO missionVO = new MissionVO();
        missionVO.setId(-1l);
        missionVO.setAnswerId(1l);
        List<MissionVO> missions = Arrays.asList(missionVO);
    	Exception exception = assertThrows(CampaignNotFoundException.class, () -> {
    		campaignService.completeMissions(newGame, 10L, playerAccount, missions);
        });

        String expectedMessage = "Error not found campaign with id: 10";
        String actualMessage = exception.getMessage();
       
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    public void whenCompleteMissionOfCampaignNotContainsInNewGameThenThrowsCampaignNotFoundException() {
        NewGame newGame = createOneNewGame();
        NewGame newGame2 = createOneNewGame();
        Campaign campaign = getFirstCampaign(newGame2);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        MissionVO missionVO = new MissionVO();
        missionVO.setId(-1l);
        missionVO.setAnswerId(1l);
        List<MissionVO> missions = Arrays.asList(missionVO);
    	Exception exception = assertThrows(CampaignNotFoundException.class, () -> {
    		campaignService.completeMissions(newGame, campaign.getId(), playerAccount, missions);
        });

        String expectedMessage = "Error not found campaign id: 2 for new game id: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void whenFindNewGameByPlayerThenReturnListNotEmptyTheCampaignVo() {
    	int expected = 1;
        NewGame newGame = createOneNewGame();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission, playerAccount);
        List<CampaignVO> result = campaignService.getAllByNewGameAndPlayer(newGame, playerAccount);
        assertEquals(expected, result.size());
    }
    
    @Test
    public void whenFindNewGameByPlayerWithMissionProgressUncompleteDontAddNote() {
        NewGame newGame = createOneNewGameWithTwoMissions();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        Mission mission2 = campaign.getMissions().get(1);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission, playerAccount);
        createOnePlayerMissionUncompletedWithCampaignAndMissionAndPlayerAccount(campaign, mission2, playerAccount);
        List<CampaignVO> result = campaignService.getAllByNewGameAndPlayer(newGame, playerAccount);
        assertNull(result.get(0).getNote());
    }
    
    @Test
    public void whenCreateNewCampaignByLeaderThenReturnCampaignDTOWithId() throws Exception {
    	NewGame newGame = createOneNewGame();
    	createOneMissionType();
    	CampaignDTO campaign = new CampaignDTO();
    	MissionDTO mission = new MissionDTO();
    	mission.setCorrectAnswer(1l);
    	mission.setScore(5d);
    	mission.setStatement("test 1");
    	mission.setOptions(createOptions());
    	mission.setMissionTypeId(1l);
    	campaign.setMaxNote(10);
    	campaign.setName("Test");
    	List<MissionDTO> missions = Arrays.asList(mission);
    	campaign.setMissions(missions);
    	LeaderAccount account = (LeaderAccount) createOneLeaderAccountWithNewGame(newGame);
    	CampaignDTO result =  campaignService.create(newGame, account, campaign);
    	assertNotNull(result);
    }
    
    @Test
    public void whenCreateNewCampaignByLeaderWithPlayerThenReturnCampaignDTOWithId() throws Exception {
    	NewGame newGame = createOneNewGame();
    	createOneMissionType();
    	CampaignDTO campaign = new CampaignDTO();
    	MissionDTO mission = new MissionDTO();
    	mission.setCorrectAnswer(1l);
    	mission.setScore(5d);
    	mission.setStatement("test 1");
    	mission.setOptions(createOptions());
    	mission.setMissionTypeId(1l);
    	campaign.setMaxNote(10);
    	campaign.setName("Test");
    	List<MissionDTO> missions = Arrays.asList(mission);
    	campaign.setMissions(missions);
    	PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
    	LeaderAccount account = (LeaderAccount) createOneLeaderAccountWithNewGameAndPlayer(newGame, playerAccount);
    	CampaignDTO result =  campaignService.create(newGame, account, campaign);
    	assertNotNull(result);
    }
    
    @Test
    public void completeMissionWithMissionProgressCompleteAndIncrementLevel() throws Exception {
        int expected = 100;
        NewGame newGame = createOneNewGame();
        createLevelProfesional();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        PlayerAccount account = (PlayerAccount) createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission, playerAccount);
        List<MissionVO> missions = createMissionVO(campaign.getMissions());
        CampaignVO campaignVo = campaignService.completeMissions(newGame, campaign.getId(), account, missions);
        assertEquals(expected, campaignVo.getProgress());
    }
    
    @Test
    public void completeMissionWithCampaignWithDateExpiredThenReturnExceptionDateExpired() throws ParseException {
    	String dateString = "2021-11-08 23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(dateString + " UTC");
        NewGame newGame = createOneNewGameWithoutDateExpired(date);
        Campaign campaign = getFirstCampaign(newGame);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        MissionVO missionVO = new MissionVO();
        missionVO.setId(1l);
        missionVO.setAnswerId(1l);
        List<MissionVO> missions = Arrays.asList(missionVO);
        CampaignDateExpiredException assertThrows = assertThrows(CampaignDateExpiredException.class, () -> {
        	campaignService.completeMissions(newGame, campaign.getId(), playerAccount, missions);
        });
        String expectedMessage = "Expired that campaign id: 1 for new game id: 1";
        
        assertEquals(expectedMessage, assertThrows.getMessage());
    }
    
	@Test
    public void completeAllMissionThenReturnCampaignVOWithLevelUp() throws Exception {
        NewGame newGame = createOneNewGameWithCampaignScore(3000d);
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        playerAccount = (PlayerAccount) createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission, playerAccount);
        List<MissionVO> missions = createMissionVO(campaign.getMissions());
        CampaignVO campaignVo = campaignService.completeMissions(newGame, campaign.getId(), playerAccount, missions);
        assertNotNull(campaignVo.getNote());
    }
	
    @Test
    public void completeMissionWithMissionMultipleChoisesAndMissionTypeIsTellAStoryThenReturnProgressMiddle() throws Exception {
        int expected = 50;
        NewGame newGame = createOneNewGameWithTwoMissionType();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        Mission mission2 = campaign.getMissions().get(1);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccount();
        createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission, playerAccount);
        PlayerAccount account = (PlayerAccount) createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission2, playerAccount);
        List<MissionVO> missions = createMissionVO(campaign.getMissions());
        CampaignVO campaignVo = campaignService.completeMissions(newGame, campaign.getId(), account, missions);
        assertEquals(expected, campaignVo.getProgress());
    }
    
    private List<OptionMissionDTO> createOptions(){
    	OptionMissionDTO missionOption1 = new OptionMissionDTO();
    	OptionMissionDTO missionOption2 = new OptionMissionDTO();
    	missionOption1.setIsCorrect(true);
    	missionOption1.setResponseValue("test 1");
    	missionOption2.setIsCorrect(false);
    	missionOption2.setResponseValue("test 2");
    	return Arrays.asList(missionOption1, missionOption2);
    }
    
    private List<MissionVO> createMissionVO(List<Mission> missions) {
        return missions.stream().map(mission -> {
            MissionVO missionVO = mapperUtil.getMapper().map(mission, MissionVO.class);
            return missionVO;
        }).collect(toList());
    }
    
    private Campaign getFirstCampaign(NewGame newGame) {
		return newGame.getCampaigns().get(0);
	}


	private Mission getFirstMission(Campaign campaign) {
		return campaign.getMissions().get(0);
	}
}

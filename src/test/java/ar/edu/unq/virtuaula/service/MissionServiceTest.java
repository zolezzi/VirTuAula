package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.MissionDTO;
import ar.edu.unq.virtuaula.exception.CampaignNotFoundException;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.Mission;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.vo.PlayerMissionVO;

public class MissionServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private MissionService missionService;

    @Test
    public void getAllMissionByCampaignWithMissionReturnListWithMissionWithStatement() {
        NewGame newGame = createOneNewGame();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        List<PlayerMissionVO> result = missionService.getAllMissionByCampaignForPlayer(newGame.getCampaigns().get(0), 1L);
        assertEquals(mission.getStatement(), result.get(0).getStatement());
    }

    @Test
    public void getAllMissionByCampaignWithMissionReturnListWithMissionWithId() {
        NewGame newGame = createOneNewGame();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        List<PlayerMissionVO> result = missionService.getAllMissionByCampaignForPlayer(newGame.getCampaigns().get(0), 1L);
        assertNotNull(result.get(0).getId());
        assertEquals(mission.getId(), result.get(0).getId());
    }

    @Test
    public void getAllMissionByCampaignWithMissionAndLeaderReturnListWithMissionWithStatement() throws CampaignNotFoundException {
        NewGame newGame = createOneNewGame();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        LeaderAccount account = (LeaderAccount) createOneLeaderAccountWithNewGame(newGame);
        List<MissionDTO> result = missionService.getAllMissionByCampaign(newGame.getCampaigns().get(0), account);
        assertEquals(mission.getStatement(), result.get(0).getStatement());
    }

    @Test
    public void getAllMissionsByCampaignWithMissionAndLeaderWithoutNewGameReturnNotFoundCampaign() throws CampaignNotFoundException {
    	NewGame newGame = createOneNewGame();
        LeaderAccount account = (LeaderAccount) createOneLeaderAccount();
        Exception exception = assertThrows(CampaignNotFoundException.class, () -> {
            missionService.getAllMissionByCampaign(newGame.getCampaigns().get(0), account);
        });
        String expectedMessage = "Not found campaign id: 1 for leader account id: 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getAllMissionByCampaignWithMissionAndLeaderWithNewGameButWithoutCampaignReturnNotFoundCampaign() throws CampaignNotFoundException {
        NewGame newGame = createOneNewGame();
        NewGame newGame2 = createOneNewGame();
        LeaderAccount account = (LeaderAccount) createOneLeaderAccountWithNewGame(newGame2);
        Exception exception = assertThrows(CampaignNotFoundException.class, () -> {
            missionService.getAllMissionByCampaign(newGame.getCampaigns().get(0), account);
        });
        String expectedMessage = "Not found campaign id: 1 for leader account id: 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getAllMissionByCampaignWithMissionAndLeaderReturnListWithMissionWithId() throws CampaignNotFoundException {
        NewGame newGame = createOneNewGame();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        LeaderAccount account = (LeaderAccount) createOneLeaderAccountWithNewGame(newGame);
        List<MissionDTO> result = missionService.getAllMissionByCampaign(newGame.getCampaigns().get(0), account);
        assertNotNull(result.get(0).getId());
        assertEquals(mission.getId(), result.get(0).getId());
    }

    @Test
    public void getAllMissionByCampaignWithMissionReturnListWithMissionWithOptionMissionWithId() {
        NewGame newGame = createOneNewGameWithTwoMissionsAndTwoOptionMissions();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        List<PlayerMissionVO> result = missionService.getAllMissionByCampaignForPlayer(newGame.getCampaigns().get(0), 1L);
        assertNotNull(result.get(0).getId());
        assertEquals(mission.getId(), result.get(0).getId());
    }
    
    @Test
    public void getAllMissionByCampaignWithPlayerMissionReturnListWithPlayerMissionWithOptionMissionWithId() {
        NewGame newGame = createOneNewGameWithTwoMissionsAndTwoOptionMissions();
        Campaign campaign = getFirstCampaign(newGame);
        Mission mission = getFirstMission(campaign);
        PlayerAccount player = (PlayerAccount) createOnePlayerAccount();
        createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission, player);
        List<PlayerMissionVO> result = missionService.getAllMissionByCampaignForPlayer(newGame.getCampaigns().get(0), 1L);
        assertNotNull(result.get(0).getId());
        assertEquals(mission.getId(), result.get(0).getId());
    }
    
    private Campaign getFirstCampaign(NewGame newGame) {
		return newGame.getCampaigns().get(0);
	}


	private Mission getFirstMission(Campaign campaign) {
		return campaign.getMissions().get(0);
	}
}

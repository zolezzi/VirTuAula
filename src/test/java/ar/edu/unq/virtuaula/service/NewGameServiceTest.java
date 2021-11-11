package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.NewGameDTO;
import ar.edu.unq.virtuaula.exception.NewGameNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.Mission;
import ar.edu.unq.virtuaula.model.LeaderAccount;

public class NewGameServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private NewGameService newGameService;

    @Test
    public void getAllWithNewGameReturnNotEmptyList() {
        int expected = 1;
        createOneNewGame();
        List<NewGameDTO> result = newGameService.getAll();
        assertEquals(expected, result.size());
    }

    @Test
    public void getAllWithNewGameReturnNewGameWithId() {
        NewGame newGame = createOneNewGame();
        List<NewGameDTO> result = newGameService.getAll();
        assertNotNull(result.get(0).getId());
        assertEquals(newGame.getId(), result.get(0).getId());
    }

    @Test
    public void getAllWithNewGameReturnMathNewGameName() {
        NewGame newGame = createOneNewGame();
        List<NewGameDTO> result = newGameService.getAll();
        assertEquals(newGame.getName(), result.get(0).getName());
    }

    @Test
    public void getAllWithoutNewGamesReturnEmptyList() {
        int expected = 0;
        List<NewGameDTO> result = newGameService.getAll();
        assertEquals(expected, result.size());
    }

    @Test
    public void getAllWithNewGameReturnNewGameWithProgress() {
        int expected = 0;
        createOneNewGame();
        List<NewGameDTO> result = newGameService.getAll();
        assertEquals(expected, result.get(0).getProgress());
    }

    @Test
    public void getAllWithNewGameWithAccountReturnNewGameWithProgress() {
        int expected = 100;
        NewGame newGame = createOneNewGame();
        Campaign campaign = newGame.getCampaigns().get(0);
        Mission mission = campaign.getMissions().get(0);
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccountWithNewGame(newGame);
        createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission, playerAccount);
        List<NewGameDTO> result = newGameService.findByAccount(playerAccount);
        assertEquals(expected, result.get(0).getProgress());
    }
    
    @Test
    public void getAllWithNewGameWithoutCampaignAndAccountReturnNewGameWithProgress() {
        int expected = 0;
        NewGame newGame = createOneNewGameWithoutCampaign();
        PlayerAccount playerAccount = (PlayerAccount) createOnePlayerAccountWithNewGame(newGame);
        List<NewGameDTO> result = newGameService.findByAccount(playerAccount);
        assertEquals(expected, result.get(0).getProgress());
    }
    
    @Test
    public void findByIdWithNewGameReturnNewGame() {
        NewGame newGame = createOneNewGame();
        NewGame newGameReturn = newGameService.findById(newGame.getId());
        assertEquals(newGame.getId(), newGameReturn.getId());
        assertEquals(newGame.getName(), newGameReturn.getName());
    }
    
    @Test
    public void findByAccountIdWithoutNewGameReturnEmptyList() {
    	int expected = 0;
    	Account account = createOneLeaderAccount();
        List<NewGameDTO> newGameReturn = newGameService.findByAccount(account);
        assertEquals(expected, newGameReturn.size());
    }
    
    @Test
    public void findByAccountIdWithNewGameReturnContainsNewGame() {
    	NewGame newGame = createOneNewGame();
    	LeaderAccount account = (LeaderAccount) createOneLeaderAccountWithNewGame(newGame);
    	Boolean result = account.containsNewGame(newGame);
        assertTrue(result);
    }
    
    @Test
    public void whenAccountQueryContainsNewGameWithoutNewGameReturnFalse() {
    	NewGame newGame = createOneNewGame();
    	NewGame newGame2 = createOneNewGame();
    	LeaderAccount account = (LeaderAccount) createOneLeaderAccountWithNewGame(newGame);
    	Boolean result = account.containsNewGame(newGame2);
        assertFalse(result);
    }
    
    @Test
    public void whenCreateNewNewGameWithLeaderAccountThenNewGameWithId() {
    	int expected = 2;
    	List<Long> accounts = new ArrayList<>();
    	accounts.add(1l);
    	NewGame newGame = createOneNewGame();
    	PlayerAccount player = (PlayerAccount) createOnePlayerAccount();
    	LeaderAccount leader = (LeaderAccount) createOneLeaderAccountWithNewGameAndPlayer(newGame, player);
    	NewGameDTO newGameDTO = Mockito.mock(NewGameDTO.class);
	    Mockito.when(newGameDTO.getName()).thenReturn("Algebra");
	    Mockito.when(newGameDTO.getDescription()).thenReturn("Materia dada en el horario del 14 a 18");
	    NewGameDTO result = newGameService.create(leader, newGameDTO, accounts);
        assertNotNull(result);
        assertEquals(expected, leader.getNewGames().size());
    }
   
    @Test
    public void whenAssignNewGameWithLeaderAccountThenReturnNewGameWithId() throws NewGameNotFoundException {
    	List<Long> accounts = new ArrayList<>();
    	accounts.add(1l);
    	accounts.add(2l);
    	NewGame newGame = createOneNewGame();
        Campaign campaign = newGame.getCampaigns().get(0);
        Mission mission = campaign.getMissions().get(0);
        PlayerAccount player = (PlayerAccount) createOnePlayerAccount();
        player = (PlayerAccount) createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission, player);
    	LeaderAccount leader = (LeaderAccount) createOneLeaderAccountWithNewGameAndPlayer(newGame, player);
	    ResponseMessage result = newGameService.assign(leader, 1l, accounts);
	    String expectedMessage = "the assignment to the new game was successful";
	    assertNotNull(result);
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    public void whenAssignNewGameWithLeaderAccountThenReturnThrowsExceptionNewGameNotFound() throws NewGameNotFoundException {
    	List<Long> accounts = new ArrayList<>();
    	accounts.add(1l);
    	accounts.add(2l);
    	NewGame newGame = createOneNewGame();
        Campaign campaign = newGame.getCampaigns().get(0);
        Mission mission = campaign.getMissions().get(0);
        PlayerAccount player = (PlayerAccount) createOnePlayerAccount();
        player = (PlayerAccount) createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(campaign, mission, player);
    	LeaderAccount leader = (LeaderAccount) createOneLeaderAccountWithNewGameAndPlayer(newGame, player);
    	Exception exception = assertThrows(NewGameNotFoundException.class, () -> {
    		newGameService.assign(leader, 2l, accounts);
        });

        String expectedMessage = "Error not found new game id: 2";
        String actualMessage = exception.getMessage();
       
        assertEquals(expectedMessage, actualMessage);
    }
}
package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.dto.LevelDTO;
import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.exception.PlayerAccountNotFoundException;
import ar.edu.unq.virtuaula.exception.LeaderAccountNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.vo.AccountVO;
import ar.edu.unq.virtuaula.vo.PlayerAccountVO;

public class AccountServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private AccountService accountService;
    
    @Test
    public void findLeaderAccountReturnAccountWithId() throws LeaderAccountNotFoundException {
        Account account = createOneLeaderAccount();
        Account result = (Account) accountService.findLeaderById(1l);
        assertNotNull(result);
        assertEquals(result.getId(), account.getId());
    }

    @Test
    public void whenfindLeaderAccountWithUsernameNotExistsThenThrowExpetion() {
        Exception exception = assertThrows(LeaderAccountNotFoundException.class, () -> {
            accountService.findLeaderById(10l);
        });

        String expectedMessage = "Error not found leader account with id: 10";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void findAccountReturnAccountWithId() throws AccountNotFoundException {
        Account account = createOneLeaderAccount();
        Account result = (Account) accountService.findById(1l);
        assertNotNull(result);
        assertEquals(result.getId(), account.getId());
    }

    @Test
    public void whenfindAccountWithUsernameNotExistsThenThrowExpetion() {
        Exception exception = assertThrows(AccountNotFoundException.class, () -> {
            accountService.findById(10l);
        });

        String expectedMessage = "Error not found account with id: 10";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void createLeaderAccountReturnAccountWithId() throws LeaderAccountNotFoundException {
        User user = createOneUser();
        AccountDTO account = Mockito.mock(AccountDTO.class);
        Mockito.when(account.getFirstName()).thenReturn("Charlie");
        Mockito.when(account.getLastName()).thenReturn("Zolezzi");
        Mockito.when(account.getEmail()).thenReturn("charlie@virtuaula.com");
        Mockito.when(account.getDni()).thenReturn(36001002);

        AccountVO result = accountService.createAccountLeader(user, account);
        assertNotNull(result);
    }

    @Test
    public void findPlayerAccountReturnAccountWithId() throws PlayerAccountNotFoundException {
        Account account = createOnePlayerAccount();
        Account result = (Account) accountService.findPlayerById(1l);
        assertNotNull(result);
        assertEquals(result.getId(), account.getId());
    }

    @Test
    public void whenFindPlayerAccountWithUsernameNotExistsThenThrowExpetion() {
        Exception exception = assertThrows(PlayerAccountNotFoundException.class, () -> {
            accountService.findPlayerById(10l);
        });

        String expectedMessage = "Error not found player account with id: 10";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void getExperienceWithPlayerAccountReturnExperience() throws PlayerAccountNotFoundException {
        PlayerAccount account = (PlayerAccount) createOnePlayerAccount();
        Double experience = accountService.getExperience(account.getId());
        assertNotNull(experience);
        assertEquals(experience, account.getExperience());
    }
    
    @Test
    public void testWhenLoadCSVFileWithPlayersValidThenReturnMessageSuccess() throws IOException {
    	String expected = "Uploaded the file successfully: hello.csv";
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("First Name,Last Name,DNI,Email\n");
        csvBuilder.append("Carlos,Cardozo,36000001,carlos@gmail.com");
        InputStream is = new ByteArrayInputStream(csvBuilder.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv", "text/csv", is);
        LeaderAccount account = (LeaderAccount) createOneLeaderAccount();
        ResponseMessage message = accountService.uploadFilePlayers(account, file);
        assertNotNull(message);
        assertEquals(expected, message.getMessage());
    }
    
    @Test
    public void testWhenLoadCSVFileWithFormatNoValidThenReturnMessageEmpty() throws IOException {
    	String expected = "";
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("First Name,Last Name,DNI,Email\n");
        csvBuilder.append("Carlos,Cardozo,36000001,carlos@gmail.com");
        InputStream is = new ByteArrayInputStream(csvBuilder.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "hello.html", "text/html", is);
        LeaderAccount account = (LeaderAccount) createOneLeaderAccount();
        ResponseMessage message = accountService.uploadFilePlayers(account, file);
        assertNotNull(message);
        assertEquals(expected, message.getMessage());
    }
    
    @Test
    public void testWhenLoadCSVFileWithoutPlayersThenReturnMessageEmpty() throws IOException {
    	String expected = "Please review file, i do not know loaded any lines from the file: hello.csv";
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("First Name,Last Name,DNI,Email\n");
        InputStream is = new ByteArrayInputStream(csvBuilder.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv", "text/csv", is);
        LeaderAccount account = (LeaderAccount) createOneLeaderAccount();
        ResponseMessage message = accountService.uploadFilePlayers(account, file);
        assertNotNull(message);
        assertEquals(expected, message.getMessage());
    }
    
    @Test
    public void testWhenLoadCSVFileNotValidThenReturnException() throws IOException {
    	String expected = "Could not upload the file: hello.csv!";
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("First Name,Last Name,DNI,Email\n");
        MockMultipartFile file = Mockito.mock(MockMultipartFile.class);
        Mockito.when(file.getContentType()).thenReturn("text/csv");
        Mockito.when(file.getOriginalFilename()).thenReturn("hello.csv");
        Mockito.when(file.getInputStream()).thenReturn(null);
        LeaderAccount account = (LeaderAccount) createOneLeaderAccount();
        ResponseMessage message = accountService.uploadFilePlayers(account, file);
        assertNotNull(message);
        assertEquals(expected, message.getMessage());
    }
    
    @Test
    public void getLevelWithPlayerAccountReturnLevel() throws PlayerAccountNotFoundException {
        String expected = "Principiante";
    	PlayerAccount account = (PlayerAccount) createOnePlayerAccount();
        LevelDTO result = accountService.getLevel(account.getId());
        assertNotNull(result);
        assertEquals(expected, result.getName());
    }
    
    @Test
    public void whenLeaderFindAllPlayerByLeaderThenReturnAllPlayersAssociate() throws PlayerAccountNotFoundException {
    	Integer expected = 1;
    	NewGame newGame = createOneNewGame();
    	PlayerAccount account = (PlayerAccount) createOnePlayerAccount();
    	LeaderAccount leader = (LeaderAccount) createOneLeaderAccountWithNewGameAndPlayer(newGame, account);
        List<PlayerAccountVO> result = accountService.findAllPlayersByLeader(leader);
        assertNotNull(result);
        assertEquals(expected, result.size());
    }
    
    
}

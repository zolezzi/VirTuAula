package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.exception.TeacherNotFoundException;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.User;

public class AccountServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private AccountService accountService;
	
    @Test
    public void findTeacherAccountReturnAccountWithId() throws TeacherNotFoundException {
    	Account account = createOneTeacherAccount();
        Account result = (Account) accountService.findTeacherById(1l);
        assertNotNull(result);
        assertEquals(result.getId(), account.getId());
    }
    
    @Test
    public void whenfindTeacherAccountWithUsernameNotExistsThenThrowExpetion() {
        Exception exception = assertThrows(TeacherNotFoundException.class, () -> {
        	accountService.findTeacherById(10l);
        });

        String expectedMessage = "Error not found account with id: 10";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void findAccountReturnAccountWithId() throws AccountNotFoundException {
    	Account account = createOneTeacherAccount();
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

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void createTeacherAccountReturnAccountWithId() throws TeacherNotFoundException {
    	User user = createOneUser();
    	String expected = "charly2";
    	AccountDTO account = Mockito.mock(AccountDTO.class);
	    Mockito.when(account.getFirstName()).thenReturn("Charlie");
	    Mockito.when(account.getLastName()).thenReturn("Zolezzi");
	    Mockito.when(account.getEmail()).thenReturn("charlie@virtuaula.com");
	    Mockito.when(account.getDni()).thenReturn(36001002);
	    
	    AccountDTO result = accountService.createAccountTeacher(user, account);
        assertNotNull(result);
        assertEquals(expected, result.getUsername());
    }
}

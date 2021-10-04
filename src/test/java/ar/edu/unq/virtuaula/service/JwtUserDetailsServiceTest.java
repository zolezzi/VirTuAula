package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.model.User;

public class JwtUserDetailsServiceTest extends VirtuaulaApplicationTests{

    @Autowired
    private JwtUserDetailsService UserDetailsService;
	
    @Test
    public void getUserWithUsernameReturnUserWithId() {
        User user = createOneUserWithTeacherAccount();
        User result = (User) UserDetailsService.loadUserByUsername("charlie");
        assertNotNull(result);
        assertEquals(result.getId(), user.getId());
    }
    
    @Test
    public void whenGetUserWithUsernameNotExistsThenThrowExpetion() {
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
        	UserDetailsService.loadUserByUsername("charlie2");
        });

        String expectedMessage = "User not found with username: charlie2";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

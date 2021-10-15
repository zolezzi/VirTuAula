package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.UserDTO;
import ar.edu.unq.virtuaula.exception.UserNotFoundException;
import ar.edu.unq.virtuaula.exception.UserRegisterException;
import ar.edu.unq.virtuaula.model.User;

public class JwtUserDetailsServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private JwtUserDetailsService UserDetailsService;

    @Test
    public void getUserWithUsernameReturnUserWithId() {
        User user = createOneUserWithTeacherAccount();
        User result = (User) UserDetailsService.loadUserByUsername("charlie");
        assertNotNull(result);
        assertNotNull(result.getAuthorities());
        assertTrue(result.isAccountNonExpired());
        assertTrue(result.isAccountNonLocked());
        assertTrue(result.isCredentialsNonExpired());
        assertTrue(result.isEnabled());
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

    @Test
    public void testRegisterAUserWithUserValidReturnUserWithUsername() throws UserRegisterException {
        UserDTO user = Mockito.mock(UserDTO.class);
        Mockito.when(user.getPassword()).thenReturn("1234567n");
        Mockito.when(user.getRepeatPassword()).thenReturn("1234567n");
        Mockito.when(user.getEmail()).thenReturn("charlie2@gmail.com");
        Mockito.when(user.getUsername()).thenReturn("charly2");
        UserDTO userResult = UserDetailsService.register(user);
        assertEquals(user.getUsername(), userResult.getUsername());
    }

    @Test
    public void testRegisterAUserWithUserValidButItAlreadyExistsRegisterEmailReturnUserRegisterException() {
        createOneUser();
        UserDTO user = Mockito.mock(UserDTO.class);
        Mockito.when(user.getPassword()).thenReturn("1234567n");
        Mockito.when(user.getRepeatPassword()).thenReturn("1234567n");
        Mockito.when(user.getEmail()).thenReturn("charlie@gmail.com");
        Mockito.when(user.getUsername()).thenReturn("charly2");
        Exception exception = assertThrows(UserRegisterException.class, () -> {
            UserDetailsService.register(user);
        });

        String expectedMessage = "There is already a registered user with this email: charlie@gmail.com";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testRegisterAUserWithUserInvalidEmailReturnUserRegisterException() {
        createOneUser();
        UserDTO user = Mockito.mock(UserDTO.class);
        Mockito.when(user.getPassword()).thenReturn("1234567n");
        Mockito.when(user.getRepeatPassword()).thenReturn("1234567n");
        Mockito.when(user.getEmail()).thenReturn("charlie@");
        Mockito.when(user.getUsername()).thenReturn("charly2");
        Exception exception = assertThrows(UserRegisterException.class, () -> {
            UserDetailsService.register(user);
        });

        String expectedMessage = "Error register user: [Error email invalid]";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindAUserWithUserValidReturnUserWithUsername() throws UserRegisterException, UserNotFoundException {
        User user = createOneUser();
        User userResult = UserDetailsService.findById(user.getId());
        assertEquals(user.getUsername(), userResult.getUsername());
    }

    @Test
    public void testFindUserByIdNotRegisterThenReturnUserNotFoundException() throws UserNotFoundException {
        createOneUser();
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            UserDetailsService.findById(10l);
        });

        String expectedMessage = "User not found with user by ID: 10";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testRegisterAUserWithUserInvalidPasswordReturnUserRegisterException() {
        createOneUser();
        UserDTO user = Mockito.mock(UserDTO.class);
        Mockito.when(user.getPassword()).thenReturn("1234567n");
        Mockito.when(user.getRepeatPassword()).thenReturn("2");
        Mockito.when(user.getEmail()).thenReturn("charlie@virtuaula.com");
        Mockito.when(user.getUsername()).thenReturn("charly2");
        Exception exception = assertThrows(UserRegisterException.class, () -> {
            UserDetailsService.register(user);
        });

        String expectedMessage = "Error register user: [Error the password must be the same]";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.AuthRequestDTO;
import ar.edu.unq.virtuaula.dto.JwtResponseDTO;
import ar.edu.unq.virtuaula.exception.LeaderAccountNotFoundException;

public class AuthenticationUserServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private AuthenticationUserService authenticationUserService;

    @Test
    public void whenLoginUserAccountValidReturnJwtResponse() throws LeaderAccountNotFoundException {
        createOneUserWithLeaderAccount();
        AuthRequestDTO authRequest = new AuthRequestDTO();
        authRequest.setUsername("charlie");
        authRequest.setPassword("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
        JwtResponseDTO result = authenticationUserService.login(authRequest);
        assertNotNull(result);
    }

    @Test
    public void whenLoginUserWithoutAccountValidReturnJwtResponse() throws LeaderAccountNotFoundException {
        createOneUser();
        AuthRequestDTO authRequest = new AuthRequestDTO();
        authRequest.setUsername("charly2");
        authRequest.setPassword("1234567n");
        JwtResponseDTO result = authenticationUserService.login(authRequest);
        assertNotNull(result);
    }

}

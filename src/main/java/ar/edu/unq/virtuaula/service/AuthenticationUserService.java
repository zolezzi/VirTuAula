package ar.edu.unq.virtuaula.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.dto.AuthRequestDTO;
import ar.edu.unq.virtuaula.dto.JwtResponseDTO;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.util.JwtTokenUtil;
import ar.edu.unq.virtuaula.util.MapperUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationUserService {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final MapperUtil mapperUtil;

    public JwtResponseDTO login(AuthRequestDTO authRequest) {
        authenticate(authRequest.getUsername(), authRequest.getPassword());

        User userDetails = (User) jwtUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
       AccountDTO account =  mapperUtil.getMapper().map(userDetails.getAccount(), AccountDTO.class);
        return new JwtResponseDTO(userDetails.getUsername(), token, account);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
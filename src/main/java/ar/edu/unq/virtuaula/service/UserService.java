package ar.edu.unq.virtuaula.service;

import ar.edu.unq.virtuaula.dto.AuthRequestDTO;
import ar.edu.unq.virtuaula.dto.JwtResponseDTO;
import ar.edu.unq.virtuaula.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public JwtResponseDTO login(AuthRequestDTO authRequest) {
        authenticate(authRequest.getUsername(), authRequest.getPassword());

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponseDTO(userDetails.getUsername(), token);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}

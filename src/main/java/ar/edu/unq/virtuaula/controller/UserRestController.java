package ar.edu.unq.virtuaula.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.AuthRequestDTO;
import ar.edu.unq.virtuaula.dto.JwtResponseDTO;
import ar.edu.unq.virtuaula.service.AuthenticationUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    
    private final AuthenticationUserService authenticationUserService;

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody AuthRequestDTO authRequestDto) {
            return ResponseEntity.ok().body(authenticationUserService.login(authRequestDto));
    }
}
